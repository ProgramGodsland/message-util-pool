package com.cpaas.portal.client.message.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class RabbitmqDeliveryChannelManager {

  private static Logger log =
      LoggerFactory.getLogger(RabbitmqDeliveryChannelManager.class.getName());

  private final RabbitmqChannelsPool channelPool;

  private RabbitmqDeliveryChannelManager() {
    this.channelPool = RabbitmqChannelsPool.getInstance();
  }

  public Channel acquireLocalChannel() {
    Channel channel = null;
    try {
      channel = this.channelPool.borrowObject();
      try {
        /**
         * Add listener
         */
        log.debug("Listners registration.");
        /**
         * Since it is behavior, we may just make singleton of it, and add reference to it.
         */

      } catch (final Exception e) {
        log.error("Operation failed... Msg: {}", e.getMessage());
        this.channelPool.invalidateObject(channel);
      }
    } catch (final Exception e) {
      log.error("Failed to borrow object from Pool. ");
      e.printStackTrace();
    }
    return channel;
  }

  public void returnChannel(final Channel channel) {
    try {
      this.channelPool.returnObject(channel);
    } catch (final Exception e) {
      log.error("Failed to return channel to channel Pool. ");
      e.printStackTrace();
    }
  }
}
