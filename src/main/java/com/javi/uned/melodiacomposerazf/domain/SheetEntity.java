package com.javi.uned.melodiacomposerazf.domain;

import java.time.LocalDateTime;

public class SheetEntity {

    private long id;
    private String date;
    private boolean finished;
    private String name;
    private long ownerId;
    private String pdfPath;
    private String specsPath;
    private String xmlPath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getSpecsPath() {
        return specsPath;
    }

    public void setSpecsPath(String specsPath) {
        this.specsPath = specsPath;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }
}
