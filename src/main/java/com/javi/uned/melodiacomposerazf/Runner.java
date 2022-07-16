package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.domain.SheetDAO;
import com.javi.uned.melodiacomposerazf.domain.SheetEntity;
import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.javi.uned.melodiacore.exceptions.ExportException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class Runner {

    public static void main(String[] args) throws ExportException, BlobStorageException, IOException, SQLException {

        SheetDAO sheetDAO = new SheetDAO();
        SheetEntity sheetEntity = new SheetEntity();
        sheetEntity.setName("ID TEST");
        System.out.println(sheetDAO.insert(sheetEntity));

    }

}
