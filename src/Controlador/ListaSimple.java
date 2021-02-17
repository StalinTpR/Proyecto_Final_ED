/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author Stalin Jimenez
 */
public class ListaSimple {

    public Nodo cabecera;
    public static final int ORDENAR_ASCENDENTE = -1;
    public static final int ORDENAR_DESCENDENTE = 1;

    public ListaSimple() {
        this.cabecera = null;
    }

    public int tamano() {
        int tamano = 0;
        if (!estaVacio()) {
            Nodo tmp = cabecera;
            while (tmp != null) {
                tamano++;
                tmp = tmp.getSig();
            }
        }
        return tamano;
    }

    public void insertar(Object dato) {
        if (tamano() <= this.tamano()) {
            if (!existeCliente(dato)) {
                Nodo tmp = new Nodo(dato,null);
                tmp.setSig(cabecera);
                cabecera=tmp;
            }else{
                System.out.println("esta cedula dañada");
            }
            
        }else{
            System.out.println("La Lista esta Llena");
        }
    }

    public boolean estaVacio() {
        return (this.cabecera == null);//(this.cabecera != null) ? false : true;
    }

    public Object extraer() {
        Object dato = null;

        if (!estaVacio()) {
            dato = cabecera.getDato();
            cabecera = cabecera.getSig();
        }
        return dato;
    }

    public Object obtenerPorPosicion(int pos) {
        Object r = null;
        if (!estaVacio()) {
            Nodo tmp = cabecera;
            for (int i = 0; i < pos; i++) {
                tmp = tmp.getSig();
                if (tmp == null) {
                    break;
                }
            }
            //System.out.println(tmp.getDato());
            if (tmp != null) {
                r = tmp.getDato();
            }
        }
        return r;
    }
    public int buscarindice(Object c){
        int cont = 0;
        if (!estaVacio()) {
            Nodo tmp = cabecera;
            while(tmp != null){
                if (tmp.getDato().toString().equals(c.toString())) {
                    return cont;                    
                }
                tmp = tmp.getSig();
                cont++;
            }
        }
        return cont;    
    }
    public int buscarString(String c){
        int cont = 0;
        if (!estaVacio()) {
            Nodo tmp = cabecera;
            while(tmp != null){
                if (tmp.getDato().toString().equals(c)) {
                    return cont;                    
                }
                tmp = tmp.getSig();
                cont++;
            }
        }
        return cont;    
    }
    
    
    public boolean existeCliente(Object c){
        boolean existe=false;
        if (!estaVacio()) {
            Nodo tmp = cabecera;
            while(tmp != null){
                if (tmp.getDato().toString().equals(c.toString())) {
                    existe = true;
                    break;
                }
                tmp = tmp.getSig();
            }
        }
        return existe;    
    }

    public void verDatos() {
        if (!estaVacio()) {
            Nodo tmp = cabecera;
            while (tmp != null) {
                System.out.print(tmp.getDato().toString() + "\t");
                tmp = tmp.getSig();
            }

        }
        System.out.println("");
    }

    public void insertar(Object dato, int pos) {
        if (estaVacio() || pos <= 0) {
            insertar(dato);
        } else {
            Nodo iterador = cabecera;
            for (int i = 0; i < pos; i++) {
                if (iterador.getSig() == null) {
                    break;
                }
                iterador = iterador.getSig();
            }
            Nodo tmp = new Nodo(dato, iterador.getSig());
            iterador.setSig(tmp);
        }
    }

    public void insertarFinal(Object dato) {
        insertar(dato, tamano() - 1);
    }

    public void editar(int posicion, Object dato) {
        // Verifica si la posición ingresada se encuentre en el rango
        // >= 0 y < que el numero de elementos del la lista.
        if (posicion >= 0 && posicion < tamano()) {
            // Consulta si el nodo a eliminar es el primero.
            if (posicion == 0) {
                // Alctualiza el valor delprimer nodo.
                cabecera.setDato(dato);
            } else {
                // En caso que el nodo a eliminar este por el medio 
                // o sea el ultimo
                Nodo aux = cabecera;
                // Recorre la lista hasta lleger al nodo anterior al eliminar.
                for (int i = 0; i < posicion; i++) {
                    aux = aux.getSig();
                }
                // Alctualiza el valor del nodo.
                aux.setDato(dato);
            }
        }
    }
    //2020-12-12 ---String

    public ListaSimple ordenar(int tipo_ordenacion, String atributo) {
        if (!estaVacio()) {
            for (int i = 0; i < tamano() - 1; i++) {
                int k = i;
                for (int j = i + 1; j < tamano(); j++) {
                    if (Utilies.compareTo(obtenerPorPosicion(j), obtenerPorPosicion(k), atributo) == tipo_ordenacion) {
                        k = j;
                    }
                }
                Object aux = obtenerPorPosicion(i);
                editar(i, obtenerPorPosicion(k));
                editar(k, aux);
            }
        }
        return this;
    }

    public Object busquedaBinaria(String dato, String atributo) {
        System.out.println("DATO " + dato + " ATRIBUTO " + atributo);
        int n = tamano();
        int centro, inf = 0, sup = n - 1;
        while (inf <= sup) {
            centro = (sup + inf) / 2;
            System.out.println("centro " + centro + " ss " + obtenerPorPosicion(centro) + " saa " + dato);
            //if ((String.valueOf(obtenerPorPosicion(centro))).equalsIgnoreCase(dato)) {
            if (Utilies.comparar(dato, obtenerPorPosicion(centro), atributo)) {
                return obtenerPorPosicion(centro);
            } else if (Utilies.compareTo(dato, obtenerPorPosicion(centro), atributo) < 0) {//else if (dato.compareTo(String.valueOf(obtenerPorPosicion(centro))) < 0) {
                sup = centro - 1;
                System.out.println(" sup " + sup);
            } else {
                inf = centro + 1;
                System.out.println("inf " + inf);
            }

        }
        return null;
    }
     

    public ListaSimple ordenar(ListaSimple estudiantes, int tipo_ordenacion, String parametro) {
        estudiantes.ordenar(tipo_ordenacion, parametro);
        return estudiantes;
    }

    public void EliminarporPosicion(int pos) {
        if (!estaVacio()) {
            if (pos < 0) {
                System.out.println("Debe ser una posicion mayor a cerp");
            } else {
                if (pos == 0) {
                    cabecera = cabecera.getSig();
                } else if (pos <= (tamano() - 1)) {
                    Nodo aux = cabecera;
                    for (int i = 0; i < pos - 1; i++) {
                        aux = aux.getSig();
                    }
                    Nodo siguiente = aux.getSig();
                    aux.setSig(siguiente.getSig());
                } else {
                    System.out.println("No se elimino");
                }
            }
        } else {
            System.out.println("Lista Vacia");
        }

    }
   

}
