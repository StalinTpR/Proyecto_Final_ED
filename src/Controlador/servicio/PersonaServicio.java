/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.servicio;




import Listas.ListaSimple;
import controlador.dao.PersonaDao;
import modelo.Persona;

/**
 *
 * @author joe
 */
public class PersonaServicio {
    public static String CEDULA = "cedula";
    public static String APELLIDOS = "apellidos";
    public static String NOMBRES = "nombres";
    public static String EXTERNAL = "external_id";
    public static String IDENTIFICADOR = "id";
    private PersonaDao obj = new PersonaDao();
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
    
    public Persona buscar(String dato, String atributo) {
        ListaSimple personas = personasOrdenar(ListaSimple.ORDENAR_ASCENDENTE, atributo);
        if(personas.tamano() > 0) {
            return (Persona)personas.busquedaBinaria(dato, atributo);
        } else
            return null;
    }
    
}
