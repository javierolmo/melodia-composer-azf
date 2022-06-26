package com.javi.uned.melodiacomposerazf.domain;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Logger;

public class SheetDAO {

    private static final Logger log = Logger.getLogger(SheetDAO.class.getName());

    public SheetEntity update(SheetEntity sheet, Connection connection) throws SQLException {
        log.info("Update data");
        PreparedStatement updateStatement = connection
                .prepareStatement("UPDATE dbo.sheets SET date = ?, finished = ?, name = ?, owner_id = ?, pdf_path = ?, specs_path = ?, xml_path = ? WHERE id = ?;");

        updateStatement.setString(1, sheet.getDate());
        updateStatement.setBoolean(2, sheet.isFinished());
        updateStatement.setString(3, sheet.getName());
        updateStatement.setLong(4, sheet.getOwnerId());
        updateStatement.setString(5, sheet.getPdfPath());
        updateStatement.setString(6, sheet.getSpecsPath());
        updateStatement.setString(7, sheet.getXmlPath());
        updateStatement.setLong(8, sheet.getId());
        updateStatement.executeUpdate();
        Optional<SheetEntity> sheetEntityOptional = selectById(sheet.getId(), connection);
        return sheetEntityOptional.orElse(null);
    }

    public Optional<SheetEntity> selectById(long id, Connection connection) throws SQLException {
        log.info("Select data");
        PreparedStatement selectStatement = connection
                .prepareStatement("SELECT * FROM dbo.sheets WHERE id = ?;");
        selectStatement.setLong(1, id);
        ResultSet rs = selectStatement.executeQuery();
        if (rs.next()) {
            SheetEntity sheet = new SheetEntity();
            sheet.setId(rs.getLong("id"));
            sheet.setDate(rs.getString("date"));
            sheet.setFinished(rs.getBoolean("finished"));
            sheet.setName(rs.getString("name"));
            sheet.setOwnerId(rs.getLong("owner_id"));
            sheet.setPdfPath(rs.getString("pdf_path"));
            sheet.setSpecsPath(rs.getString("specs_path"));
            sheet.setXmlPath(rs.getString("xml_path"));
            return Optional.of(sheet);
        } else {
            return Optional.empty();
        }
    }


}
