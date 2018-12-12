package com.cpaas.portal.client.message.manage;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RabbitmqChannelsPool implements ObjectPool<Channel> {

  private static Logger log = LoggerFactory.getLogger(RabbitmqChannelsPool.class.getName());
  private final ReentrantLock lock;

  /**
   * Use class loader to secure thread safe singleton
   * 
   * @author sewang
   *
   */
  private static class LazyHolder {
    static final RabbitmqChannelsPool INSTANCE = new RabbitmqChannelsPool();
  }

  public static RabbitmqChannelsPool getInstance() {
    return LazyHolder.INSTANCE;
  }

  private final Queue<Channel> channels;
  private Connection connection;
  private int poolSize;

  private RabbitmqChannelsPool() {
    log.debug("Initializing rabbitmq channels pool...");
    this.channels = new LinkedBlockingQueue<Channel>();
    this.lock = new ReentrantLock();

  }


  /**
   * Fill in connection data source.
   */
  public void init(final Connection connection, final int initialConnection) {

    log.debug("Initialize channel pool size to " + initialConnection);
    this.connection = connection;
    this.poolSize = initialConnection;
    for (; this.channels.size() < initialConnection;) {
      try {
        this.lock.tryLock(10, TimeUnit.SECONDS);
        this.addObject();
        if (this.lock.isHeldByCurrentThread()) {
          this.lock.unlock();
        }
      } catch (final IOException e) {
        log.error("IOException: " + e.getMessage());
      } catch (final IllegalStateException e) {
        log.error("IllegalStateException: " + e.getMessage());
      } catch (final UnsupportedOperationException e) {
        log.error("UnsupportedOperationException: " + e.getMessage());
      } catch (final Exception e) {
        log.error("Unknow exception: " + e.getMessage());
      }
    }
  }

  /**
   * Be called when initialized.
   */
  @Override
  public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {

    final Channel channel = this.connection.createChannel();
    
    this.channels.add(channel);
    log.debug("Add channel to pool, channel pool size: " + this.channels.size());
  }

  @Override
  public Channel borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
    Channel channel = null;
    this.lock.tryLock(10, TimeUnit.SECONDS);

    channel = this.channels.poll();
    if (null == channel) {
      this.poolSize += 10;
      log.debug("Enlarge rabbitmq channel pool size by 10. ");
      for (int i = 0; i < 10; i++) {
        this.addObject();
      }
      channel = this.channels.poll();
    }
    log.debug("Channel pool size down to " + this.channels.size());

    if (this.lock.isHeldByCurrentThread()) {
      this.lock.unlock();
    }
    return channel;
  }

  /**
   * Clear the collection, but still keep it there
   */
  @Override
  public void clear() throws Exception, UnsupportedOperationException {
    this.channels.clear();
  }

  @Override
  public void close() throws Exception {

  }

  /**
   * Have to introduce another queue to get the accurate number of active number, now just use pool
   * supposed size minus actual size
   */
  @Override
  public int getNumActive() throws UnsupportedOperationException {
    return this.poolSize - this.getNumIdle();
  }

  @Override
  public int getNumIdle() throws UnsupportedOperationException {
    return this.poolSize - this.channels.size();
  }

  /**
   * Think: is it necessary to close and re-open channel again?
   */
  @Override
  public void invalidateObject(Channel channel) throws Exception {
    /**
     * Reset channel
     */
    this.lock.tryLock(10, TimeUnit.SECONDS);
    if (null != channel) {
      if (channel.isOpen()) {
        channel.close();
      }
      channel = null;
      /**
       * Add a new one, just to keep stats correct
       */
      this.addObject();
    }
    if (this.lock.isHeldByCurrentThread()) {
      this.lock.unlock();
    }
  }

  /**
   * May use semapthore.
   */
  @Override
  public void returnObject(Channel channel) throws Exception {
    this.lock.tryLock(10, TimeUnit.SECONDS);
    if (channel.isOpen()) {
      this.channels.add(channel);
      log.debug("Channel still open return it to pool. Pool size: " + this.channels.size());
    } else {
      channel = null;
      log.debug("Channel has been closed, reinitializing one... ");
      this.addObject();
    }
    if (this.lock.isHeldByCurrentThread()) {
      this.lock.unlock();
    }
  }


  /**
   * Will not imiplement anything for now
   */
  @Override
  public void setFactory(final PoolableObjectFactory<Channel> arg0)
      throws IllegalStateException, UnsupportedOperationException {

  }

}