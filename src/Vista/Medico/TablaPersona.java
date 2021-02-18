/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Medico;

import Vista.Administrador.*;
import Controlador.ListaSimple;
import Modelo.Persona;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stalin Jimenez
 */
public class TablaPersona extends AbstractTableModel{
    private ListaSimple s;

    public ListaSimple getS() {
        return s;
    }

    public void setS(ListaSimple s) {
        this.s = s;
    }    

    @Override
    public int getRowCount() {
        return s.tamano();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int j) {
        Persona persona = (Persona) s.obtenerPorPosicion(i);
        switch(j){
            case 0: return persona.getNombres();
            case 1: return persona.getApellidos();
            case 2: return persona.getCedula();
            case 3: return persona.getDireccion();
            default: return null;
        }        
    }
    
}
