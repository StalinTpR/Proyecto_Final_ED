/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Controlador.ListaSimple;
import Modelo.Persona;

/**
 *
 * @author Stalin Jimenez
 */
public class PersonaDao extends AdaptadorDao{
    private Persona persona;
    
    public PersonaDao() {
        super(new Conexion(), Persona.class);
    }

    public Persona getPersona() {
        if(persona == null)
            persona = new Persona();
        return persona;
    }
    
    public Boolean guardar() {
        try {
            this.getPersona().setId(Long.parseLong(String.valueOf(listar().tamano() + 1)));
            this.guardar(this.getPersona());
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar estudiante "+ e);
            return false;
        }
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public ListaSimple ordenar(ListaSimple estudiantes, int tipo_ordenacion, String atributo) {
        estudiantes.ordenar(tipo_ordenacion, atributo);
        return estudiantes;
    }
}
