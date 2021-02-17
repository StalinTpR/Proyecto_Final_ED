/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Stalin Jimenez
 */
public class Utilies {
    public static int compareTo(Object o, Object o1, String atributoClase) {
        int i = 0;
        if (o instanceof Integer && o1 instanceof Integer) {
            Integer uno = (Integer) o;
            Integer dos = (Integer) o1;
            if (uno > dos) {
                i = 1;
            } else if (uno < dos) {
                i = -1;
            }

        } else {
            String uno = extracciondato(o, atributoClase);
            String dos = extracciondato(o1, atributoClase);
            if (uno != null && dos != null) {
                if (uno.toUpperCase().compareTo(dos.toUpperCase()) > 0) {
                    i = 1;
                } else {
                    i = -1;
                }
            } 
        }

        /*  if (o instanceof Estudainte && o1 instanceof Estudainte) {
            Estudainte uno = (Estudainte) o;
            Estudainte dos = (Estudainte) o1;
            //i = uno.getApellidos().toUpperCase().compareTo(dos.getApellidos().toUpperCase());
            if (uno.getApellidos().toUpperCase().compareTo(dos.getApellidos().toUpperCase()) > 0) {
                i = 1;
            } else {
                i = -1;
            }
        }

        if (o instanceof Rol && o1 instanceof Rol) {
            Rol uno = (Rol) o;
            Rol dos = (Rol) o1;
            //i = uno.getApellidos().toUpperCase().compareTo(dos.getApellidos().toUpperCase());
            if (uno.getNombre().toUpperCase().compareTo(dos.getNombre().toUpperCase()) > 0) {
                i = 1;
            } else {
                i = -1;
            }
        }*/
        return i;
    }

    public long factorial(long num) {

        if (num == 0) {
            return 1l;
        } else {
            return num * factorial(num - 1);
        }
    }

    public long factorialOther(long num) {
        long numero = 1;

        for (long i = 1; i <= num; i++) {
            numero = numero * i;
            System.out.println("*** " + i);
        }

        return numero;
    }

    private static String extracciondato(Object obj, String atributoClase) {
        Class clase = obj.getClass();
        Field atributo = null;
        Object informacion = null;
        for (Field f : clase.getDeclaredFields()) {
            System.out.println(f.getName().toString());
            if (f.getName().toString().equalsIgnoreCase(atributoClase)) {
                atributo = f;
            }
        }
        if (atributo != null) {
            //  Method metodo = null;
            for (Method metodoAux : clase.getMethods()) {
                if (metodoAux.getName().startsWith("get")) {
                    if (metodoAux.getName().toLowerCase().endsWith(atributo.getName())) {
                        try {
                            informacion = metodoAux.invoke(obj);
                            break;
                        } catch (Exception e) {
                            System.out.println("Error de metodo " + e);
                        }
                    }
                }
            }
        }
        return (informacion != null) ? informacion.toString() : null;
    }

    public static Boolean comparar(String uno, Object obj, String atributoClase) {
        String dos = extracciondato(obj, atributoClase);
        //System.out.println("COMPARAR DATOS " + uno + "   " + dos);
        return (dos != null) ? uno.equals(dos.toString()) : false;
    }
    
    public static int comparareTo(String uno, Object obj, String atributoClase) {
        String dos = extracciondato(obj, atributoClase);
        System.out.println("COMPARAR DATOS " + uno + "   " + dos);
        return (dos != null) ? uno.compareTo(dos.toString()) : -1;
    }

    public Boolean compararRigido(String uno, String dos) {
        return uno.equals(dos);
    }
    public static ListaSimple busquedaSecuencial(ListaSimple lsa, Object dato, String atributoClase) {
        ListaSimple encontrados = new ListaSimple();
        for (int i = 0; i < lsa.tamano(); i++) {
            String uno = extracciondato(lsa.obtenerPorPosicion(i), atributoClase);
            if (uno != null) {
                if (uno.replace(" ", "").compareToIgnoreCase(String.valueOf(dato)) == 0) {
                    encontrados.insertar(lsa.obtenerPorPosicion(i));
                }
            }
        }
        return encontrados;
    }
}
