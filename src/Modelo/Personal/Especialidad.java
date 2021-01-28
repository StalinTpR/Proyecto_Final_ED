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
public class Especialidad {
    private Long IdEsp;
    private String Nombre;
    private Long IdDep;

    public Long getIdEsp() {
        return IdEsp;
    }

    public void setIdEsp(Long IdEsp) {
        this.IdEsp = IdEsp;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Long getIdDep() {
        return IdDep;
    }

    public void setIdDep(Long IdDep) {
        this.IdDep = IdDep;
    }    
}
