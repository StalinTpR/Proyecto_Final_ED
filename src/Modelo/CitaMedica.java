/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Stalin Jimenez
 */
public class CitaMedica {
    
    private String TipoCita;
    private Persona Paciente;
    private Persona Doc;
    private Date fecha;
    private boolean estado;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    public Persona getPaciente() {
        return Paciente;
    }

    public Persona getDoc() {
        return Doc;
    }

    public Date getFecha() {
        return fecha;
    }



    public void setPaciente(Persona Paciente) {
        this.Paciente = Paciente;
    }

    public void setDoc(Persona Doc) {
        this.Doc = Doc;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoCita() {
        return TipoCita;
    }

    public void setTipoCita(String TipoCita) {
        this.TipoCita = TipoCita;
    }
    @Override
    public String toString() {
        return TipoCita;
    }
    
}
