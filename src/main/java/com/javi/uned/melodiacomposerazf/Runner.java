package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.services.ComposerService;
import com.javi.uned.melodiacomposerazf.services.KeyVaultService;
import com.javi.uned.melodiacore.exceptions.ExportException;
import com.javi.uned.melodiacore.io.export.MelodiaExporter;
import com.javi.uned.melodiacore.model.MelodiaScore;

public class Runner {

    public static void main(String[] args) throws ExportException {

        //KeyVaultService keyVaultService = new KeyVaultService();
        //String secret = keyVaultService.getSecret(KeyVaultService.BLOB_STORAGE_CS);

        //System.out.println(secret);
        ComposerService composerService = new ComposerService();
        MelodiaScore melodiaScore = composerService.composeRandom();
        MelodiaExporter.toXML(melodiaScore, "hola/score.musicxml");

    }

}
