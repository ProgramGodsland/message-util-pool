/**
 * GENBAND ("GENBAND") CONFIDENTIAL
 *
 * Copyright (c) 2012-2018 [GENBAND], All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains the property of GENBAND. The
 * intellectual and technical concepts contained herein are proprietary to COMPANY and may be
 * covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or
 * copyright law. Dissemination of this information or reproduction of this material is strictly
 * forbidden unless prior written permission is obtained from GENBAND. Access to the source code
 * contained herein is hereby forbidden to anyone except current GENBAND employees, managers or
 * contractors who have executed
 *
 * Confidentiality and Non-disclosure agreements explicitly covering such access.
 *
 * The copyright notice above does not evidence any actual or intended publication or disclosure of
 * this source code, which includes information that is confidential and/or proprietary, and is a
 * trade secret, of GENBAND. ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC PERFORMANCE, OR
 * PUBLIC DISPLAY OF OR THROUGH USE OF THIS SOURCE CODE WITHOUT THE EXPRESS WRITTEN CONSENT OF
 * COMPANY IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL TREATIES.
 * THE RECEIPT OR POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY
 * ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL
 * ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
 **/
package com.cpaas.portal.client.message.manage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpaas.portal.client.message.RabbitmqConfiguration;
import com.cpaas.portal.client.message.properties.MessageProperties;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author irene
 *
 * 
 */
@Component
public class RabbitmqConnection {
  private static Logger log = LoggerFactory.getLogger(RabbitmqConnection.class.getName());

  private Connection connection;
  private MessageProperties prop;

  @Autowired
  private RabbitmqConfiguration config;

  @Autowired
  private ConnectionFactory connectionFactory;

  @PostConstruct
  private void init() {
    /**
     * Try to establish an connection with RabbitMQ
     */
    try {
      this.prop = config.getMessageProperties();
      if (false != this.establishConnection()) {
        log.debug("Rabbitmq connection to {}:{} is establishing", this.prop.getRabbitmqHost(),
            this.prop.getRabbitmqPort());
        RabbitmqChannelsPool.getInstance().init(this.connection,
            this.prop.getRabbitmqChannelPoolSize());
      } else {
        log.error("cannot connect to RabbitMQ broker...");
      }
    } catch (final Throwable e) {
      log.error("Rabbitmq connection errors : " + e.getMessage());
      e.printStackTrace();
    }
  }

  private boolean establishConnection() throws InterruptedException {
    boolean result = false;
    final ConnectionFactory factory = connectionFactory;

    try {
      log.debug("Connection host:" + this.prop.getRabbitmqHost() + " port: "
          + this.prop.getRabbitmqPort());
      final Connection connection = factory.newConnection();
      this.connection = connection;
      result = true;
    } catch (final TimeoutException e) {
      log.error("Rabbitmq connection timeout, connection cannot be established. for reason: {}",
          e.getMessage());
    } catch (final IOException e) {
      log.error("Rabbitmq IO Exception. for reason: {}", e.getMessage());
      /**
       * Connection established failure handling, re-schedule a new connection.
       */
      log.debug(String.format("Waiting for interval %d and re-attempt to connect : %s:%s ", 15000,
          this.prop.getRabbitmqHost(), this.prop.getRabbitmqPort()));
      Thread.sleep(15000);

      this.establishConnection();

    } catch (final Exception e) {
      log.error("Unknown exception error: {}", e.getMessage());
    }
    return result;
  }
}
