/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package documentData;

/**
 *
 * @author marcelovillalobos
 */
public class resultadosInspeccion {
    
    private int numeroDefectos;
    private Double porcentajeDefectos;
    private String resultadoFinal;
    private String observaciones;
   

    public resultadosInspeccion() {
    }

    public resultadosInspeccion(int numeroDefectos, Double porcentajeDefectos, String resultadoFinal, String observaciones) {
        this.numeroDefectos = numeroDefectos;
        this.porcentajeDefectos = porcentajeDefectos;
        this.resultadoFinal = resultadoFinal;
        this.observaciones = observaciones;
    }

    public int getNumeroDefectos() {
        return numeroDefectos;
    }

    public void setNumeroDefectos(int numeroDefectos) {
        this.numeroDefectos = numeroDefectos;
    }

    public Double getPorcentajeDefectos() {
        return porcentajeDefectos;
    }

    public void setPorcentajeDefectos(Double porcentajeDefectos) {
        this.porcentajeDefectos = porcentajeDefectos;
    }

    public String getResultadoFinal() {
        return resultadoFinal;
    }

    public void setResultadoFinal(String resultadoFinal) {
        this.resultadoFinal = resultadoFinal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    
    
    
    
}
