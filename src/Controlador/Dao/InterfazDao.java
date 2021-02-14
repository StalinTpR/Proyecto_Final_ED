/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Controlador.ListaSimple;

/**
 *
 * @author Stalin Jimenez
 */
public interface InterfazDao {
    public void guardar(Object o) throws Exception;
    public Boolean modificar(Object o)throws Exception;
    public void eliminar(int pos)throws Exception;
    public Object Dato(int pss)throws Exception;
    public ListaSimple listar(); 
}
