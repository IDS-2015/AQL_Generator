/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package documentData;

/**
 *
 * @author marcelovillalobos
 */
public class criteriosInspeccion {

    private String nivelInspeccion;
    private int tamanoMuestra;

    private int cantidadRechazo;
    private int cantidadAceptacion;

    public criteriosInspeccion() {
    }

    public criteriosInspeccion(String nivelInspeccion, int tamanoMuestra, int cantidadRechazo, int cantidadAceptacion) {
        this.nivelInspeccion = nivelInspeccion;
        this.tamanoMuestra = tamanoMuestra;
        this.cantidadRechazo = cantidadRechazo;
        this.cantidadAceptacion = cantidadAceptacion;
    }

    public String getNivelInspeccion() {
        return nivelInspeccion;
    }

    public void setNivelInspeccion(String nivelInspeccion) {
        this.nivelInspeccion = nivelInspeccion;
    }

    public int getTamanoMuestra() {
        return tamanoMuestra;
    }

    public void setTamanoMuestra(int tamanoMuestra) {
        this.tamanoMuestra = tamanoMuestra;
    }

    public int getCantidadRechazo() {
        return cantidadRechazo;
    }

    public void setCantidadRechazo(int cantidadRechazo) {
        this.cantidadRechazo = cantidadRechazo;
    }

    public int getCantidadAceptacion() {
        return cantidadAceptacion;
    }

    public void setCantidadAceptacion(int cantidadAceptacion) {
        this.cantidadAceptacion = cantidadAceptacion;
    }

    
    

}
