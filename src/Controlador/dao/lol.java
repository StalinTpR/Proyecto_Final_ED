/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.dao;

import Modelo.Personal.Departamento;

/**
 *
 * @author Stalin Jimenez
 */
public class lol {
    public static void main(String[] args) {
        Departamento s = new Departamento();
        s.setNomDep("hola");
        DepartamentoDao st = new  DepartamentoDao();
        st.setDep(s);
        st.guardar();
        
    }
}
