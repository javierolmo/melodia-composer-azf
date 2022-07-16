package com.javi.uned.melodiacomposerazf.domain;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Logger;

public class RequestDAO {

    private static final Logger log = Logger.getLogger(RequestDAO.class.getName());
    private static final String connectionString = System.getenv("DATABASE_CONNECTION_STRING");

    public Optional<RequestEntity> update(RequestEntity requestEntity) {
        String query = "UPDATE dbo.sheets SET azf_code = ?, end_date_time = ?, specs = ?, start_date_time = ?, user_id = ?, status = ? WHERE id = ?;";
        log.info("Update database request with id " + requestEntity.getId());
        log.info("New value: " + requestEntity);

        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement updateStatement = connection.prepareStatement(query);
        ){

            updateStatement.setString(1, requestEntity.getAzfCode());
            updateStatement.setTimestamp(2, Timestamp.valueOf(requestEntity.getEndDateTime()));
            updateStatement.setString(3, requestEntity.getSpecs());
            updateStatement.setTimestamp(4, Timestamp.valueOf(requestEntity.getStartDateTime()));
            updateStatement.setLong(5, requestEntity.getUserId());
            updateStatement.setString(6, requestEntity.getStatus());
            updateStatement.setLong(7, requestEntity.getId());
            updateStatement.executeUpdate();
            return selectById(requestEntity.getId());

        } catch (SQLException e) {
            log.severe("Error updating data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<RequestEntity> selectById(long id) throws SQLException {

        String query = "SELECT * FROM dbo.requests WHERE id = ?;";
        log.info("Select data");

        try (
            Connection connection = DriverManager.getConnection(connectionString);
            PreparedStatement selectStatement = connection.prepareStatement(query);
        ) {
            selectStatement.setLong(1, id);
            ResultSet rs = selectStatement.executeQuery();
            if (rs.next()) {
                RequestEntity requestEntity = new RequestEntity();
                requestEntity.setId(rs.getLong("id"));
                requestEntity.setAzfCode(rs.getString("azf_code"));
                requestEntity.setEndDateTime(rs.getTimestamp("end_date_time").toLocalDateTime());
                requestEntity.setSpecs(rs.getString("specs"));
                requestEntity.setStartDateTime(rs.getTimestamp("start_date_time").toLocalDateTime());
                requestEntity.setUserId(rs.getLong("user_id"));
                requestEntity.setStatus(rs.getString("status"));
                return Optional.of(requestEntity);
            } else {
                return Optional.empty();
            }
        }
    }


}
