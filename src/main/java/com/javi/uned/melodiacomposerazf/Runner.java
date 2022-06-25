package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.domain.MelodiaContainers;
import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.javi.uned.melodiacomposerazf.services.BlobStorageService;
import com.javi.uned.melodiacomposerazf.services.ComposerService;
import com.javi.uned.melodiacomposerazf.services.KeyVaultService;
import com.javi.uned.melodiacore.exceptions.ExportException;
import com.javi.uned.melodiacore.io.export.MelodiaExporter;
import com.javi.uned.melodiacore.model.MelodiaScore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Runner {

    public static void main(String[] args) throws ExportException, BlobStorageException, IOException {

        //KeyVaultService keyVaultService = new KeyVaultService();
        //String secret = keyVaultService.getSecret(KeyVaultService.BLOB_STORAGE_CS);

        //System.out.println(secret);
        File file = new File("hola/score.musicxml");
        file.getParentFile().mkdirs();
        file.createNewFile();

        BlobStorageService blobStorageService = new BlobStorageService(MelodiaContainers.SHEETS);
        blobStorageService.storeFile("hola/score.musicxml", "test/score.musicxml");

    }

}
