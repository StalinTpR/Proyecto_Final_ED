/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Controlador.ListaSimple;
import Modelo.Rol;
import java.util.UUID;

/**
 *
 * @author Stalin Jimenez
 */
public class RolDao extends AdaptadorDao{
     private Rol rol;

    public RolDao() {
        super(new Conexion(), Rol.class);
    }

    public Rol getRol() {
        if (rol == null) {
            rol = new Rol();
        }
        return rol;
    }

    public Boolean guardar() {
        try {
            this.getRol().setId(Long.parseLong(String.valueOf(listar().tamano() + 1)));
            this.guardar(this.getRol());
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar rol " + e);
            return false;
        }
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public ListaSimple ordenar(ListaSimple estudiantes, int tipo_ordenacion, String atributo) {
        estudiantes.ordenar(tipo_ordenacion, atributo);
        return estudiantes;
    }

    public void crearRoles() {        
        if (listar().tamano() == 0) {
            Rol admin = new Rol();
            admin.setNombre("Administrador");
            setRol(admin);
            guardar();            
            setRol(null);
            Rol Doc = new Rol();
            Doc.setNombre("Doctor");
            setRol(Doc);
            guardar();
            setRol(null);
            Rol pac = new Rol();
            pac.setNombre("Paciente");
            setRol(pac);
            guardar();
            setRol(null);                     
        }

    }
}
