/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listas;



/**
 *
 * @author joe
 */
public class Nodo {
    private Object dato;
    private Nodo sig;
    
    public Nodo() {
        dato = null;
        sig = null;
    }

    public Nodo(Object dato, Nodo sig) {
        this.dato = dato;
        this.sig = sig;
    }
    
    
    
    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public Nodo getSig() {
        return sig;
    }

    public void setSig(Nodo sig) {
        this.sig = sig;
    }
    
}
