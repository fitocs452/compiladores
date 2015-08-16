/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio1compiladores;

/**
 *
 * @author Anahi_Morales
 */
public class Transicion {
    
    private String simbolo;
    private Estado estadoInicial;
    private Estado estadoFinal;

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Estado getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(Estado estadoFinal) {
        this.estadoFinal = estadoFinal;
    }
    
    public String toString() {
        return "(" + estadoInicial + " - " + simbolo + " - " + estadoFinal + ")";
    }
}
