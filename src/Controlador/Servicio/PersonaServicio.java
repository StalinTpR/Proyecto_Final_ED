/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Servicio;


import Controlador.Dao.PersonaDao;
import Controlador.ListaSimple;
import Controlador.Utilies;
import Modelo.Persona;


/**
 *
 * @author Stalin Jimenez
 */
public class PersonaServicio {

    public static String CEDULA = "cedula";
    public static String APELLIDOS = "apellidos";
    public static String NOMBRES = "nombres";
    public static String EXTERNAL = "external_id";
    public static String IDENTIFICADOR = "id";
    private PersonaDao obj = new PersonaDao();
    private PersonaDao per = new PersonaDao();

    public Persona getPersona() {
        return obj.getPersona();
    }

    public void fijarPersona(Persona persona) {
        obj.setPersona(persona);
    }

    public Boolean guardar() {
        return obj.guardar();
    }

    public ListaSimple personas() {
        return obj.listar();
    }

    public ListaSimple personasOrdenar(int tipo, String atributo) {
        return obj.ordenar(obj.listar(), tipo, atributo);
    }

    public Persona buscar(Long dato, String atributo) {
        ListaSimple lista = per.listar();
        Persona persona = null;
        for (int i = 0; i < lista.tamano(); i++) {
            Persona s = (Persona) lista.obtenerPorPosicion(i);
            if (s.getId()==dato) {
                persona=s;
            }            
        }
        ListaSimple listaPersonas = Utilies.busquedaSecuencial(per.listar(),persona, "cedula");     
        if (listaPersonas.tamano() > 0) {
            Persona f = (Persona) listaPersonas.obtenerPorPosicion(0);
            return f;
        } else {
            return null;
        }
    }
}
