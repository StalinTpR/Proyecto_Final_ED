/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.servicio;

import controlador.dao.CuentaDao;
import modelo.Cuenta;

/**
 *
 * @author joe
 */
public class CuentaServicio {
    private CuentaDao obj = new CuentaDao();
    public Cuenta getCuenta() {
        return obj.getCuenta();
    }
    
    public Cuenta inicioSesion(String usaurio, String clave) {
        return obj.inicioSesion(usaurio, clave);
    }
    
}
