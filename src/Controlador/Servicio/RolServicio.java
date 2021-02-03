/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Servicio;

import Controlador.Dao.RolDao;
import Controlador.ListaSimple;
import Modelo.Rol;

/**
 *
 * @author Stalin Jimenez
 */
public class RolServicio {

    public static String IDENTIFICADOR = "id";
    public static String NOMBRE = "nombre";

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

    public Rol buscar(String dato, String atributo) {
        ListaSimple roles = rolesOrdenar(ListaSimple.ORDENAR_DESCENDENTE, atributo);
        if (roles.tamano() > 0) {
            return (Rol) roles.busquedaBinaria(dato, atributo);
        } else {
            return null;
        }
    }
}
