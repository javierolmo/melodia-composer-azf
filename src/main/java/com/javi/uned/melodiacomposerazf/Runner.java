package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.javi.uned.melodiacomposerazf.services.DatabaseService;
import com.javi.uned.melodiacore.exceptions.ExportException;

import java.io.IOException;
import java.sql.SQLException;

public class Runner {

    public static void main(String[] args) throws ExportException, BlobStorageException, IOException, SQLException {
        DatabaseService databaseService = new DatabaseService();
        System.out.println(databaseService.findRequestById(1));
    }

}
