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
public class Especialidad {
    private Long id;
    private String nombre;
    private Long id_Dep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId_Dep() {
        return id_Dep;
    }

    public void setId_Dep(Long id_Dep) {
        this.id_Dep = id_Dep;
    }

    @Override
    public String toString() {
        return nombre;
    }    
}
