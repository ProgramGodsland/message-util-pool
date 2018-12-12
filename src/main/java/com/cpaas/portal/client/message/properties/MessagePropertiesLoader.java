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
package com.cpaas.portal.client.message.properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author irene
 * 
 */
@Component
public class MessagePropertiesLoader {
  private static Logger log = LoggerFactory.getLogger(MessagePropertiesLoader.class);

  private MessageProperties msgProp = new MessageProperties();

  @PostConstruct
  private void init() {
    loadRabbitmqServiceName();
    loadRabbitmqHost();
    loadRabbitmqUsername();
    loadRabbitmqPassword();

    loadRabbitmqPort();
    loadRabbitmqManagementPort();
    loadRabbitmqMessageTTL();
    loadRabbitmqChannelPoolSize();
  }

  public MessageProperties getMsgProp() {
    return msgProp;
  }

  private void loadRabbitmqServiceName() {
    System.err.println("service name: " + System.getenv("SERVICE_NAME"));

    String rabbitmqServiceName =
        null == msgProp.getRabbitmqServiceName() ? System.getenv("SERVICE_NAME")
            : msgProp.getRabbitmqServiceName();

    if (null == rabbitmqServiceName) {
      rabbitmqServiceName = new String("kandy-thee");
    }
    log.info("Rabbitmq service name: " + rabbitmqServiceName);
    msgProp.setRabbitmqServiceName(rabbitmqServiceName);

  }

  private void loadRabbitmqHost() {
    String rabbitmqHost = null == msgProp.getRabbitmqHost() ? System.getenv("RABBIT_HOST")
        : msgProp.getRabbitmqHost();
    if (null == rabbitmqHost) {
      rabbitmqHost = new String("172.28.249.154");
    }
    log.info("Rabbitmq host: " + rabbitmqHost);
    msgProp.setRabbitmqHost(rabbitmqHost);
  }

  private void loadRabbitmqUsername() {
    String rabbitmqUsername = null == msgProp.getRabbitUsername() ? System.getenv("RABBIT_USERNAME")
        : msgProp.getRabbitUsername();
    if (null == rabbitmqUsername) {
      rabbitmqUsername = new String("guest");
    }
    log.info("Rabbitmq username: " + rabbitmqUsername);
    msgProp.setRabbitUsername(rabbitmqUsername);
  }

  private void loadRabbitmqPassword() {
    String rabbitmqPasswd = null == msgProp.getRabbitPasswd() ? System.getenv("RABBIT_PASSWD")
        : msgProp.getRabbitPasswd();
    if (null == rabbitmqPasswd) {
      rabbitmqPasswd = new String("guest");
    }
    log.info("Rabbitmq password: " + rabbitmqPasswd);
    msgProp.setRabbitPasswd(rabbitmqPasswd);
  }

  private void loadRabbitmqPort() {
    final String rabbitPort = 0 == msgProp.getRabbitmqPort() ? System.getenv("RABBIT_PORT")
        : String.valueOf(msgProp.getRabbitmqPort());
    final int rabbitmqPort = null == rabbitPort ? 32158 : Integer.parseInt(rabbitPort);
    log.info("Rabbitmq port: " + rabbitmqPort);
    msgProp.setRabbitmqPort(rabbitmqPort);
  }

  private void loadRabbitmqManagementPort() {
    final String rabbitmqManagementPort =
        0 == msgProp.getRabbitManagementPort() ? System.getenv("RABBIT_MANAGEMENT_PORT")
            : String.valueOf(msgProp.getRabbitManagementPort());
    final int rabbitManagementPort =
        null == rabbitmqManagementPort ? 32160 : Integer.parseInt(rabbitmqManagementPort);
    log.info("Rabbitmq management port: " + rabbitmqManagementPort);
    msgProp.setRabbitManagementPort(rabbitManagementPort);

  }

  private void loadRabbitmqMessageTTL() {
    final String rabbitTTL =
        0 == msgProp.getRabbitmqMessageTTL() ? System.getenv("RABBIT_MESSAGE_TTL")
            : String.valueOf(msgProp.getRabbitmqMessageTTL());
    final int rabbitmqTTL = null == rabbitTTL ? 60000 : Integer.parseInt(rabbitTTL);
    log.info("Rabbitmq message TTL: " + rabbitmqTTL);
    msgProp.setRabbitmqMessageTTL(rabbitmqTTL);
  }

  private void loadRabbitmqChannelPoolSize() {
    final String rabbitmqChannelPoolSize =
        0 == msgProp.getRabbitmqChannelPoolSize() ? System.getenv("RABBIT_CHANNEL_POOL_SIZE")
            : String.valueOf(msgProp.getRabbitmqChannelPoolSize());
    final int rabbitChannelPoolSize =
        null == rabbitmqChannelPoolSize ? 15 : Integer.parseInt(rabbitmqChannelPoolSize);
    log.info("Rabbitmq channel pool size: " + rabbitmqChannelPoolSize);
    msgProp.setRabbitmqChannelPoolSize(rabbitChannelPoolSize);
  }
}
