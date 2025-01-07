package org.example.dao;

import org.example.commons.MySQLConnection;
import org.example.dto.ApplianceDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ApplianceMySqlDAOImpl implements ApplianceDAO{
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet result;

    @Override
    public List<ApplianceDTO> findAll() {
        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("SELECT code, type, name FROM pokemones");
            result = statement.executeQuery();

            List<ApplianceDTO> applianceList = new ArrayList<>();
            while (result.next()) {
                ApplianceDTO applianceDTO = new ApplianceDTO();
                applianceDTO.setId(result.getInt("id"));
                applianceDTO.setWatts(result.getInt("watts"));
                applianceDTO.setName(result.getString("name"));

                applianceList.add(applianceDTO);
            }
            connection.commit();
            return applianceList;
        }catch (Exception exception) {
            rollback();
            throw new RuntimeException("error to find all pokemones: " + exception.getMessage());
        } finally {
            closeResources();
        }
    }

    @Override
    public ApplianceDTO finById(int id) {
        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("SELECT code, type, name FROM pokemones WHERE code = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();

            ApplianceDTO appliance = new ApplianceDTO();
            if (result.next()) {
                appliance.setId(result.getInt("code"));
                appliance.setWatts(result.getInt("watts"));
                appliance.setName(result.getString("name"));
            }
            connection.commit();
            return appliance;

        } catch (Exception exception) {
            rollback();
            throw new RuntimeException("error to find appliance by code: " + exception.getMessage());
        } finally {
            closeResources();
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement("DELETE FROM pokemones WHERE code = ?");
            statement.setInt(1, id);


            int rowsAffected = statement.executeUpdate();
            connection.commit();


            return rowsAffected > 0;

        } catch (Exception exception) {
            rollback();
            throw new RuntimeException("Error deleting appliance by id: " + exception.getMessage(), exception);
        } finally {
            closeResources();
        }
    }


    @Override
    public boolean supports(Class<?> selectedCass) {
        return false;
    }

    private void rollback() {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (Exception exception) {
            throw new RuntimeException("error to rollback: " + exception.getMessage());
        }
    }

    private void closeResources() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
        } catch (Exception exception) {
            throw new RuntimeException("error to close resources: " + exception.getMessage());
        }
    }
}
