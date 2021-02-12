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
public class RecetaMedica {
     private Long id;
     private Medicamento[] s;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicamento[] getS() {
        return s;
    }

    public void setS(Medicamento[] s) {
        this.s = s;
    }
     
}
