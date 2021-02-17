/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.Servicio.PersonaServicio;
import Controlador.Servicio.RolServicio;
import Modelo.Cuenta;
import Modelo.Persona;
import Modelo.Rol;

/**
 *
 * @author Stalin Jimenez
 */
public class Sesion {
    private static Cuenta cuenta;
    private static Rol rol;
    private static Persona persona;

    public  Persona getPersona() {
        return persona;
    }

    public  void setPersona(Persona persona) {
        Sesion.persona = persona;
    }
    

    public static Cuenta getCuenta() {
        return cuenta;
    }

    public static void setCuenta(Cuenta cuenta) {
        Sesion.cuenta = cuenta;
    }

    public static Rol getRol() {
        return rol;
    }

    public static void setRol(Rol rol) {
        Sesion.rol = rol;
    }
    
    public static void cargarDatos() {        
        persona = new PersonaServicio().buscar(cuenta.getId_persona(), PersonaServicio.CEDULA);
        rol = new RolServicio().buscar(persona.getId_rol(), RolServicio.IDENTIFICADOR);
    }

    @Override
    public String toString() {
        return rol.getNombre();
    }
    
}
