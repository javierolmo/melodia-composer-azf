package com.javi.uned.melodiacomposerazf.domain;

public class TonalityDTO {

    private long id;
    private boolean mayor;
    private short alteraciones;
    private String americanName;
    private String traditionalName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMayor() {
        return mayor;
    }

    public void setMayor(boolean mayor) {
        this.mayor = mayor;
    }

    public short getAlteraciones() {
        return alteraciones;
    }

    public void setAlteraciones(short alteraciones) {
        this.alteraciones = alteraciones;
    }

    public String getAmericanName() {
        return americanName;
    }

    public void setAmericanName(String americanName) {
        this.americanName = americanName;
    }

    public String getTraditionalName() {
        return traditionalName;
    }

    public void setTraditionalName(String traditionalName) {
        this.traditionalName = traditionalName;
    }
}
