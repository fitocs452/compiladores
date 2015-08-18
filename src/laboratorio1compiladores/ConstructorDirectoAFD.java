/**
* Universidad del Valle de Guatemala
* Gustavo Adolfo Morales Martínez 13014
* 
* 16-ago-2015
* Descripción:
*/

package laboratorio1compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;

/**
 *
 * @author Anahi_Morales
 */
public class ConstructorDirectoAFD {
    private HashSet<Nodo> nodosArbol;
    private HashMap<Integer, ArrayList<Nodo>> tablaRelaciones = new HashMap();
    private Automata AFD;
    
    public boolean nullable(Nodo nodo) {
        if (nodo.getId().equals("€") || nodo.getId().equals("*")) {
            return true;
        }
        if (nodo.getId().equals("|")) {
            if (nullable(nodo.getNodoDerecho()) || nullable(nodo.getNodoIzquierdo())) {
                return true;
            }
        }
        if (nodo.getId().equals(".")) {
            if (nullable(nodo.getNodoDerecho()) && nullable(nodo.getNodoIzquierdo())) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Nodo> firstPos(Nodo nodo) {
        ArrayList<Nodo> nodos = new ArrayList();
        System.out.println(nodo);
        System.out.println("FirstPos: " + nodo.getId());
        String simbolo = nodo.getId();
        if (simbolo.equals("€")) {
            return nodos;
        }
        if (!simbolo.equals("|") && !simbolo.equals("*") && !simbolo.equals(".")) {
            if (!nodos.contains(nodo)) {
                nodos.add(nodo);
                return nodos;
            }
        }
        if (simbolo.equals("|")) {
            ArrayList<Nodo> primerFirstPos = firstPos(nodo.getNodoDerecho());
            ArrayList<Nodo> segundoFirstPos = firstPos(nodo.getNodoIzquierdo());
            for (int i = 0; i < primerFirstPos.size(); i++) {
                if (!nodos.contains(primerFirstPos.get(i))) {
                    nodos.add(primerFirstPos.get(i));
                }
            }
            for (int i = 0; i < segundoFirstPos.size(); i++) {
                if (!nodos.contains(segundoFirstPos.get(i))) {
                    nodos.add(segundoFirstPos.get(i));
                }
            }
            return nodos;
        }
        if (simbolo.equals(".")) {
            if (nullable(nodo.getNodoIzquierdo())) {
                ArrayList<Nodo> primerFirstPos = firstPos(nodo.getNodoDerecho());
                ArrayList<Nodo> segundoFirstPos = firstPos(nodo.getNodoIzquierdo());
                for (int i = 0; i < primerFirstPos.size(); i++) {
                    if (!nodos.contains(primerFirstPos.get(i))) {
                        nodos.add(primerFirstPos.get(i));
                    }
                }
                for (int i = 0; i < segundoFirstPos.size(); i++) {
                    if (!nodos.contains(segundoFirstPos.get(i))) {
                        nodos.add(segundoFirstPos.get(i));
                    }
                }
                return nodos;
            } else {
                ArrayList<Nodo> firstPos = firstPos(nodo.getNodoIzquierdo());
                for (int i = 0; i < firstPos.size(); i++) {
                    if (!nodos.contains(firstPos.get(i))) {
                        nodos.add(firstPos.get(i));
                    }
                }
                return nodos;
            }
        }
        if (simbolo.equals("*")) {
            ArrayList<Nodo> firstPos = firstPos(nodo.getNodoIzquierdo());
            for (int i = 0; i < firstPos.size(); i++) {
                if (!nodos.contains(firstPos.get(i))) {
                    nodos.add(firstPos.get(i));
                }
            }
            return nodos;
        }
        return nodos;
    }
    
    public ArrayList<Nodo> lastPos(Nodo nodo) {
        ArrayList<Nodo> nodos = new ArrayList();
        String simbolo = nodo.getId();
        if (simbolo.equals("€")) {
            return nodos;
        }
        if (!simbolo.equals("|") && !simbolo.equals("*") && !simbolo.equals(".")) {
            if (!nodos.contains(nodo)) {
                nodos.add(nodo);
                return nodos;
            }
        }
        if (simbolo.equals("|")) {
            ArrayList<Nodo> primerFirstPos = lastPos(nodo.getNodoDerecho());
            ArrayList<Nodo> segundoFirstPos = lastPos(nodo.getNodoIzquierdo());
            for (int i = 0; i < primerFirstPos.size(); i++) {
                if (!nodos.contains(primerFirstPos.get(i))) {
                    nodos.add(primerFirstPos.get(i));
                }
            }
            for (int i = 0; i < segundoFirstPos.size(); i++) {
                if (!nodos.contains(segundoFirstPos.get(i))) {
                    nodos.add(segundoFirstPos.get(i));
                }
            }
            return nodos;
        }
        if (simbolo.equals(".")) {
            if (nullable(nodo.getNodoDerecho())) {
                ArrayList<Nodo> primerFirstPos = lastPos(nodo.getNodoDerecho());
                ArrayList<Nodo> segundoFirstPos = lastPos(nodo.getNodoIzquierdo());
                for (int i = 0; i < primerFirstPos.size(); i++) {
                    if (!nodos.contains(primerFirstPos.get(i))) {
                        nodos.add(primerFirstPos.get(i));
                    }
                }
                for (int i = 0; i < segundoFirstPos.size(); i++) {
                    if (!nodos.contains(segundoFirstPos.get(i))) {
                        nodos.add(segundoFirstPos.get(i));
                    }
                }
                return nodos;
            } else {
                ArrayList<Nodo> firstPos = lastPos(nodo.getNodoDerecho());
                for (int i = 0; i < firstPos.size(); i++) {
                    if (!nodos.contains(firstPos.get(i))) {
                        nodos.add(firstPos.get(i));
                    }
                }
                return nodos;
            }
        }
        if (simbolo.equals("*")) {
            ArrayList<Nodo> firstPos = lastPos(nodo.getNodoIzquierdo());
            for (int i = 0; i < firstPos.size(); i++) {
                if (!nodos.contains(firstPos.get(i))) {
                    nodos.add(firstPos.get(i));
                }
            }
            return nodos;
        }
        return nodos;
    }
    
    public void followPos(Nodo nodo) {

        String simbolo = nodo.getId();
        System.out.println("Simbolo: " + simbolo);
        if (simbolo.equals("*")) {
            System.out.println("Es kleen");
            ArrayList<Nodo> firstPos = firstPos(nodo);
            ArrayList<Nodo> lastPos = lastPos(nodo);
            //first pos y lastpos del nodo
            for (Nodo n: lastPos) {
                int id = n.getIdNodoHoja();
                if (tablaRelaciones.containsKey(id)) {
                    ArrayList<Nodo> nodosInId = tablaRelaciones.get(id);
                    for (Nodo na: nodosInId) {
                        if (!firstPos.contains(na)) {
                            firstPos.add(na);
                        }
                    }
                }
                System.out.println(firstPos);
                tablaRelaciones.put(id, firstPos);
            }
        }
        if (simbolo.equals(".")) {
            System.out.println("Es concatenacion");
            ArrayList<Nodo> firstPos = firstPos(nodo.getNodoDerecho());
            ArrayList<Nodo> lastPos = lastPos(nodo.getNodoIzquierdo());
            System.out.println("Tabla size: " + tablaRelaciones.size());
            for (Nodo n: lastPos) {
                int id = n.getIdNodoHoja();
                
                if (tablaRelaciones.containsKey(id)) {
                    ArrayList<Nodo> nodosInId = tablaRelaciones.get(id);
                    for (Nodo na: nodosInId) {
                        if (!firstPos.contains(na)) {
                            firstPos.add(na);
                        }
                    }
                }
                System.out.println(firstPos);
                tablaRelaciones.put(id, firstPos);
            }
        }
    }

    public Automata crearAFD_Directo(Arbol arbol) {
        ArrayList<Nodo> nodosArbol = new ArrayList();
        nodosArbol.addAll(arbol.getNodosArbol());

        System.out.println("********* NODOS ARBOL ********************");
        System.out.println(nodosArbol);
        System.out.println("*****************************");
        
        for (Nodo n: nodosArbol) {
            followPos(n);
        }
        
        return generarEstadosAutomata(arbol);
    }
    
    public Automata generarEstadosAutomata (Arbol arbol) {
        Automata afd = new Automata();
        setAlfabeto(afd, arbol);
                System.out.println("***********TABLA RELACIONES ******************");
        System.out.println(tablaRelaciones);
                System.out.println("*****************************");
        ArrayList<ArrayList<Nodo>> estados = new ArrayList();
        Queue<ArrayList<Nodo>> colaArrayNodo = new LinkedList();
        
        Estado estadoInicial = new Estado();
        estadoInicial.setIdentificador(0);
        afd.setEstadoInicial(estadoInicial);
        afd.addEstado(estadoInicial);
        
        //Nodo nodoRaiz = arbol.crearNodoRaiz();
        //arbol.setNodoRaiz(nodoRaiz);
        
        ArrayList<Nodo> firstPosRoot = firstPos(arbol.getNodoRaiz());
        System.out.println("************* firstPosRoot *************");
        System.out.println(firstPosRoot);
        System.out.println("****************************************");
        estados.add(firstPosRoot);
        colaArrayNodo.add(firstPosRoot);
        
        for (Nodo n: firstPosRoot) {
            if (n.getId().equals("#")) {
                afd.addEstadoAceptacion(estadoInicial);
            }
        }
        
        int indexEstado = 1;
        int indexEstadoArray = 0;
        
        while (!colaArrayNodo.isEmpty()) {
            ArrayList<Nodo> arrayActual = colaArrayNodo.poll();
            System.out.println("*********** Array Actual *****************");
            System.out.println(arrayActual);
            System.out.println("**********************************");
            for (Character c: afd.getAlfabeto()) {
                ArrayList<Nodo> temp = new ArrayList();
                
                for (Nodo n: arrayActual) {
                    if (n.getId().equals(String.valueOf(c))) {
                        System.out.println("Index: " + n.getIdNodoHoja());
                        System.out.println("Tamaño tabla: " + tablaRelaciones.size());
                        System.out.println(tablaRelaciones);
                        temp.addAll(tablaRelaciones.get(n.getIdNodoHoja()));
                    }
                }
                
                if (!estados.contains(temp)) {
                    Estado anterior = afd.getEstados().get(indexEstadoArray);
                    Estado siguiente = new Estado();
                    afd.addEstado(siguiente);
                    siguiente.setIdentificador(indexEstado);
                    indexEstado++;
                    
                    anterior.addTransicion(new Transicion(String.valueOf(c), anterior, siguiente));
                    colaArrayNodo.add(temp);
                    estados.add(temp);
                    
                    for (Nodo n: temp){
                        if (n.getId().equals("#"))
                            afd.addEstadoAceptacion(siguiente);
                    }    
                }
                if (estados.contains(temp)) {
                   
                    Estado anterior = afd.getEstados().get(indexEstadoArray);
                    Estado siguiente = afd.getEstados().get(estados.indexOf(temp));
                    anterior.addTransicion(new Transicion(String.valueOf(c),anterior, siguiente));
                }
            }
            indexEstadoArray++;
        }
        System.out.println(afd);
        return afd;
    }
    
    public void setAlfabeto(Automata automata, Arbol arbol) {
        ArrayList<Nodo> nodosArbol = arbol.getNodosArbol();
        for (Nodo n: nodosArbol) {
            if (n.getIsHoja()) {
                Character id = n.getId().toCharArray()[0];
                if (!automata.getAlfabeto().contains(id) && id != '#')
                automata.addSimboloAlfabeto(id);
                System.out.println("Simbolo Alfabeto: " + id);
            }
        }
    }
}
