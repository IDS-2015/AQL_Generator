/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Collaborator;

/**
 *
 * @author marcelovillalobos
 */
public class collaboratorEntity {
    
    private int idCollaborator;
    private String nameCollaborator;
    private String typeCollaborator;
    private String status;

    public collaboratorEntity() {
    }

    public collaboratorEntity(int idCollaborator, String nameCollaborator, String typeCollaborator, String status) {
        this.idCollaborator = idCollaborator;
        this.nameCollaborator = nameCollaborator;
        this.typeCollaborator = typeCollaborator;
        this.status = status;
    }

    

    public int getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(int idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    public String getNameCollaborator() {
        return nameCollaborator;
    }

    public void setNameCollaborator(String nameCollaborator) {
        this.nameCollaborator = nameCollaborator;
    }

    public String getTypeCollaborator() {
        return typeCollaborator;
    }

    public void setTypeCollaborator(String typeCollaborator) {
        this.typeCollaborator = typeCollaborator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
