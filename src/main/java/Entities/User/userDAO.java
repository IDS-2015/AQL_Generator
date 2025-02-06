/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.User;

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
public class userDAO {

    public static userEntity UserEntity = new userEntity();

    public String SQLDriver = "com.mysql.jdbc.Driver";

    public userDAO() {
    }

    //Method to get all collaborators
    public List<userEntity> getAllUsers() throws SQLException {
        List<userEntity> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM user";

        try {
            Class.forName(SQLDriver);
            connection = DBUtils.getConnection();
            statement = DBUtils.getPreparedStatement(connection, sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userEntity user = new userEntity();
                int user_id = resultSet.getInt("id_user");
                String name_user = resultSet.getString("name_user");
                String password_user = resultSet.getString("password_user");
                String role_user = resultSet.getString("role_user");
                String estado = resultSet.getString("status");
                user.setIdUser(user_id);
                user.setNameUser(name_user);
                user.setPasswordUser(password_user);
                user.setRoleUser(role_user);
                user.setStatus(estado);
                users.add(user);
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
        return users;
    }

    // Method to CREATE a user
    public void createUser(String name_user, String password_user, String role_user) throws SQLException {
        String status = "Active";
        String sql = "INSERT INTO user (name_user, password_user, role_user, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setString(1, name_user);
            statement.setString(2, password_user);
            statement.setString(3, role_user);
            statement.setString(4, status);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Method to UPDATE a user
    public void updateUser(int id, String name_user, String password_user, String role_user) throws SQLException {
        String sql = "UPDATE user SET name_user = ?, password_user = ?, role_user = ? WHERE id_user = ?";

        try (Connection connection = DBUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setString(1, name_user);
            statement.setString(2, password_user);
            statement.setString(3, role_user);
            statement.setInt(4, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Method to CHANGE STATUS of a user
    private void changeUserStatus(int id, String status) throws SQLException {
        String sql = "UPDATE user SET status = ? WHERE id_user = ?";

        try (Connection connection = DBUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Method to DEACTIVATE a user
    public void deactivateUser(int id) throws SQLException {
        changeUserStatus(id, "Inactive");
    }

    // Method to ACTIVATE a user
    public void activateUser(int id) throws SQLException {
        changeUserStatus(id, "Active");
    }

    // Method to GET users by status
    private List<userEntity> getUsersByStatus(String status) throws SQLException {
        List<userEntity> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE status = ?";

        try (Connection connection = DBUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userEntity user = new userEntity();
                    user.setIdUser(resultSet.getInt("id_user"));
                    user.setNameUser(resultSet.getString("name_user"));
                    user.setPasswordUser(resultSet.getString("password_user"));
                    user.setRoleUser(resultSet.getString("role_user"));
                    user.setStatus(resultSet.getString("status"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }

    // Method to GET a user by name (for login validation)
    public userEntity getUserByName(String name) throws SQLException {
        String sql = "SELECT * FROM user WHERE name_user = ?";

        try (Connection connection = DBUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userEntity user = new userEntity();
                    user.setIdUser(resultSet.getInt("id_user"));
                    user.setNameUser(resultSet.getString("name_user"));
                    user.setPasswordUser(resultSet.getString("password_user"));
                    user.setRoleUser(resultSet.getString("role_user"));
                    user.setStatus(resultSet.getString("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null; // User not found
    }

    // Method to GET all ACTIVE users
    public List<userEntity> getActiveUsers() throws SQLException {
        return getUsersByStatus("Active");
    }

    // Method to GET all INACTIVE users
    public List<userEntity> getInactiveUsers() throws SQLException {
        return getUsersByStatus("Inactive");
    }

}
