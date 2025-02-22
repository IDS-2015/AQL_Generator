/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 *
 * @author marcelovillalobos
 */
public interface clientEventsListener {
    void onClientAdded();
    void onClientUpdated();
    void onClientUnactivated();
    void onClientActivated();
}