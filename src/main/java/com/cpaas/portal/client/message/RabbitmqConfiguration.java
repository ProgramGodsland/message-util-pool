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
package com.cpaas.portal.client.message;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cpaas.portal.client.message.properties.MessageProperties;
import com.cpaas.portal.client.message.properties.MessagePropertiesLoader;
import com.cpaas.portal.client.message.util.MessageFactory;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author irene
 *
 * 
 */
@Configuration
public class RabbitmqConfiguration {
  @Autowired
  private MessagePropertiesLoader propertiesLoader;

  private String directExchange;
  private String queueName;
  private MessageProperties properties;

  @PostConstruct
  private void init() {
    try {
      queueName = InetAddress.getLocalHost().getHostName();
      System.err.println("Host name: " + queueName);
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      System.err.println("Unable to get queueName");
      queueName = "";
      e.printStackTrace();
    }
    properties = propertiesLoader.getMsgProp();
    directExchange = properties.getRabbitmqServiceName();
    queueName = String.format("%s-%s", directExchange, queueName);
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost(this.properties.getRabbitmqHost());
    connectionFactory.setPort(this.properties.getRabbitmqPort());
    connectionFactory.setUsername(this.properties.getRabbitUsername());
    connectionFactory.setPassword(this.properties.getRabbitPasswd());
    return connectionFactory;
  }

  @Bean
  public MessageFactory messageFactory() {
    return MessageFactory.getInstance();
  }

  public String getDirectExchangeName() {
    return this.directExchange;
  }

  public String getQueueName() {
    return this.queueName;
  }

  public MessagePropertiesLoader getMessagePropertiesLoader() {
    return this.propertiesLoader;
  }
}
