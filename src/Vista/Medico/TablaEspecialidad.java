/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Medico;

import Vista.Administrador.*;
import Modelo.Especialidad;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stalin Jimenez
 */
public class TablaEspecialidad extends AbstractTableModel {

    private Especialidad[] esp;

    public Especialidad[] getEsp() {
        return esp;
    }

    public void setEsp(Especialidad[] esp) {
        this.esp = esp;
    }

    @Override
    public int getRowCount() {
        return esp.length;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int i, int j) {
        Especialidad especia = esp[i];
        switch (j) {
            case 0:
                return especia.getNombre();
            default:
                return null;
        }
    }

}
