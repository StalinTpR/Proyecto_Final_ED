/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Personal;

/**
 *
 * @author Stalin Jimenez
 */
public class Doctor {

    private Long IdDoc;
    private Long IdCuenta;
    private Long IdEsp;
    private Paciente[] lisPac;

    public Long getIdDoc() {
        return IdDoc;
    }

    public void setIdDoc(Long IdDoc) {
        this.IdDoc = IdDoc;
    }

    public Long getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(Long IdCuenta) {
        this.IdCuenta = IdCuenta;
    }

    public Long getIdEsp() {
        return IdEsp;
    }

    public void setIdEsp(Long IdEsp) {
        this.IdEsp = IdEsp;
    }

    public Paciente[] getLisPac() {
        return lisPac;
    }

    public void setLisPac(Paciente[] lisPac) {
        this.lisPac = lisPac;
    }

}
