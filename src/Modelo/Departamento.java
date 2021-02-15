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
public class Departamento {

    private Long id;
    private String nombre;
    private Especialidad[] Esp;

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

    public Especialidad[] getEsp() {
        return Esp;
    }

    public void setEsp(Especialidad[] Esp) {
        this.Esp = Esp;
    }
    

    @Override
    public String toString() {
        return nombre;
    }
}
