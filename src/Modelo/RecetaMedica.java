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
     
     private Medicamento[] s;

    

    public Medicamento[] getS() {
        return s;
    }

    public void setS(Medicamento[] s) {
        this.s = s;
    }

    @Override
    public String toString() {
        for (int i = 0; i < s.length; i++) {
            return s[i].getNombre(); 
        }
         return null;        
    }
     
}
