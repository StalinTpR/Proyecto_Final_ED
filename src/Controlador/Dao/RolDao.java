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
        PersonaDao pdao = new PersonaDao();
        if (listar().tamano() == 0) {
            Rol admin = new Rol();
            admin.setNombre("Administrador");
            setRol(admin);
            guardar();
            setRol(null);
            Rol part = new Rol();
            part.setNombre("Particular");
            setRol(part);
            guardar();
            setRol(null);
            if (pdao.listar().tamano() == 0) {
                ListaSimple listado = listar().ordenar(ListaSimple.ORDENAR_ASCENDENTE, "nombre");
                Object objeto = listado.busquedaBinaria("Administrador", "nombre");
                long id_rol = (objeto != null) ? ((Rol) objeto).getId() : -1;
                System.out.println("Se encontro " + objeto);
                pdao.getPersona().setApellidos("Administrador");
                pdao.getPersona().setNombres("Administrador");
                pdao.getPersona().setCedula("2222222222");
                pdao.getPersona().setDireccion("Casa");
                pdao.getPersona().setExternal_id(UUID.randomUUID().toString());
                //pdao.getPersona().setId(pdao.listar().tamano() + 1);
                pdao.getPersona().setId_rol(id_rol);
                pdao.guardar();                
                CuentaDao cdao = new CuentaDao();
                cdao.getCuenta().setClave("1234");
                cdao.getCuenta().setEstado(Boolean.TRUE);
                cdao.getCuenta().setExternal_id(UUID.randomUUID().toString());
                cdao.getCuenta().setId_persona(pdao.getPersona().getId());
                cdao.getCuenta().setUsuario("2222222222");
                cdao.guardar();
                pdao.setPersona(null);
                cdao.setCuenta(null);
            }
        }

    }
}
