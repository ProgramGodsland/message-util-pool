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
package com.cpaas.portal.client.message.producer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpaas.portal.client.message.manage.RabbitmqDeliveryChannelManager;
import com.cpaas.portal.client.message.resource.Message;
import com.cpaas.portal.client.message.util.MessageFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;


/**
 * @author irene
 *
 * 
 */
@Component
public class Producer {
  protected Logger logger = LoggerFactory.getLogger(getClass());
  
    private static Logger log = LoggerFactory.getLogger(Producer.class.getName());

    @Autowired
    private MessageFactory utils;
    
    @Autowired
    private RabbitmqDeliveryChannelManager manager;

    /**
     * Send message by default routing key, subscriber
     * 
     * @param msg Message
     * @return 
     * @throws Exception
     */
    public boolean sendMessage(final Message msg) throws Exception {
      if (null == msg || null == msg.getMessageParams()) {
        throw new Exception("Invalid request");
      }
      final boolean result =
          this.sendMessage(msg, msg.getMessageParams().getSubscriber());
      return result;
    }

    /**
     * Send message by using input routing key
     * 
     * @param msg Message
     * @param routingKey Routing key
     * @return
     * @throws Exception
     */
    public boolean sendMessage(final Message msg, final String routingKey)
        throws Exception {
      if (null == msg || null == msg.getMessageParams()) {
        throw new Exception("Invalid request");
      }
      boolean result = false;
      Channel channel = this.manager.acquireLocalChannel();

      try {
        final String exchange = msg.getMessageParams().getTerminatingMS();

        byte[] contents;
        log.info("Message to deliver: {}", msg);
        contents = utils.serializeData(msg);

        channel.basicPublish(exchange, routingKey, true, MessageProperties.BASIC, contents);

        this.manager.returnChannel(channel);
        result = true;
      } catch (final IOException e) {
        log.error("Message sending failure: msg {}", msg);
        e.printStackTrace();
      } catch (final Exception e) {
        log.error("Message sending unknown failure: msg {}", msg);
        e.printStackTrace();
      }
      return result;
    }

    
    

}
