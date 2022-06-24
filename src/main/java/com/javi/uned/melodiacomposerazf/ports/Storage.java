package com.javi.uned.melodiacomposerazf.ports;

public interface Storage {

    void storeFile(String sourcePath, String targetPath) throws Exception;

}
