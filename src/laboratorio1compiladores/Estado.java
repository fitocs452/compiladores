/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio1compiladores;

import java.util.ArrayList;

/**
 *
 * @author Anahi_Morales
 */
public class Estado {
    private int identificador;
    private ArrayList<Transicion> transiciones = new ArrayList();
    private ArrayList<Integer> idNodos = new ArrayList();
            
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Transicion> transiciones) {
        this.transiciones = transiciones;
    }
    
    public void addTransicion(Transicion transicion) {
        this.transiciones.add(transicion);
    }
    
    public String toString() {
        return String.valueOf(this.identificador) + " Nodos: " + idNodos;
    }

    public ArrayList<Integer> getIdNodos() {
        return idNodos;
    }

    public void setIdNodos(ArrayList<Integer> idNodos) {
        this.idNodos = idNodos;
    }
}
