/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controlador.Dao.DepartamentoDao;
import Controlador.Dao.RolDao;
import Modelo.Departamento;

/**
 *
 * @author Stalin Jimenez
 */
public class StalinJimenezMolina {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DepartamentoDao s = new DepartamentoDao();
        Departamento sta = new Departamento();
        sta.setNombre("Hola");
        s.setDepartamento(sta);
        s.guardar();
        s.setDepartamento(null);
    }
    
}
