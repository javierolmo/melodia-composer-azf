package com.javi.uned.melodiacomposerazf.domain;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Logger;

public class SheetDAO {

    private static final Logger log = Logger.getLogger(SheetDAO.class.getName());
    private static final String connectionString = System.getenv("DATABASE_CONNECTION_STRING");

    public int insert(SheetEntity sheetEntity) throws SQLException {

        String query = "INSERT INTO sheets (name, date, finished, owner_id, pdf_path, specs_path, xml_path) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, sheetEntity.getName());
            statement.setString(2, sheetEntity.getDate());
            statement.setBoolean(3, sheetEntity.isFinished());
            statement.setLong(4, sheetEntity.getOwnerId());
            statement.setString(5, sheetEntity.getPdfPath());
            statement.setString(6, sheetEntity.getSpecsPath());
            statement.setString(7, sheetEntity.getXmlPath());
            statement.executeUpdate();

            ResultSet tableKeys = statement.getGeneratedKeys();
            tableKeys.next();
            return tableKeys.getInt(1);

        } catch (SQLException e) {
            log.info("Error inserting sheet");
            throw e;
        }
    }

    public Optional<SheetEntity> update(SheetEntity sheet) {
        String query = "UPDATE dbo.sheets SET date = ?, finished = ?, name = ?, owner_id = ?, pdf_path = ?, specs_path = ?, xml_path = ? WHERE id = ?;";
        log.info("Update database sheet with id " + sheet.getId());
        log.info("New value: " + sheet);

        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement updateStatement = connection.prepareStatement(query);
        ){

            updateStatement.setString(1, sheet.getDate());
            updateStatement.setBoolean(2, sheet.isFinished());
            updateStatement.setString(3, sheet.getName());
            updateStatement.setLong(4, sheet.getOwnerId());
            updateStatement.setString(5, sheet.getPdfPath());
            updateStatement.setString(6, sheet.getSpecsPath());
            updateStatement.setString(7, sheet.getXmlPath());
            updateStatement.setLong(8, sheet.getId());
            updateStatement.executeUpdate();
            return selectById(sheet.getId());

        } catch (SQLException e) {
            log.severe("Error updating data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<SheetEntity> selectById(long id) throws SQLException {

        String query = "SELECT * FROM dbo.sheets WHERE id = ?;";
        log.info("Select data");
        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement selectStatement = connection.prepareStatement(query);
        ) {

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


}
