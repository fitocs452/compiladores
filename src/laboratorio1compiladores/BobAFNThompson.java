/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio1compiladores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 *
 * @author Anahi_Morales
 */
public class BobAFNThompson {
    private RegExConverter convertidor = new RegExConverter(); // Convertidor de infix to postfix con extras + ?
    private Automata automata;  //Automata AFN
    private Stack<Automata> pilaAutomatas =  new Stack();
    private String expresionRegular;
    private Simulacion simulador = new Simulacion();
    
    public BobAFNThompson (String expresion) {
        this.expresionRegular = convertidor.realizarConversiones(expresion);
        System.out.println(this.expresionRegular);
    }
    
    public Automata leerExpresionRegular() {
        //Ciclo que recorre toda la expresión regular en postfix
        for (int i = 0; i < this.expresionRegular.length(); i++) {
            //Se evalúan casos para saber qué autómata crear
            
            /** El primer caso en donde no se haga una operación sino lee
             *  un caractér del alfabeto crea un autómata básico
             */
            if (expresionRegular.charAt(i) != '|' &&
                expresionRegular.charAt(i) != '.' &&
                expresionRegular.charAt(i) != '*'
            ) {
                System.out.println("-----------------------");
                System.out.println("AFN básico");
                automata = afnBasico(expresionRegular, i);
                pilaAutomatas.push(automata);
                System.out.println(automata);
                System.out.println("-----------------------");
            }
            
           if (expresionRegular.charAt(i) == '*') {
               System.out.println("-----------------------");
               System.out.println("AFN Kleen");
               Automata automataEnPila = pilaAutomatas.pop();
               automata = afnKleen(automataEnPila);
               pilaAutomatas.push(automata);
               System.out.println(automata);
               System.out.println("-----------------------");
           }
           
           if (expresionRegular.charAt(i) == '.') {
               System.out.println("-----------------------");
               System.out.println("AFN concatenacion");
               Automata primerAutomata = pilaAutomatas.pop();
               Automata segundoAutomata = pilaAutomatas.pop();
               
               automata = afnConcatenacion(primerAutomata, segundoAutomata);
               pilaAutomatas.push(automata);
               System.out.println(automata);
               System.out.println("-----------------------");
           }
           
           if (expresionRegular.charAt(i) == '|') {
               System.out.println("-----------------------");
               System.out.println("AFN Or");
               Automata segundoAutomata = pilaAutomatas.pop();
               Automata primerAutomata = pilaAutomatas.pop();
               
               automata = afnOr(primerAutomata, segundoAutomata);
               pilaAutomatas.push(automata);
               System.out.println(automata);
               System.out.println("-----------------------");
           }
        }
        
        System.out.println("--------------------------------");
        for (Estado e: automata.getEstados()){
            for (Transicion tran : e.getTransiciones()){
                System.out.println(tran.getEstadoFinal());
                System.out.println( tran.getEstadoFinal().getTransiciones());
            }
        }
        System.out.println("--------------------------------");
        
        this.reconocerAlfabeto(automata);
        return automata;
    }
    
    /** El autómata AFN básico está compuesto de solamente dos estados conectados
     * por una transición, en donde el símbolo que los conecta es el leído
     */
    public Automata afnBasico(String expresionRegular, int i) {
        Automata automata = new Automata();
        
        Estado estadoInicial = new Estado();
        Estado estadoFinal = new Estado();
        
        estadoInicial.setIdentificador(0);
        estadoFinal.setIdentificador(1);
        
        automata.setEstadoInicial(estadoInicial);
        
        automata.addEstado(estadoInicial);
        automata.addEstado(estadoFinal);
        automata.addEstadoAceptacion(estadoFinal);

        Transicion transicion = new Transicion();
        
        transicion.setSimbolo(String.valueOf(expresionRegular.charAt(i)));
        transicion.setEstadoFinal(estadoFinal);
        transicion.setEstadoInicial(estadoInicial);

        estadoInicial.addTransicion(transicion);
        
        return automata;
    }
    
    /** El afn Kleen hace una cerradura sobre el autómata envíado como parámetro
     */
    public Automata afnKleen(Automata automataEnPila) {
        //Se crea un nuevo autómata
        Automata automataKleen = new Automata();

        //Se crean dos estados nuevos, que serán el estado inicial y final del nuevo autómata
        Estado estadoInicial = new Estado();
        Estado estadoFinal = new Estado();

        //Se crean 4 transiciones
        //La primera conecta el nuevo estado inicial con el estado inicial del autómata enviado
        Transicion transEstadoInicial = new Transicion(); 
        //La segundo conecta el nuevo estado final con el estado final del autómata enviado
        Transicion transEstadoFinal = new Transicion();
        /**La tercera conecta nuestro nuevo estado inicial con nuestro nuevo estado final,
         * que indica la aceptación de una cadena vacía
         */
        Transicion transAceptacionVacia = new Transicion();
        
        //La cuarta conecta el estado inicial y el estado final del autómata que indica un ciclo
        Transicion transCiclo = new Transicion();

        //Transicion en donde se acepta la producción de una cadena vacía
        transAceptacionVacia.setSimbolo("€");
        transAceptacionVacia.setEstadoInicial(estadoInicial);
        transAceptacionVacia.setEstadoFinal(estadoFinal);
        estadoInicial.addTransicion(transAceptacionVacia);
        estadoInicial.setIdentificador(0);

        //Segunda transición
        transEstadoInicial.setEstadoInicial(estadoInicial);
        transEstadoInicial.setEstadoFinal(automataEnPila.getEstadoInicial());
        transEstadoInicial.setSimbolo("€");               
        estadoInicial.addTransicion(transEstadoInicial);

        automataEnPila.getEstadoInicial().setIdentificador(1);

        int indexEstados = 2;

        //Agregar estado inicial y estado final al nuevo autómata
        automataKleen.setEstadoInicial(estadoInicial);
        automataKleen.addEstadoAceptacion(estadoFinal);
        automataKleen.addEstado(estadoInicial);
        
        //Se agregan todos los estados del autómata al que se le aplica el kleen al nuevo autómata,
        // sin incluír los estados de aceptación y el estado inicial
        for (int j = 1; j < automataEnPila.getEstados().size() - automataEnPila.getEstadosAceptacion().size(); j++) {
            ArrayList<Estado> estados = automataEnPila.getEstados();
            Estado estadoActual = estados.get(j);
            if (!automataKleen.getEstados().contains(estadoActual)) {
                estadoActual.setIdentificador(indexEstados);
                indexEstados++;
                automataKleen.addEstado(estadoActual);
            }
        }
        
        //Se agrega el estado inicial del autómata sacado de la pila como un estado
        automataKleen.addEstado(automataEnPila.getEstadoInicial());

        //Dado que permito que existan varios estados de aceptación:
        for (int j = 0; j < automataEnPila.getEstadosAceptacion().size(); j++) {  
            //por cada estado final:
            //Trasición en donde se hace el ciclo para repetirse n veces
            transCiclo.setEstadoInicial(automataEnPila.getEstadosAceptacion().get(j));
            transCiclo.setEstadoFinal(automataEnPila.getEstadoInicial());
            transCiclo.setSimbolo("€");

            automataEnPila.getEstadosAceptacion().get(j).addTransicion(transCiclo);
            automataEnPila.getEstadosAceptacion().get(j).setIdentificador(indexEstados);
            indexEstados++;

            //Transición en donde se hacen las conexiones a los nuevos estados inicial y final
            transEstadoFinal.setEstadoInicial(automataEnPila.getEstadosAceptacion().get(j));
            transEstadoFinal.setEstadoFinal(estadoFinal);
            transEstadoFinal.setSimbolo("€");
            automataEnPila.getEstadosAceptacion().get(j).addTransicion(transEstadoFinal);
            
            //Se agrega cada uno de los estados finales del autómata sacado de la pila,
            // como un estado normal de nuestro nuevo autómata
            automataKleen.addEstado(automataEnPila.getEstadosAceptacion().get(j));
        }
        estadoFinal.setIdentificador(indexEstados);
        automataKleen.addEstado(estadoFinal);

        System.out.println(automataKleen);
        
        return automataKleen;
    }
    
    /** El afn Concatenación crea un nuevo autómata con los autómatas concatenados envíados
     */
    public Automata afnConcatenacion(Automata segundoAutomata, Automata primerAutomata) {
        //Se crea el nuevo autómata que contiene la concatenación de los dos autómatas enviados
        Automata automataConcatenado = new Automata();
        //Se crea un nuevo estado final en donde llegan los estados finales del segundo autómata
        Estado estadoFinal = new Estado();
        
        //Se le agrega como estado inicial de nuestro nuevo autómata, el estado inicial del primer
        //autómata a concatenar
        automataConcatenado.setEstadoInicial(primerAutomata.getEstadoInicial());
        automataConcatenado.addEstado(primerAutomata.getEstadoInicial());
        
        int indexEstados = 1;
        
        //Se agregan todos los estados del primer autómata a nuestro nuevo autómata
        for (int i = 1; i < primerAutomata.getEstados().size() - primerAutomata.getEstadosAceptacion().size(); i++) {
            ArrayList<Estado> estados = primerAutomata.getEstados();
            Estado estadoActual = estados.get(i);
            if (!automataConcatenado.getEstados().contains(estadoActual)) {
                System.out.println(estadoActual.getIdentificador());
                estadoActual.setIdentificador(indexEstados);
                indexEstados++;
                automataConcatenado.addEstado(estadoActual);
            }
        }
        
        //Dado que es una concatenación debo realizar  una conexión entre cada uno de los estados,
        // de aceptación del primer autómata hacia el estado inicial del segundo autómata
        for (int i = 0; i < primerAutomata.getEstadosAceptacion().size(); i++) {
            Estado estado = primerAutomata.getEstadosAceptacion().get(i);
            estado.setIdentificador(indexEstados);
            
            indexEstados++;
            //A cada una de las conexiónes de los estados de aceptación del primer autómata
            // se le coloca el símbolo epsilon hacia el estado inicial del segundo autómata
            Transicion transicion = new Transicion();
            transicion.setSimbolo("€");
            transicion.setEstadoInicial(estado);
            transicion.setEstadoFinal(segundoAutomata.getEstadoInicial());
            //Se le agrega la transición al estado de aceptación del primer autómata
            estado.addTransicion(transicion);
            //Se agrega el estado a nuestros estados del nuevo autómata
            automataConcatenado.addEstado(estado);
        }
        
        //Se agrega el estado inicial del segundo autómata a nuestro nuevo autómata
        segundoAutomata.getEstadoInicial().setIdentificador(indexEstados);
        automataConcatenado.addEstado(segundoAutomata.getEstadoInicial());
        indexEstados++;
        
        //Agregamos todos los estados del segundo autómata, exceptuando el estado inicial y los estados de aceptación
        for (int i = 1; i < segundoAutomata.getEstados().size() - segundoAutomata.getEstadosAceptacion().size(); i++) {
            ArrayList<Estado> estados = segundoAutomata.getEstados();
            Estado estadoActual = estados.get(i);
            //Verificamos que el estado no esté agregado
            if (!automataConcatenado.getEstados().contains(estadoActual)) {
                estadoActual.setIdentificador(indexEstados);
                indexEstados++;
                automataConcatenado.addEstado(estadoActual);
            }
        }
        
        // Por cada uno de los estados de aceptación de nuestro segundo autómata se hace una transición
        // a nuestro nuevo estado final
        for (int i = 0; i < segundoAutomata.getEstadosAceptacion().size(); i++) {
            Estado estado = segundoAutomata.getEstadosAceptacion().get(i);
            estado.setIdentificador(indexEstados);
            indexEstados++;
            
            //Las transiciones tienen signo epsilon
            Transicion transAceptacion = new Transicion();
            transAceptacion.setSimbolo("€");
            transAceptacion.setEstadoInicial(estado);
            transAceptacion.setEstadoFinal(estadoFinal);
            
            //Se le agrega la transición al estado
            estado.addTransicion(transAceptacion);
            //se agrega el estado a nuestro autómata
            automataConcatenado.addEstado(estado);
        }
        
        estadoFinal.setIdentificador(indexEstados);
        automataConcatenado.addEstado(estadoFinal);
        automataConcatenado.addEstadoAceptacion(estadoFinal);
        
        System.out.println(automataConcatenado);
        return automataConcatenado;
    }
    
    public Automata afnOr(Automata primerAutomata, Automata segundoAutomata) {
        Automata automataOr = new Automata();
        
        Estado estadoInicial = new Estado();
        Estado estadoFinal = new Estado();
        
        estadoInicial.setIdentificador(0);
        automataOr.addEstado(estadoInicial);
        automataOr.setEstadoInicial(estadoInicial);
        
        Transicion transicionInicial1 = new Transicion();
        transicionInicial1.setSimbolo("€");
        transicionInicial1.setEstadoInicial(estadoInicial);
        transicionInicial1.setEstadoFinal(primerAutomata.getEstadoInicial());
        estadoInicial.addTransicion(transicionInicial1);
        
        Transicion transicionInicial2 = new Transicion();
        transicionInicial2.setSimbolo("€");
        transicionInicial2.setEstadoInicial(estadoInicial);
        transicionInicial2.setEstadoFinal(segundoAutomata.getEstadoInicial());
        estadoInicial.addTransicion(transicionInicial2);
        
        int indexEstados = 1;
        //Primer Autómata del Or
        primerAutomata.getEstadoInicial().setIdentificador(indexEstados);
        automataOr.addEstado(primerAutomata.getEstadoInicial());
        indexEstados++;
        
        for (int i = 0; i < primerAutomata.getEstados().size() - primerAutomata.getEstadosAceptacion().size(); i++) {
            ArrayList<Estado> estados = primerAutomata.getEstados();
            Estado estadoActual = estados.get(i);
            if (!automataOr.getEstados().contains(estadoActual)) {
                estadoActual.setIdentificador(indexEstados);
                indexEstados++;
                automataOr.addEstado(estadoActual);
            }
        }
        
        for (int i = 0; i < primerAutomata.getEstadosAceptacion().size(); i++) {
            Transicion transAceptacion = new Transicion();
            Estado estadoActual = primerAutomata.getEstadosAceptacion().get(i);
            estadoActual.setIdentificador(indexEstados);
            indexEstados++;
            
            transAceptacion.setSimbolo("€");
            transAceptacion.setEstadoInicial(estadoActual);
            transAceptacion.setEstadoFinal(estadoFinal);
            estadoActual.addTransicion(transAceptacion);
            
            automataOr.addEstado(estadoActual);
        }
        
        segundoAutomata.getEstadoInicial().setIdentificador(indexEstados);
        automataOr.addEstado(segundoAutomata.getEstadoInicial());
        indexEstados++;
        
        for (int i = 0; i < segundoAutomata.getEstados().size() - segundoAutomata.getEstadosAceptacion().size(); i++) {
            ArrayList<Estado> estados = segundoAutomata.getEstados();
            Estado estadoActual = estados.get(i);
            if (!automataOr.getEstados().contains(estadoActual)) {
                estadoActual.setIdentificador(indexEstados);
                indexEstados++;
                automataOr.addEstado(estadoActual);
            }
        }
        
        for (int i = 0; i < segundoAutomata.getEstadosAceptacion().size(); i++) {
            Transicion transAceptacion = new Transicion();
            Estado estadoActual = segundoAutomata.getEstadosAceptacion().get(i);
            estadoActual.setIdentificador(indexEstados);
            indexEstados++;
            
            transAceptacion.setSimbolo("€");
            transAceptacion.setEstadoInicial(estadoActual);
            transAceptacion.setEstadoFinal(estadoFinal);
            estadoActual.addTransicion(transAceptacion);
            
            automataOr.addEstado(estadoActual);
        }
        
        estadoFinal.setIdentificador(indexEstados);
        automataOr.addEstado(estadoFinal);
        automataOr.addEstadoAceptacion(estadoFinal);
        
        System.out.println(automataOr);
        return automataOr;
    }
    
    // Se reconoce el alfabeto, lo que hace es recorrer la cadena y por cada
    // caracter distinto y que no sea operación es un nuevo símbolo del alfabeto
    public void reconocerAlfabeto(Automata automata) {
        for (int i = 0; i < this.expresionRegular.length(); i++) {
            
            Character simbolo_actual = expresionRegular.charAt(i);
            ArrayList<Character> alfabeto = automata.getAlfabeto();
            
            if (!alfabeto.contains(simbolo_actual) && 
                    simbolo_actual != '|' &&
                    simbolo_actual != '*' &&
                    simbolo_actual != '.'
                ) {
                automata.addSimboloAlfabeto(simbolo_actual);
            }
        }
    }
    
    // Verifica que una cadena ingresada sea aceptada en el autómata AFN
    public boolean simular (String expReg) {
        //Para simular primero se hace un eClosure del estado inicial
        Estado estado_inicial = this.automata.getEstadoInicial();
        HashSet<Estado> estados = this.simulador.eClosure(estado_inicial);
        
        for (int i = 0; i < expReg.length(); i++) {    
            HashSet<Estado> estados_finales = new HashSet();
            //Se hace un move con el simbolo del alfabeto al subconjuno del eClosure "estados"
            HashSet<Estado> estados_intermedios = this.simulador.move(estados, String.valueOf(expReg.charAt(i)));
            for (Estado e: estados_intermedios) {
                //Se hace un eClosure por cada uno de los estados devueltos luego del move
                HashSet<Estado> estadosFin = this.simulador.eClosure(e);
                //Unimos todos los estados devueltos del move a Hash en donde no se aceptan estados repetidos
                //dado que cada uno de estos subconjuntos, es parte de uno solo
                estados_finales.addAll(estadosFin);
            }
            //se hace recursivo de ésta manera
            estados = estados_finales;
        }
        
        boolean aceptado = false;
        //Se verifica sí el estado de aceptación del autómata se encuentra en el arreglo
        // devuelto de la simulación
        for (Estado e: automata.getEstadosAceptacion()) {
            if (estados.contains(e)) {
                //Sí lo contiene se acepta la expresión
                aceptado = true;
            }
        }
        return aceptado;
        //5b899329f8
    }
}
