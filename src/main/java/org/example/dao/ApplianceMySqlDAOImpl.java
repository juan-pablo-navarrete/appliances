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
            statement = connection.prepareStatement("SELECT id, watts, name FROM appliance");
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
            throw new RuntimeException("error to find all appliance: " + exception.getMessage());
        } finally {
            closeResources();
        }
    }

    @Override
    public ApplianceDTO findById(int id) {
        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("SELECT id, watts, name FROM appliance WHERE id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();

            ApplianceDTO appliance = null;
            if (result.next()) {
                appliance = new ApplianceDTO();
                appliance.setId(result.getInt("id"));
                appliance.setWatts(result.getInt("watts"));
                appliance.setName(result.getString("name"));
            }
            connection.commit();

            if (appliance==null)
                throw new IllegalArgumentException("No record matching the id was found"+ id);
            return appliance;

        } catch (Exception exception) {
            rollback();
            throw new RuntimeException("error to find appliance by id: " + exception.getMessage());
        } finally {
            closeResources();
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("DELETE FROM appliance WHERE id = ?");
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
        return this.getClass().isAssignableFrom(selectedCass);
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
