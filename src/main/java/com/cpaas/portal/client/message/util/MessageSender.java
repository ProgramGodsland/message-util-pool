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
package com.cpaas.portal.client.message.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpaas.portal.client.message.producer.Producer;
import com.cpaas.portal.client.message.resource.Message;

/**
 * @author irene
 *
 * 
 */
@Component
public class MessageSender {
  private Logger log = LoggerFactory.getLogger(MessageSender.class);
  
  @Autowired
  private Producer producer;
  
  @Autowired
  private MessageFactory msgFactory;
  
  public void sendNotification(String routingKey, Message message) throws Exception {
    if (null == message.getMessageParams()) {
      throw new Exception("Invalid request");
    }
    try {
      msgFactory.wrapNotificationMessage(message);
      producer.sendMessage(message, routingKey);
    } catch (Exception e) {
      System.err.println("Unable to send message!");
      log.error("Unable to send message exception: {}", e.getMessage());
      e.printStackTrace();
    }
  }
}
