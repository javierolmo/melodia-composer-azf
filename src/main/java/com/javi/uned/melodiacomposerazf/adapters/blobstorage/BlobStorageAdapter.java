package com.javi.uned.melodiacomposerazf.adapters.blobstorage;

import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.javi.uned.melodiacomposerazf.ports.Storage;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.logging.Logger;

public class BlobStorageAdapter implements Storage {

    private static final Logger log = Logger.getLogger(BlobStorageAdapter.class.getName());

    private CloudBlobClient blobClient;
    private CloudBlobContainer container;

    public BlobStorageAdapter(String storageConnectionString, MelodiaContainers containerRef) throws BlobStorageException {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerRef.getContainerName());
            container.createIfNotExists();

            log.info("BlobStorageAdapter initialized successfully");
        } catch (URISyntaxException | InvalidKeyException | StorageException e) {
            log.info("BlobStorageAdapter initialization failed");
            throw new BlobStorageException("Error initializing BlobStorageAdapter", e);
        }
    }

    public void storeFile(String sourcePath, String targetPath) throws BlobStorageException {
        try {

            CloudBlockBlob blob = container.getBlockBlobReference(targetPath);

            File sourceFile = new File(sourcePath);
            FileInputStream fis = new FileInputStream(sourceFile);

            blob.upload(fis, sourceFile.length());

            fis.close();

            log.info("File " + targetPath + " stored successfully");

        } catch (IOException | StorageException | URISyntaxException e) {
            log.info("File " + targetPath + " storage failed");
            throw new BlobStorageException("Error storing file", e);
        }
    }

}
