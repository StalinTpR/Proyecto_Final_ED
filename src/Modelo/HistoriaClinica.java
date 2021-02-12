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
public class HistoriaClinica {

    private long idPaciente;
    private Consulta[] s;

    public long getId() {
        return idPaciente;
    }

    public void setId(long id) {
        this.idPaciente = id;
    }

    public Consulta[] getS() {
        return s;
    }

    public void setS(Consulta[] s) {
        this.s = s;
    }

}
