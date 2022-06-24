package com.javi.uned.melodiacomposerazf.adapters.blobstorage;

public enum MelodiaContainers {
    SHEETS;

    public String getContainerName() {
        return name().toLowerCase();
    }

}
