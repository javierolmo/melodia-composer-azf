package com.javi.uned.melodiacomposerazf.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacore.model.specs.ScoreSpecsDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Request implements Serializable {

    private Long id;
    private Long userId;
    private String startDateTime;
    private String endDateTime;
    private String azfCode;
    private String specs;
    private String status;
    private Long sheetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getAzfCode() {
        return azfCode;
    }

    public void setAzfCode(String azfCode) {
        this.azfCode = azfCode;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSheetId() {
        return sheetId;
    }

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }

    public ScoreSpecsDTO scoreSpecsDTO () throws JsonProcessingException {
        return new ObjectMapper().readValue(specs, ScoreSpecsDTO.class);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", userId=" + userId +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", azfCode='" + azfCode + '\'' +
                ", specs='" + specs + '\'' +
                ", status='" + status + '\'' +
                ", sheetId=" + sheetId +
                '}';
    }
}
