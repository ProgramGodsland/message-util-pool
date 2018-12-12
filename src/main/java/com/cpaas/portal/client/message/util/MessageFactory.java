package com.cpaas.portal.client.message.util;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cpaas.portal.client.message.resource.Message;
import com.cpaas.portal.client.message.resource.MessageParams;
import com.cpaas.portal.client.message.resource.MessageType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Take message as it is and build an message from it
 * 
 * @author irlu
 *
 */
public class MessageFactory {

  private static Logger log = LoggerFactory.getLogger(MessageFactory.class);

  private String defaultQueueName;
  private String originService;
  private final ObjectMapper mapper;

  private MessageFactory() {

    this.mapper = new ObjectMapper();
    this.mapper
        .enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, JsonTypeInfo.As.PROPERTY)
        .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
  }

  private static class LazyHolder {
    static final MessageFactory INSTANCE = new MessageFactory();
  }

  public static MessageFactory getInstance() {
    return LazyHolder.INSTANCE;
  }

  public Message wrapNotificationMessage(final Message msg) {
    msg.getMessageParams().setType(MessageType.notification);
    msg.getMessageParams().setOriginatingMS(this.originService);
    msg.getMessageParams().setMessageID(UUID.randomUUID().toString());
    msg.getMessageParams().setTransactionID(UUID.randomUUID().toString());
    msg.getMessageParams().setCalledMessageQueue(this.defaultQueueName);
    return msg;
  }

  /**
   * 
   * 
   * @param messageRealm internal or external
   * @param act service action, type serviceAction
   * @param serviceData service data
   * @param terminatingMS which service that terminate this action
   * @param subscriber Subscriber
   * @param setupTransactionId Deside if you want to initialize transaction id or not.
   * @return
   */
  public Message buildMessage(final String messageRealm, final String act,
      final JsonNode serviceData, final String terminatingMS, final String subscriber,
      final boolean setupTransactionId) {

    final Message msg = new Message();
    msg.setVersion("1");
    msg.setMessageRealm(messageRealm);
    msg.setServiceAction(act);
    msg.setServiceData(serviceData);

    final MessageParams params = new MessageParams();
    params.setSubscriber(subscriber);
    params.setOriginatingMS(this.originService);
    params.setTerminatingMS(terminatingMS);
    if (setupTransactionId) {
      params.setTransactionID(UUID.randomUUID().toString());
    }

    msg.setMessageParams(params);
    log.info("Build message: {}", msg);
    return msg;
  }

  public Message buildRequestMessage(final String messageRealm, final String act,
      final JsonNode serviceData, final String terminatingMS, final String subscriber,
      final boolean setupTransactionId, final String token) {
    final Message msg = this.buildMessage(messageRealm, act, serviceData, terminatingMS, subscriber,
        setupTransactionId);
    msg.getMessageParams().setAuthorization(token);
    return msg;
  }

  /**
   * 
   * 
   * @param messageRealm internal or external
   * @param act service action, type serviceAction
   * @param serviceData service data
   * @param terminatingMS which service that terminate this action
   * @param subscriber Subscriber
   *
   * 
   * @return
   */
  public Message buildMessage(final String messageRealm, final String act,
      final JsonNode serviceData, final String terminatingMS, final String subscriber) {
    return this.buildMessage(messageRealm, act, serviceData, terminatingMS, subscriber, false);
  }

  public byte[] serializeData(final Message obj) throws IOException {
    byte[] result = null;
    result = this.mapper.setSerializationInclusion(Include.NON_NULL).writeValueAsBytes(obj);
    return result;
  }
  
  public String getDefaultQueueName() {
    return this.defaultQueueName;
  }

  public void setDefaultQueueName(final String defaultQueueName) {
    this.defaultQueueName = defaultQueueName;
  }

  public String getOriginService() {
    return this.originService;
  }

  public void setOriginService(final String originService) {
    this.originService = originService;
  }

  public ObjectMapper getMapper() {
    return this.mapper;
  }


}
