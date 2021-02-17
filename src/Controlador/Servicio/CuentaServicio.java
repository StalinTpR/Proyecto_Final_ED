/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Servicio;

import Controlador.Dao.CuentaDao;
import Controlador.ListaSimple;
import Controlador.Utilies;
import Modelo.Cuenta;

/**
 *
 * @author Stalin Jimenez
 */
public class CuentaServicio {
    private CuentaDao obj = new CuentaDao();
    public Cuenta getCuenta() {
        return obj.getCuenta();
    }
    
    public Cuenta inicioSesion(String usaurio, String clave) {
        ListaSimple lista = obj.listar();
        Cuenta cuenta = null;
        for (int i = 0; i < lista.tamano(); i++) {
            Cuenta s = (Cuenta) lista.obtenerPorPosicion(i);
            if (s.getUsuario().equals(usaurio)&& s.getClave().equals(clave)) {
                cuenta=s;
            }            
        }
        ListaSimple listaPersonas = Utilies.busquedaSecuencial(lista,cuenta, "usuario");     
        if (listaPersonas.tamano() > 0) {
            Cuenta f = (Cuenta) listaPersonas.obtenerPorPosicion(0);
            return f;
        } else {
            return null;
        }
    }
}
