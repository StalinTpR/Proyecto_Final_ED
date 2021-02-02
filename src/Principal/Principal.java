/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Controlador.dao.DepartamentoDao;
import Modelo.Personal.Departamento;
import controlador.dao.RolDao;

/**
 *
 * @author Stalin Jimenez
 */
public class Principal {

    public static void main(String[] args) {

        Departamento s = new Departamento();
        DepartamentoDao se3 = new DepartamentoDao();
        se3.setDep(s);
        se3.guardar();

    }
}
