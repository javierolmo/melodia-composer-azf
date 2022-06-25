package com.javi.uned.melodiacomposerazf.domain;

public enum MelodiaContainers {
    SHEETS;

    public String getContainerName() {
        return name().toLowerCase();
    }

}
