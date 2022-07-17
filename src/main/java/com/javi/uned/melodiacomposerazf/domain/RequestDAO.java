package com.javi.uned.melodiacomposerazf.domain;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Logger;

public class RequestDAO {

    private static final Logger log = Logger.getLogger(RequestDAO.class.getName());
    private static final String connectionString = System.getenv("DATABASE_CONNECTION_STRING");

    public Optional<Request> update(Request request) {
        String query = "UPDATE dbo.sheets SET azf_code = ?, end_date_time = ?, specs = ?, start_date_time = ?, user_id = ?, status = ?, sheet_id = ? WHERE id = ?;";
        log.info("Update database request with id " + request.getId());
        log.info("New value: " + request);

        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement updateStatement = connection.prepareStatement(query);
        ){

            updateStatement.setString(1, request.getAzfCode());
            updateStatement.setTimestamp(2, Timestamp.valueOf(request.getEndDateTime()));
            updateStatement.setString(3, request.getSpecs());
            updateStatement.setTimestamp(4, Timestamp.valueOf(request.getStartDateTime()));
            updateStatement.setLong(5, request.getUserId());
            updateStatement.setString(6, request.getStatus());
            updateStatement.setLong(7, request.getId());
            updateStatement.setLong(8, request.getSheetId());
            updateStatement.executeUpdate();
            return selectById(request.getId());

        } catch (SQLException e) {
            log.severe("Error updating data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<Request> selectById(long id) throws SQLException {

        String query = "SELECT * FROM dbo.requests WHERE id = ?;";
        log.info("Select data");

        try (
            Connection connection = DriverManager.getConnection(connectionString);
            PreparedStatement selectStatement = connection.prepareStatement(query);
        ) {
            selectStatement.setLong(1, id);
            ResultSet rs = selectStatement.executeQuery();
            if (rs.next()) {
                Request request = new Request();
                request.setId(rs.getLong("id"));
                request.setAzfCode(rs.getString("azf_code"));
                try {
                    request.setEndDateTime(rs.getTimestamp("end_date_time").toLocalDateTime());
                } catch (NullPointerException e) {
                    request.setEndDateTime(null);
                }
                request.setSpecs(rs.getString("specs"));
                try {
                    request.setStartDateTime(rs.getTimestamp("start_date_time").toLocalDateTime());
                } catch (NullPointerException e) {
                    request.setStartDateTime(null);
                }
                request.setUserId(rs.getLong("user_id"));
                request.setStatus(rs.getString("status"));
                request.setSheetId(rs.getLong("sheet_id"));
                return Optional.of(request);
            } else {
                return Optional.empty();
            }
        }
    }


}
