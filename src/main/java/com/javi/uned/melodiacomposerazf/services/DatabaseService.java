package com.javi.uned.melodiacomposerazf.services;

import com.javi.uned.melodiacomposerazf.domain.Request;
import com.javi.uned.melodiacomposerazf.domain.RequestDAO;
import com.javi.uned.melodiacomposerazf.domain.SheetDAO;
import com.javi.uned.melodiacomposerazf.domain.SheetEntity;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class DatabaseService {

    private SheetDAO sheetDAO;
    private RequestDAO requestDAO;

    public DatabaseService() {
        this.sheetDAO = new SheetDAO();
        this.requestDAO = new RequestDAO();
    }

    public SheetEntity findSheetById(long id) throws SQLException {
        return sheetDAO.selectById(id).orElseThrow(() -> new SQLException("Sheet with id " + id + " not found"));
    }

    public SheetEntity insertSheet(String movementTitle, int requesterId) throws SQLException {
        SheetEntity sheetEntity = new SheetEntity();
        sheetEntity.setDate(LocalDateTime.now().toString());
        sheetEntity.setName(movementTitle);
        sheetEntity.setOwnerId(requesterId);
        int sheetId = sheetDAO.insert(sheetEntity);
        return sheetDAO.selectById(sheetId).orElseThrow(() -> new SQLException("Sheet not found"));
    }

    public Request selectRequestById(long id) throws SQLException {
        return requestDAO.selectById(id).orElseThrow(() -> new SQLException("Request not found"));
    }
}
