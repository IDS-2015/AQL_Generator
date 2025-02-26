/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Collaborator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Database.*;

/**
 *
 * @author marcelovillalobos
 */
public class collaboratorDAO {

    public static collaboratorEntity CollaboratorEntity = new collaboratorEntity();

    public String SQLDriver = "com.mysql.jdbc.Driver";

    public collaboratorDAO() {
    }

    //Method to get all collaborators
    public List<collaboratorEntity> getAllCollaborators() throws SQLException {
        List<collaboratorEntity> collaborators = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM collaborator";

        try {
            Class.forName(SQLDriver);
            connection = DBUtils.getConnection();
            statement = DBUtils.getPreparedStatement(connection, sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                collaboratorEntity collaborator = new collaboratorEntity();
                int collaborator_id = resultSet.getInt("id_collaborator");
                String name_collaborator = resultSet.getString("name_collaborator");
                String type_collaborator = resultSet.getString("type_collaborator");
                String estado = resultSet.getString("status");
                collaborator.setIdCollaborator(collaborator_id);
                collaborator.setNameCollaborator(name_collaborator);
                collaborator.setTypeCollaborator(type_collaborator);
                collaborator.setStatus(estado);
                collaborators.add(collaborator);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return collaborators;
    }

    // Method to CREATE a collaborator
    public void createCollaborator(String name_collaborator, String type_collaborator) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String status = "Active";
        String sql = "INSERT INTO collaborator (name_collaborator, type_collaborator, status) VALUES (?, ?, ?)";

        try {
            Class.forName(SQLDriver);
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false);
            statement = DBUtils.getPreparedStatement(connection, sql);
            statement.setString(1, name_collaborator);
            statement.setString(2, type_collaborator);
            statement.setString(3, status);
            statement.executeUpdate();
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    // Method to UPDATE a collaborator
    public void updateCollaborator(int id, String name_collaborator, String type_collaborator) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "UPDATE collaborator SET name_collaborator = ?, type_collaborator = ? WHERE id_collaborator = ?";

        try {
            Class.forName(SQLDriver);
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false);
            statement = DBUtils.getPreparedStatement(connection, sql);
            statement.setString(1, name_collaborator);
            statement.setString(2, type_collaborator);
            statement.setInt(3, id);
            statement.executeUpdate();
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    // Method to CHANGE STATUS of a collaborator
    private void changeCollaboratorStatus(int id, String status) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "UPDATE collaborator SET status = ? WHERE id_collaborator = ?";

        try {
            Class.forName(SQLDriver);
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false);
            statement = DBUtils.getPreparedStatement(connection, sql);
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.commit();
        } catch (ClassNotFoundException | SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    // Method to DEACTIVATE a collaborator
    public void deactivateCollaborator(int id) throws SQLException {
        changeCollaboratorStatus(id, "Inactive");
    }

    // Method to ACTIVATE a collaborator
    public void activateCollaborator(int id) throws SQLException {
        changeCollaboratorStatus(id, "Active");
    }

    // Method to GET collaborators by status
    public List<collaboratorEntity> getCollaboratorsByStatus(String status) throws SQLException {
        List<collaboratorEntity> collaborators = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM collaborator WHERE status = ?";

        try {
            Class.forName(SQLDriver);
            connection = DBUtils.getConnection();
            statement = DBUtils.getPreparedStatement(connection, sql);
            statement.setString(1, status);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                collaboratorEntity collaborator = new collaboratorEntity();
                collaborator.setIdCollaborator(resultSet.getInt("id_collaborator"));
                collaborator.setNameCollaborator(resultSet.getString("name_collaborator"));
                collaborator.setTypeCollaborator(resultSet.getString("type_collaborator"));
                collaborator.setStatus(resultSet.getString("status"));
                collaborators.add(collaborator);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return collaborators;
    }

    // Method to GET all ACTIVE collaborators
    public List<collaboratorEntity> getActiveCollaborators() throws SQLException {
        return getCollaboratorsByStatus("Active");
    }

    // Method to GET all INACTIVE collaborators
    public List<collaboratorEntity> getInactiveCollaborators() throws SQLException {
        return getCollaboratorsByStatus("Inactive");
    }

}
