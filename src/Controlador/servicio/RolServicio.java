/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.servicio;

import Listas.ListaSimple;
import controlador.dao.RolDao;

import modelo.Rol;

/**
 *
 * @author joe
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
        ListaSimple roles = rolesOrdenar(ListaSimple.ORDENAR_ASCENDENTE, atributo);        
        if(roles.tamano() > 0) {
            return (Rol)roles.busquedaBinaria(dato, atributo);
        } else
            return null;
    }
    
}
