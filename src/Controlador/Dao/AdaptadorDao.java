/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Controlador.ListaSimple;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 *
 * @author Stalin Jimenez
 */
public class AdaptadorDao implements InterfazDao {

    private Conexion conexion;
    private Class clazz;

    public AdaptadorDao(Conexion conexion, Class clazz) {
        this.conexion = conexion;
        this.clazz = clazz;
    }

    @Override
    public void guardar(Object o) throws Exception {
        ListaSimple lista = listar();
        lista.insertar(o);
        conexion.getXtrStream().toXML(lista, new FileOutputStream(conexion.getREPO() + File.separatorChar + clazz.getSimpleName() + ".json"));
    }

    @Override
    public void modificar(Object o, int pos) throws Exception {
        ListaSimple lista = listar();
        lista.editar(pos, o);
        conexion.getXtrStream().toXML(lista, new FileOutputStream(conexion.getREPO() + File.separatorChar + clazz.getSimpleName() + ".json"));
    }

    @Override
    public ListaSimple listar() {
        ListaSimple lista = new ListaSimple();
        try {
            lista = (ListaSimple) conexion.getXtrStream().fromXML(new FileReader(conexion.getREPO() + File.separatorChar + clazz.getSimpleName() + ".json"));
            //Object obj = xtrStream.fromXML(new FileReader(url+File.separatorChar+"horario.json") );

        } catch (Exception e) {
            System.out.println("No se pudo listar " + e);
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void eliminar(int pos) throws Exception {
        ListaSimple lista = listar();
        lista.EliminarporPosicion(pos);
        conexion.getXtrStream().toXML(lista, new FileOutputStream(conexion.getREPO() + File.separatorChar + clazz.getSimpleName() + ".json"));
    }

    @Override
    public Object Dato(int pss) {
        ListaSimple lista = listar();
        return lista.obtenerPorPosicion(pss);
    }

    public ListaSimple ordenar() {
        ListaSimple lista = listar();
        ListaSimple ordenada = new ListaSimple();
        return ordenada = lista.ordenar(lista.ORDENAR_ASCENDENTE, "id");
    }
}
