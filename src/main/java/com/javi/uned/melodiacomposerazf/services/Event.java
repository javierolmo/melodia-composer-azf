package com.javi.uned.melodiacomposerazf.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Event <T>{

    private String id;
    private String subject;
    private T data;
    private String eventType;
    private String dataVersion;
    private String metadataVersion;
    private String eventTime;
    private String topic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public T getData(Class<T> clazz) {
        return new ObjectMapper().convertValue(data, clazz);
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getMetadataVersion() {
        return metadataVersion;
    }

    public void setMetadataVersion(String metadataVersion) {
        this.metadataVersion = metadataVersion;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", data=" + data +
                ", eventType='" + eventType + '\'' +
                ", dataVersion='" + dataVersion + '\'' +
                ", metadataVersion='" + metadataVersion + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
