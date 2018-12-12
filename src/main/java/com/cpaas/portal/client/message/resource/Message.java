/**
 *  GENBAND ("GENBAND") CONFIDENTIAL
 *
 *  Copyright (c) 2012-2018 [GENBAND], All Rights Reserved.
 *
 *  NOTICE:  All information contained herein is, and remains the property of GENBAND. The intellectual and technical concepts contained
 *  herein are proprietary to COMPANY and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material is strictly forbidden unless prior written permission is obtained
 *  from GENBAND.  Access to the source code contained herein is hereby forbidden to anyone except current GENBAND employees, managers or contractors who have executed
 *
 *  Confidentiality and Non-disclosure agreements explicitly covering such access.
 *
 *  The copyright notice above does not evidence any actual or intended publication or disclosure  of  this source code, which includes
 *  information that is confidential and/or proprietary, and is a trade secret, of  GENBAND.   ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE,
 *  OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT  THE EXPRESS WRITTEN CONSENT OF COMPANY IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE
 *  LAWS AND INTERNATIONAL TREATIES.  THE RECEIPT OR POSSESSION OF  THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS
 *  TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
 **/
package com.cpaas.portal.client.message.resource;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author irene
 *
 * 
 */
public class Message extends BaseResource{
  /**
   * 
   */
  private static final long serialVersionUID = -5267645904986441677L;
  private String version;
  private String messageRealm;
  private MessageParams messageParams;
  private String serviceAction;

  private JsonNode serviceData;

  public Message(final String version, final String messageRealm, final MessageParams messageParams,
      final String serviceAction, final JsonNode serviceData) {
    super();
    this.version = version;
    this.messageRealm = messageRealm;
    this.messageParams = messageParams;
    this.serviceAction = serviceAction;
    this.serviceData = serviceData;
  }

  public Message() {

  }

  public Message(final Message message) {
    this(message.getVersion(), message.getMessageRealm(), message.getMessageParams(),
        message.getServiceAction(), message.getServiceData());
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getMessageRealm() {
    return messageRealm;
  }

  public void setMessageRealm(String messageRealm) {
    this.messageRealm = messageRealm;
  }

  public MessageParams getMessageParams() {
    return messageParams;
  }

  public void setMessageParams(MessageParams messageParams) {
    this.messageParams = messageParams;
  }

  public String getServiceAction() {
    return serviceAction;
  }

  public void setServiceAction(String serviceAction) {
    this.serviceAction = serviceAction;
  }

  public JsonNode getServiceData() {
    return serviceData;
  }

  public void setServiceData(JsonNode serviceData) {
    this.serviceData = serviceData;
  }
  
}
