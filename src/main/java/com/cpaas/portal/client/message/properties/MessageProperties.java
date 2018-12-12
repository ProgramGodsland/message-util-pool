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

/**
 * @author irene
 *
 * 
 */
public class MessageProperties {
  private String rabbitmqServiceName;
  private String rabbitmqHost;
  private String rabbitUsername;
  private String rabbitPasswd;

  private int rabbitmqPort;
  private int rabbitManagementPort;
  private int rabbitmqMessageTTL;
  private int rabbitmqChannelPoolSize;

  public String getRabbitmqServiceName() {
    return rabbitmqServiceName;
  }

  public void setRabbitmqServiceName(String rabbitmqServiceName) {
    this.rabbitmqServiceName = rabbitmqServiceName;
  }

  public String getRabbitmqHost() {
    return rabbitmqHost;
  }

  public void setRabbitmqHost(String rabbitmqHost) {
    this.rabbitmqHost = rabbitmqHost;
  }

  public String getRabbitUsername() {
    return rabbitUsername;
  }

  public void setRabbitUsername(String rabbitUsername) {
    this.rabbitUsername = rabbitUsername;
  }

  public String getRabbitPasswd() {
    return rabbitPasswd;
  }

  public void setRabbitPasswd(String rabbitPasswd) {
    this.rabbitPasswd = rabbitPasswd;
  }

  public int getRabbitmqPort() {
    return rabbitmqPort;
  }

  public void setRabbitmqPort(int rabbitmqPort) {
    this.rabbitmqPort = rabbitmqPort;
  }

  public int getRabbitManagementPort() {
    return rabbitManagementPort;
  }

  public void setRabbitManagementPort(int rabbitManagementPort) {
    this.rabbitManagementPort = rabbitManagementPort;
  }

  public int getRabbitmqMessageTTL() {
    return rabbitmqMessageTTL;
  }

  public void setRabbitmqMessageTTL(int rabbitmqMessageTTL) {
    this.rabbitmqMessageTTL = rabbitmqMessageTTL;
  }

  public int getRabbitmqChannelPoolSize() {
    return rabbitmqChannelPoolSize;
  }

  public void setRabbitmqChannelPoolSize(int rabbitmqChannelPoolSize) {
    this.rabbitmqChannelPoolSize = rabbitmqChannelPoolSize;
  }

}
