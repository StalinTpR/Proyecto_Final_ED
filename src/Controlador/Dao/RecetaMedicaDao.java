/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Controlador.ListaSimple;
import Modelo.Medicamento;
import Modelo.RecetaMedica;

/**
 *
 * @author Stalin Jimenez
 */
public class RecetaMedicaDao extends AdaptadorDao{
    private RecetaMedica RM;
    
    public RecetaMedicaDao() {
        super(new Conexion(), RecetaMedica.class);
    }

    public RecetaMedica getRecetaMedica() {
        if(RM == null)
            RM = new RecetaMedica();
        return RM;
    }
    
    public Boolean guardar() {
        try {
            this.getRecetaMedica().setId(Long.parseLong(String.valueOf(listar().tamano() + 1)));
            this.guardar(this.getRecetaMedica());
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar estudiante "+ e);
            return false;
        }
    }

    public void setRecetaMedica(RecetaMedica rm) {
        this.RM = rm;
    }
    
    public ListaSimple ordenar(ListaSimple estudiantes, int tipo_ordenacion, String atributo) {
        estudiantes.ordenar(tipo_ordenacion, atributo);
        return estudiantes;
    }
    public void crearReceta(){
        Medicamento s = new Medicamento();
        s.setId(Long.parseLong("1"));
        s.setNombre("Propol");
        s.setCantidad(10);
        Medicamento[] st = new Medicamento[1];
        st[0]=s;        
        RecetaMedica lol = new RecetaMedica();
        lol.setId(Long.parseLong("1"));
        lol.setS(st);
        setRecetaMedica(lol);
        guardar();
        setRecetaMedica(null);       
    }
}
