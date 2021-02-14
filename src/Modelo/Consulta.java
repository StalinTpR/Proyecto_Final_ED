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
public class Consulta {

    private Long id;
    private Diagnostico dia;
    private CitaMedica cita;
    private boolean estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Diagnostico getDia() {
        return dia;
    }

    public void setDia(Diagnostico dia) {
        this.dia = dia;
    }

    public CitaMedica getCita() {
        return cita;
    }

    public void setCita(CitaMedica cita) {
        this.cita = cita;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    

}
