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
public class Administrador {

    private long idAdministrador;
    private Long idCuenta;
    private Doctor[] doc;
    private Paciente[] pac;
    private Departamento[] dep;
    private Especialidad[] esp;

    public long getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(long idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Doctor[] getDoc() {
        return doc;
    }

    public void setDoc(Doctor[] doc) {
        this.doc = doc;
    }

    public Paciente[] getPac() {
        return pac;
    }

    public void setPac(Paciente[] pac) {
        this.pac = pac;
    }

    public Departamento[] getDep() {
        return dep;
    }

    public void setDep(Departamento[] dep) {
        this.dep = dep;
    }

    public Especialidad[] getEsp() {
        return esp;
    }

    public void setEsp(Especialidad[] esp) {
        this.esp = esp;
    }
    
}
