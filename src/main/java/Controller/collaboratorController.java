/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entities.Collaborator.collaboratorDAO;
import Entities.Collaborator.collaboratorEntity;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author marcelovillalobos
 */
public class collaboratorController {

    private final collaboratorDAO collaboratorDao;

    public collaboratorController() {
        this.collaboratorDao = new collaboratorDAO();
    }

    // Get all collaborators
    public List<collaboratorEntity> getAllCollaborators() {
        try {
            return collaboratorDao.getAllCollaborators();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create a new collaborator
    public boolean createCollaborator(String name, String type) {
        try {
            collaboratorDao.createCollaborator(name, type);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update collaborator
    public boolean updateCollaborator(int id, String name, String type) {
        try {
            collaboratorDao.updateCollaborator(id, name, type);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deactivate collaborator
    public boolean deactivateCollaborator(int id) {
        try {
            collaboratorDao.deactivateCollaborator(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Activate collaborator
    public boolean activateCollaborator(int id) {
        try {
            collaboratorDao.activateCollaborator(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all active collaborators
    public List<collaboratorEntity> getActiveCollaborators() {
        try {
            return collaboratorDao.getActiveCollaborators();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all inactive collaborators
    public List<collaboratorEntity> getInactiveCollaborators() {
        try {
            return collaboratorDao.getInactiveCollaborators();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Obtener colaboradores activos de tipo "collaborator"
    public List<collaboratorEntity> getActiveCollaboratorsByType() {
        try {
            return collaboratorDao.getActiveCollaborators()
                    .stream()
                    .filter(c -> "collaborator".equalsIgnoreCase(c.getTypeCollaborator()))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get collaborators by status
    public List<collaboratorEntity> getCollaboratorsByStatus(String status) {
        try {
            return collaboratorDao.getCollaboratorsByStatus(status);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
