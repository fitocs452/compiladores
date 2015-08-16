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
public class Automata {
    private ArrayList<Estado> estados = new ArrayList();
    private Estado estadoInicial;
    private ArrayList<Estado> estadosAceptacion = new ArrayList();
    private ArrayList<Character> alfabeto = new ArrayList();

    public ArrayList<Estado> getEstados() {
        return estados;
    }
    
    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public ArrayList<Estado> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(ArrayList<Estado> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }
    
    public void addEstado(Estado estado) {
        this.estados.add(estado);
    }
    
    public void addEstadoAceptacion(Estado estado) {
        this.estadosAceptacion.add(estado);
    }

    public ArrayList<Character> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(ArrayList<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }
    
    public void addSimboloAlfabeto(Character simbolo) {
        this.alfabeto.add(simbolo);
    }
    
    public String toString() {
        String data = 
                "\r\n" + "Automata "
                + "\r\n" + "Alfabeto: " + this.alfabeto.toString()
                + "\r\n" + "Estado inicial: " + this.estadoInicial
                + "\r\n" + "Estados: " + this.estados.toString()
                + "\r\n" + "Estado de aceptación: " + this.estadosAceptacion.toString()
                + "\r\n" + "Transiciones: ";
        
        for (int i = 0; i < this.estados.size(); i++) {
            Estado estado = estados.get(i);
            data += estado.getTransiciones();
        }
        data += "\r\n";
        return data;
    }
    
    
}
