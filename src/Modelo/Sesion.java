/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import controlador.servicio.PersonaServicio;
import controlador.servicio.RolServicio;
import modelo.Cuenta;
import modelo.Persona;
import modelo.Rol;

/**
 *
 * @author Stalin Jimenez
 */
public class Sesion {

    private static Cuenta cuenta;
    private static Rol rol;
    private static Persona persona;

    public static Persona getPersona() {
        return persona;
    }

    public static void setPersona(Persona persona) {
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
        persona = new PersonaServicio().buscar(cuenta.getId().toString(), PersonaServicio.IDENTIFICADOR);
        rol = new RolServicio().buscar(persona.getId_rol().toString(), RolServicio.IDENTIFICADOR);
    }
}
