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
    private Consulta consulta;
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

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

}
