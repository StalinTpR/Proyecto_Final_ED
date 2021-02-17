/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Paciente;

import Modelo.CitaMedica;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stalin Jimenez
 */
public class TablaCitasPaciente extends AbstractTableModel{
    private CitaMedica[] sd;

    public CitaMedica[] getSd() {
        return sd;
    }

    public void setSd(CitaMedica[] sd) {
        this.sd = sd;
    }
    

    @Override
    public int getRowCount() {
        return  sd.length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int j) {
        CitaMedica cita = sd[i];
        switch(j){
            case 0: return cita.getTipoCita();
            case 1: return cita.getFecha();
            case 2: return cita.getPaciente().getNombres();
            case 3: return cita.getPaciente().getApellidos();
            case 4: return cita.isEstado();
            default: return null;
        }  
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "Tipo";
            case 1: return "Fecha";
            case 2: return "Nombre";
            case 3: return "Apellido";
            case 4: return "Estado";
            default: return null;
        }
    }
    
}
