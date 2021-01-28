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
public class Departamento {
    private Long idDepartamento;
    private String NomDep;
    private Especialidad[] esp;

    public Long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNomDep() {
        return NomDep;
    }

    public void setNomDep(String NomDep) {
        this.NomDep = NomDep;
    }

    public Especialidad[] getEsp() {
        return esp;
    }

    public void setEsp(Especialidad[] esp) {
        this.esp = esp;
    }
    
}
