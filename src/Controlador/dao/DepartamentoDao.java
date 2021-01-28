/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.dao;

import Listas.ListaSimple;
import Modelo.Personal.Departamento;
import controlador.dao.AdaptadorDao;
import controlador.dao.Conexion;

/**
 *
 * @author Stalin Jimenez
 */
public class DepartamentoDao extends AdaptadorDao {

    private Departamento dep;

    public DepartamentoDao() {
        super(new Conexion(), Departamento.class);

    }

    public Departamento getDep() {
        if (dep == null) {
            dep = new Departamento();
        }
        return dep;
    }

    public Boolean guardar() {
        try {
            this.getDep().setIdDepartamento(Long.parseLong(String.valueOf(listar().tamano() + 1)));
            this.guardar(this.getDep());
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar estudiante " + e);
            return false;
        }
    }

    public void setDep(Departamento Dep) {
        this.dep = Dep;
    }

    public ListaSimple ordenar(ListaSimple departamento, int tipo_ordenacion, String atributo) {
        departamento.ordenar(tipo_ordenacion, atributo);
        return departamento;
    }
}
