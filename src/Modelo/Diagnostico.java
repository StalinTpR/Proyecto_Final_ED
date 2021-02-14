/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Stalin Jimenez
 */
public class Diagnostico {
   
    private Enfermedad idEnfermedad;
    private RecetaMedica idRecetaMedica;
    private String Observaciones;

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public Enfermedad getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(Enfermedad idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public RecetaMedica getIdRecetaMedica() {
        return idRecetaMedica;
    }

    public void setIdRecetaMedica(RecetaMedica idRecetaMedica) {
        this.idRecetaMedica = idRecetaMedica;
    }
    
}
