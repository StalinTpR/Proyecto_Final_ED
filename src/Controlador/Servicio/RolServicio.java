/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Servicio;

import Controlador.Dao.PersonaDao;
import Controlador.Dao.RolDao;
import Controlador.ListaSimple;
import Controlador.Utilies;
import Modelo.Persona;
import Modelo.Rol;

/**
 *
 * @author Stalin Jimenez
 */
public class RolServicio {

    public static String IDENTIFICADOR = "id";
    public static String NOMBRE = "nombre";
    private RolDao rol = new RolDao();

    private RolDao obj = new RolDao();

    public Rol getRol() {
        return obj.getRol();
    }

    public void crearRoles() {
        obj.crearRoles();
    }

    public ListaSimple rolesOrdenar(int tipo, String atributo) {
        return obj.ordenar(obj.listar(), tipo, atributo);
    }

    public Rol buscar(Long dato, String atributo) {
        rol.crearRoles();
        ListaSimple lista = rol.listar();
        Rol roles = null;
        for (int i = 0; i < lista.tamano(); i++) {
            Rol s = (Rol) lista.obtenerPorPosicion(i);
            if (s.getId()==dato) {
                roles=s;
            }            
        }
        ListaSimple listaRoles = Utilies.busquedaSecuencial(lista,roles,"nombre");        
        if(listaRoles.tamano() > 0) {
            Rol d=(Rol) listaRoles.obtenerPorPosicion(0);
            return d;
        } else
            return null;
    }
}
