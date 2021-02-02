/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Controlador.ListaSimple;
import Modelo.Cuenta;

/**
 *
 * @author Stalin Jimenez
 */
public class CuentaDao extends AdaptadorDao{
    private Cuenta cuenta;
    
    public CuentaDao() {
        super(new Conexion(), Cuenta.class);
    }

    public Cuenta getCuenta() {
        if(cuenta == null)
            cuenta = new Cuenta();
        return cuenta;
    }
    
    public Boolean guardar() {
        try {
            this.getCuenta().setId(Long.parseLong(String.valueOf(listar().tamano() + 1)));
            this.guardar(this.getCuenta());
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar estudiante "+ e);
            return false;
        }
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    public ListaSimple ordenar(ListaSimple estudiantes, int tipo_ordenacion, String parametro) {
        estudiantes.ordenar(tipo_ordenacion, parametro);
        return estudiantes;
    }
    
    public Cuenta inicioSesion(String usuario, String clave) {
        ListaSimple cuentas = listar();
        cuentas = ordenar(cuentas, ListaSimple.ORDENAR_ASCENDENTE, "usuario");
        Cuenta cuenta = (Cuenta) cuentas.busquedaBinaria(usuario, "usuario");//select * from cuenta where usuario = "xxxxx" //inyeccion sql select usuario form cueta where 1 = 1;
        if(cuenta != null) {
            if(!cuenta.getClave().equals(clave)) 
                cuenta = null;            
        }
        return cuenta;
    }    
}
