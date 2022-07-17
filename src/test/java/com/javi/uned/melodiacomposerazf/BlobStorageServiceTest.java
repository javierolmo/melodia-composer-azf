package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.services.BlobStorageService;
import com.javi.uned.melodiacomposerazf.domain.MelodiaContainers;
import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlobStorageServiceTest {

    private BlobStorageService adapter;

    @BeforeEach
    void setUp() {
        try {
            adapter = new BlobStorageService(MelodiaContainers.SHEETS);
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