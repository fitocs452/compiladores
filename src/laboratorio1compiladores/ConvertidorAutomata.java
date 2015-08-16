/**
* Universidad del Valle de Guatemala
* Gustavo Adolfo Morales Martínez 13014
* 
* 09-ago-2015
* Descripción:
*/

package laboratorio1compiladores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Anahi_Morales
 */
public class ConvertidorAutomata {
    private Simulacion simulador = new Simulacion();
    private Automata automata = new Automata();
    private String expReg;
    
    public Automata convertirAFNtoAFD(Automata automataAFN) {
        Estado estadoInicial = new Estado();
        estadoInicial.setIdentificador(0);
        automata.setEstadoInicial(estadoInicial);
        automata.addEstado(estadoInicial);
        
        int indexEstados = 1;
        Queue<HashSet> pilaSubconjuntos = new LinkedList();
        ArrayList<Character> alfabeto = automataAFN.getAlfabeto();
        Estado estado_inicial = automataAFN.getEstadoInicial();
        ArrayList<HashSet<Estado>> arrayHashEstados = new ArrayList();
        
        HashSet<Estado> estadosIniciales = simulador.eClosure(estado_inicial);
        pilaSubconjuntos.add(estadosIniciales);
        
        int indexEstadoAnterior = 0;
        while (!pilaSubconjuntos.isEmpty()) {
            HashSet<Estado> estados = pilaSubconjuntos.poll();
            
            for (int i = 0; i < alfabeto.size(); i++) {
                HashSet<Estado> mergeClosure = new HashSet();
                
                HashSet<Estado> estadosMove = simulador.move(estados, String.valueOf(alfabeto.get(i)));
                for (Estado e: estadosMove) {
                    HashSet<Estado> estadosClosure = simulador.eClosure(e);
                    mergeClosure.addAll(estadosClosure);
                }
                
                if (!arrayHashEstados.contains(mergeClosure)) {

                    arrayHashEstados.add(mergeClosure);
                    pilaSubconjuntos.add(mergeClosure);
                    
                    Estado estado = new Estado();
                    estado.setIdentificador(indexEstados);
                    indexEstados++;
                    
                    Transicion t = new Transicion();
                    t.setEstadoInicial(automata.getEstados().get(indexEstadoAnterior));
                    t.setEstadoFinal(estado);
                    t.setSimbolo(String.valueOf(alfabeto.get(i)));
                    automata.getEstados().get(indexEstadoAnterior).addTransicion(t);
                    automata.addEstado(estado);
                    
                    for (Estado e : automataAFN.getEstadosAceptacion()) {
                        if (mergeClosure.contains(e)) {
                            this.automata.addEstadoAceptacion(estado);
                        }
                    }
                }
                else{
                    Transicion t = new Transicion();
                    t.setSimbolo(String.valueOf(alfabeto.get(i)));
                    t.setEstadoInicial(automata.getEstados().get(indexEstadoAnterior));
                    t.setEstadoFinal(automata.getEstados().get(arrayHashEstados.indexOf(mergeClosure) + 1));
                    automata.getEstados().get(indexEstadoAnterior).addTransicion(t);
                }
            }
            indexEstadoAnterior++;
        }
        return this.automata;
    }
    
    public boolean simular (String expReg) {
        Estado estado_inicial = this.automata.getEstadoInicial();
        HashSet<Estado> estados = this.simulador.eClosure(estado_inicial);
        
        for (int i = 0; i < expReg.length(); i++) {    
            HashSet<Estado> estados_finales = new HashSet();
            HashSet<Estado> estados_intermedios = this.simulador.move(estados, String.valueOf(expReg.charAt(i)));
            for (Estado e: estados_intermedios) {
                HashSet<Estado> estadosFin = this.simulador.eClosure(e);
                estados_finales.addAll(estadosFin);
            }
            estados = estados_finales;
        }
        
        boolean aceptado = false;
        for (Estado e: automata.getEstadosAceptacion()) {
            if (estados.contains(e)) {
                aceptado = true;
            }
        }
        return aceptado;
        //5b899329f8
    }

    public Simulacion getSimulador() {
        return simulador;
    }

    public void setSimulador(Simulacion simulador) {
        this.simulador = simulador;
    }

    public Automata getAutomata() {
        return automata;
    }

    public void setAutomata(Automata automata) {
        this.automata = automata;
    }
    
    
}
