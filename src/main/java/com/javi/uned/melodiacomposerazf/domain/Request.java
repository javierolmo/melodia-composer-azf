package com.javi.uned.melodiacomposerazf.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacore.model.specs.ScoreSpecsDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Request implements Serializable {

    private long id;
    private long userId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String azfCode;
    private String specs;
    private String status;
    private long sheetId = -1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
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

    public long getSheetId() {
        return sheetId;
    }

    public void setSheetId(long sheetId) {
        this.sheetId = sheetId;
    }

    public ScoreSpecsDTO scoreSpecsDTO () throws JsonProcessingException {
        return new ObjectMapper().readValue(specs, ScoreSpecsDTO.class);
    }


}
