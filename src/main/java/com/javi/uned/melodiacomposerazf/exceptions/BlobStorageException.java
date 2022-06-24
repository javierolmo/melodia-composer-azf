package com.javi.uned.melodiacomposerazf.exceptions;

public class BlobStorageException extends Exception {

    public BlobStorageException(String message) {
        super(message);
    }

    public BlobStorageException() {
        super();
    }

    public BlobStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlobStorageException(Throwable cause) {
        super(cause);
    }

    protected BlobStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
