/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Controlador.ListaSimple;
import Modelo.Especialidad;

/**
 *
 * @author Stalin Jimenez
 */
public class EspecialidadDao extends AdaptadorDao{
    private Especialidad esp;
    
    public EspecialidadDao() {
        super(new Conexion(), Especialidad.class);
    }

    public Especialidad getEspecialidad() {
        if (esp == null) {
            esp = new Especialidad();
        }
        return esp;
    }

    public Boolean guardar() {
        try {
            this.getEspecialidad().setId(Long.parseLong(String.valueOf(listar().tamano() + 1)));
            this.guardar(this.getEspecialidad());
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar Especialidad " + e);
            return false;
        }
    }

    public void setEspecialidad(Especialidad dep) {
        this.esp = esp;
    }

    public ListaSimple ordenar(ListaSimple dep, int tipo_ordenacion, String atributo) {
        dep.ordenar(tipo_ordenacion, atributo);
        return dep;
    }
}
