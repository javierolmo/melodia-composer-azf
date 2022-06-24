package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.adapters.blobstorage.BlobStorageAdapter;
import com.javi.uned.melodiacomposerazf.adapters.blobstorage.MelodiaContainers;
import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.microsoft.azure.storage.StorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import static org.junit.jupiter.api.Assertions.*;

class BlobStorageAdapterTest {

    private BlobStorageAdapter adapter;

    @BeforeEach
    void setUp() {
        try {
            adapter = new BlobStorageAdapter("", MelodiaContainers.SHEETS);
        } catch (BlobStorageException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void storeFile() {
        try {
            adapter.storeFile("src/test/resources/test.txt", "test.txt");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown");
        }
    }
}