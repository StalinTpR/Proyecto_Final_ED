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
    private long idCita;
    private String TipoCita;
    private Persona Paciente;
    private Persona Doc;
    private Date fecha;

    public long getIdCita() {
        return idCita;
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

    public void setIdCita(long idCita) {
        this.idCita = idCita;
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
    
    
}
