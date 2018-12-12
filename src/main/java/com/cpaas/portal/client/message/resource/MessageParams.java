package com.cpaas.portal.client.message.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageParams extends BaseResource {
  /**
   * 
   */
  private static final long serialVersionUID = -6683270941463458142L;
  private String subscriber;
  private String calledMessageQueue;

  @JsonProperty(required = true)
  private MessageType type;
  private String messageID;

  @JsonProperty(required = true)
  private String transactionID;
  private String originatingMS;
  private String terminatingMS;
  private String authorization;

  public String getAuthorization() {
    return this.authorization;
  }

  public void setAuthorization(final String accessToken) {
    this.authorization = accessToken;
  }

  public String getSubscriber() {
    return this.subscriber;
  }

  public void setSubscriber(final String subscriber) {
    this.subscriber = subscriber;
  }

  public MessageType getType() {
    return this.type;
  }

  public void setType(final MessageType type) {
    this.type = type;
  }

  public String getOriginatingMS() {
    return this.originatingMS;
  }

  public void setOriginatingMS(final String originatingMS) {
    this.originatingMS = originatingMS;
  }

  public String getTerminatingMS() {
    return this.terminatingMS;
  }

  public void setTerminatingMS(final String terminatingMS) {
    this.terminatingMS = terminatingMS;
  }

  public String getCalledMessageQueue() {
    return this.calledMessageQueue;
  }

  public void setCalledMessageQueue(final String calledMessageQueue) {
    this.calledMessageQueue = calledMessageQueue;
  }

  public String getMessageID() {
    return this.messageID;
  }

  public void setMessageID(final String messageID) {
    this.messageID = messageID;
  }

  public String getTransactionID() {
    return this.transactionID;
  }

  public void setTransactionID(final String transactionID) {
    this.transactionID = transactionID;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("\"subscriber\":\"" + this.getSubscriber() + "\",");
    sb.append("\"calledMessageQueue\":\"" + this.getCalledMessageQueue() + "\",");
    sb.append("\"type\":\"" + this.getType() + "\",");
    sb.append("\"messageID\":\"" + this.getMessageID() + "\",");
    sb.append("\"transactionID\":\"" + this.getTransactionID() + "\",");
    sb.append("\"originatingMS\":\"" + this.getOriginatingMS() + "\",");
    sb.append("\"terminatingMS\":\"" + this.getTerminatingMS() + "\",");
    sb.append("\"authorization\":\"" + this.getAuthorization() + "\"");
    sb.append("}");
    return sb.toString();
  }

}
