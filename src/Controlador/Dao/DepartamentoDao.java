/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Controlador.ListaSimple;
import Modelo.Departamento;

/**
 *
 * @author Stalin Jimenez
 */
public class DepartamentoDao  extends AdaptadorDao{
    private Departamento dep;
    
    public DepartamentoDao() {
        super(new Conexion(), Departamento.class);
    }

    public Departamento getDepartamento() {
        if (dep == null) {
            dep = new Departamento();
        }
        return dep;
    }

    public Boolean guardar() {
        try {
            this.getDepartamento().setId(Long.parseLong(String.valueOf(listar().tamano() + 1)));
            this.guardar(this.getDepartamento());
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar Departamento " + e);
            return false;
        }
    }

    public void setDepartamento(Departamento dep) {
        this.dep = dep;
    }

    public ListaSimple ordenar(ListaSimple dep, int tipo_ordenacion, String atributo) {
        dep.ordenar(tipo_ordenacion, atributo);
        return dep;
    }
}
