package Controller;

import Entities.User.userDAO;
import Entities.User.userEntity;
import java.sql.SQLException;
import java.util.List;

public class userController {

    private final userDAO userDao;

    public userController() {
        this.userDao = new userDAO();
    }

    // Get all users
    public List<userEntity> getAllUsers() {
        try {
            return userDao.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get user by name
    public userEntity getUserByName(String name) {
        try {
            return userDao.getUserByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public userEntity validateUser(String username, String password) {
        try {
            userEntity user = userDao.getUserByName(username);
            if (user != null && user.getPasswordUser().equals(password)) {
                return user; // User is authenticated
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Authentication failed
    }

    // Create a new user
    public boolean createUser(String name, String password, String role) {
        try {
            userDao.createUser(name, password, role);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update user information
    public boolean updateUser(int id, String name, String password, String role) {
        try {
            userDao.updateUser(id, name, password, role);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deactivate user
    public boolean deactivateUser(int id) {
        try {
            userDao.deactivateUser(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Activate user
    public boolean activateUser(int id) {
        try {
            userDao.activateUser(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all active users
    public List<userEntity> getActiveUsers() {
        try {
            return userDao.getActiveUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all inactive users
    public List<userEntity> getInactiveUsers() {
        try {
            return userDao.getInactiveUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get users by status
    public List<userEntity> getUsersByStatus(String status) {
        try {
            return userDao.getUsersByStatus(status);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
