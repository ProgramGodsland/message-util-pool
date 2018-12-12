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
package com.cpaas.portal.client.message.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author irene
 *
 * 
 */
@Component
public class Consumer {

  protected Logger log = LoggerFactory.getLogger(getClass());

  public void receiveMessage(String message) {
    log.info("Received (String) " + message);
    processMessage(message);
  }

  public void receiveMessage(byte[] message) {
    String strMessage = new String(message);
    log.info("Received (No String) " + strMessage);
    processMessage(strMessage);
  }

  private void processMessage(String message) {
    // try {
    // MailDTO mailDTO = new ObjectMapper().readValue(message, MailDTO.class);
    // ValidationUtil.validateMailDTO(mailDTO);
    // mailServiceImpl.sendMail(mailDTO, null);
    // } catch (JsonParseException e) {
    // log.warn("Bad JSON in message: " + message);
    // } catch (JsonMappingException e) {
    // log.warn("cannot map JSON to NotificationRequest: " + message);
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
  }
}
