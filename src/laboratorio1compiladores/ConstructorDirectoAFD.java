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
import java.util.LinkedList;
import java.util.Queue;

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
                firstPos = firstPos(nodo.getNodoDerecho());
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
        
        return generarAutomata(arbol);
    }
    
    public Automata generarEstadosAutomata (Arbol arbol) {
        Automata afd = new Automata();
        setAlfabeto(afd, arbol);
        System.out.println("***********TABLA RELACIONES ******************");
        System.out.println(tablaRelaciones);
        System.out.println("*****************************");
        ArrayList<ArrayList<Nodo>> estados = new ArrayList();
        Queue<ArrayList<Nodo>> colaArrayNodo = new LinkedList();
        
        ArrayList<Integer> tempIds = new ArrayList();
        
        Estado estadoInicial = new Estado();
        estadoInicial.setIdentificador(0);
        afd.setEstadoInicial(estadoInicial);
        afd.addEstado(estadoInicial);
        
        ArrayList<Nodo> firstPosRoot = firstPos(arbol.getNodoRaiz());
        for (Nodo n: firstPosRoot) {
            tempIds.add(n.getIdNodoHoja());
        }
        estadoInicial.setIdNodos(tempIds);
        
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
                
                if (!estados.contains(temp) && !colaArrayNodo.contains(temp)) {
                    Estado anterior = afd.getEstados().get(indexEstadoArray);
                    Estado siguiente = new Estado();
                    afd.addEstado(siguiente);
                    siguiente.setIdentificador(indexEstado);
                    indexEstado++;
                    
                    ArrayList<Transicion> transiciones = anterior.getTransiciones();
                    Transicion t = new Transicion(String.valueOf(c), anterior, siguiente);
                    
                    if (!transiciones.contains(t)) {
                        anterior.addTransicion(t);
                    }
                    
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
                    
                    ArrayList<Transicion> transiciones = anterior.getTransiciones();
                    Transicion t = new Transicion(String.valueOf(c), anterior, siguiente);
                    
                    if (!transiciones.contains(t)) {
                        anterior.addTransicion(t);
                    }
                }
            }
            indexEstadoArray++;
        }
        System.out.println(afd);
        return afd;
    }
    
    public Automata generarAutomata (Arbol arbol) {
        Automata afd = new Automata();
        setAlfabeto(afd, arbol);
        System.out.println("***********TABLA RELACIONES ******************");
        System.out.println(tablaRelaciones);
        System.out.println("*****************************");
        ArrayList<Estado> noMarcados = new ArrayList();
        ArrayList<Estado> marcados = new ArrayList();
        
        ArrayList<Integer> tempIds = new ArrayList();
        
        Estado estadoInicial = new Estado();
        estadoInicial.setIdentificador(0);
        afd.setEstadoInicial(estadoInicial);
        
        ArrayList<Nodo> firstPosRoot = firstPos(arbol.getNodoRaiz());
        
        for (Nodo n: firstPosRoot) {
            tempIds.add(n.getIdNodoHoja());
        }
        System.out.println("FirstPos: " + tempIds);
        estadoInicial.setIdNodos(tempIds);
        noMarcados.add(estadoInicial);
        int indexEstado = 1;
        Estado estadoActual = estadoInicial;
        System.out.println(tablaRelaciones);
        while (!noMarcados.isEmpty()) {
            for (Character c: afd.getAlfabeto()) {
                ArrayList<Integer> ids = estadoActual.getIdNodos();
                System.out.println("Id Estados: " + ids);
                ArrayList<Integer> tempId = new ArrayList();
                for (Integer n: ids) {
                    Nodo nodoId = arbol.getNodoByIdNodoHoja(n);
                    System.out.println("Nodo encontrado: " + nodoId);
                    if (nodoId.getId().equals(String.valueOf(c))) {
                        System.out.println("Tabla relaciones del nodo: " + getArrayIdFollow(tablaRelaciones.get(nodoId.getIdNodoHoja())));
                        tempId = this.mergeArrays(tempId, getArrayIdFollow(tablaRelaciones.get(nodoId.getIdNodoHoja())));
                    }
                }
                System.out.println("Temp id: " + tempId);
                
                if (verificarIgualdadListas(tempId, ids)) {
                    System.out.println("TempId es igual a ids");
                    Transicion t = new Transicion(String.valueOf(c), estadoActual, estadoActual);
                    estadoActual.addTransicion(t);
                    System.out.println("-----------Transicion------------------");
                    System.out.println(t);
                    System.out.println("****************************************");
                } else {
                    boolean contenido = false;
                    for (Estado e: noMarcados) {
                        if (verificarIgualdadListas(e.getIdNodos(), tempId)) {
                            contenido = true;
                            e.addTransicion(new Transicion(String.valueOf(c), estadoActual, e));
                        }
                    }
                    for (Estado e: marcados) {
                        if (verificarIgualdadListas(e.getIdNodos(), tempId)) {
                            contenido = true;
                            e.addTransicion(new Transicion(String.valueOf(c), estadoActual, e));
                        }
                    }
                    if (contenido == false) {
                        Estado estado = new Estado();
                        estado.setIdentificador(indexEstado);
                        estado.setIdNodos(tempId);
                        
                        indexEstado++;
                        estadoActual.addTransicion(new Transicion(String.valueOf(c), estadoActual, estado));
                        
                        noMarcados.add(estado);
                    }
                }
            }
            marcados.add(estadoActual);
            if (!noMarcados.isEmpty()) {
                estadoActual = noMarcados.get(noMarcados.size() - 1);
                noMarcados.remove(noMarcados.size() - 1);
            }
        }
        afd.setEstados(marcados);
        System.out.println("********************Marcados ********************");
        System.out.println(marcados);
        System.out.println("********************Marcados ********************");
        Nodo nNumeral = arbol.getNodoById("#");
        ArrayList<Estado> estadosAceptacion = new ArrayList();
        estadosAceptacion = this.getEstadosAceptacion(marcados, nNumeral);
        afd.setEstadosAceptacion(estadosAceptacion);
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
    
    public ArrayList<Integer> getArrayIdFollow (ArrayList<Nodo> nodos) {
        ArrayList<Integer> returnArray = new ArrayList();
        for (Nodo n: nodos) {
            returnArray.add(n.getIdNodoHoja());
        }
        return returnArray;
    }
    
    public ArrayList<Integer> mergeArrays (ArrayList<Integer> list1, ArrayList<Integer> list2) {
        for (Integer i2: list2){
            if (!list1.contains(i2)) {
                list1.add(i2);
            }
        }
        System.out.println("Merge Arrays --------------------");
        System.out.println(list1);
        System.out.println(list2);
        System.out.println("---------------------------------");
        return list1;
    }
    
    public boolean verificarIgualdadListas (ArrayList<Integer> list1, ArrayList<Integer> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        int contador = 0;
        for (Integer i: list1) {
            if (list2.contains(i)) {
                contador++;
            }
        }
        if (contador == list1.size()) {
            return true;
        }
        return false;
    }
    
    public ArrayList<Estado> getEstadosAceptacion(ArrayList<Estado> marcados, Nodo n) {
        ArrayList<Estado> aceptacion = new ArrayList();
        int nNumeralId = n.getIdNodoHoja();
        for (Estado e: marcados) {
            System.out.println("Nodo marcados: " + e.getIdNodos());
            for (Integer i: e.getIdNodos()) {
                if (i == nNumeralId) {
                    aceptacion.add(e);
                }
            }
        }
        return aceptacion;
    }
    
    public Automata minimizacion(Automata afd) {
        Simulacion simulador = new Simulacion();
        ArrayList<ArrayList<Estado>> particion = new ArrayList();
        
        ArrayList<Estado> estadosNoAceptacion = new ArrayList();
        ArrayList<ArrayList<Integer>> grupoL = new ArrayList();
        
        particion.add(afd.getEstadosAceptacion());

        for (Estado e: afd.getEstados()) {
            if (!afd.getEstadosAceptacion().contains(e)) {
                estadosNoAceptacion.add(e);
            }
        }
        
        particion.add(estadosNoAceptacion);
        System.out.println("******** Primera particion *************");
        System.out.println(particion);
        System.out.println("**************///////*******************");
        boolean salir = true;
        while (salir) {
            ArrayList<ArrayList<Estado>> NParticion = new ArrayList();
            for (ArrayList<Estado> estados: particion) {
                for (Estado estado: estados) {
                    ArrayList<Integer> ds = new ArrayList();
                    for (Character c: afd.getAlfabeto()) {
                        Estado t = simulador.move(estado, String.valueOf(c));
                        for (ArrayList<Estado> grupoH: particion) {
                            if (grupoH.contains(t)) {
                                int index = particion.indexOf(grupoH);
                                if (!ds.contains(index)) {
                                    ds.add(index);
                                    estado.getIdGrupoPertenece().add(index);
                                }
                            }
                        }
                    }
                    if (!grupoL.contains(ds)) {
                        grupoL.add(ds);
                    }
                }
                
                ArrayList<ArrayList<Estado>> dx = new ArrayList();
                for (Estado estado: estados) {
                    ArrayList<Integer> idsGrupoPerteneceEstado = estado.getIdGrupoPertenece();
                    if (dx.contains(idsGrupoPerteneceEstado)) {
                        int i = dx.indexOf(idsGrupoPerteneceEstado);
                        dx.get(i).add(estado);
                    } else {
                        ArrayList<Estado> temporal = new ArrayList();
                        temporal.add(estado);
                        dx.add(temporal);
                    }
                    //ArrayList<Integer> estadosAlcanzados = grupoL.get(i);
                }
                
                for (ArrayList<Estado> states: dx) {
                    NParticion.add(states);
                }
            }
            System.out.println("****************** Particion ******************");
            System.out.println(particion);
            System.out.println("****************** --------- ******************");
            
            System.out.println("****************** Nueva Particion ******************");
            System.out.println(NParticion);
            System.out.println("****************** --------- ******************");
            if (NParticion.equals(particion)) {
                salir = false;
            } else {
                particion = NParticion;
            }
        }
        
        Automata automataMinimizado = new Automata();
        System.out.println("Estados aceptacion: " + afd.getEstadosAceptacion());
        System.out.println("Estado inicial: " + afd.getEstadoInicial());
        HashMap<Estado, Estado> grupoMin = new HashMap();
        int indexEstados = 0;
        int index = 0;
        for (ArrayList<Estado> estados: particion) {
            Estado estado = new Estado();
            estado.setIdentificador(indexEstados);
            indexEstados++;
            System.out.println("*** Estados: " + estados);
            if (estados.contains(afd.getEstadoInicial())) {
                automataMinimizado.setEstadoInicial(estado);
            }
            for (Estado e: afd.getEstadosAceptacion()) {
                if (estados.contains(e)) {
                    automataMinimizado.addEstadoAceptacion(estado);
                }
            }
            automataMinimizado.addEstado(estado);
            for (Estado e: estados) {
                grupoMin.put(e, estado);
            }

            //    for (Transicion t: estados.get(i).getTransiciones()) {
            //        if (!estados.contains(t.getEstadoFinal())) {
                        //estado en la posicion i del automata original
            //            estado.addTransicion(new Transicion(t.getSimbolo(), estado, automataMinimizado.getEstados().get(i)));
            //        }
            //    }
            //}
            index++;
        }
        System.out.println(grupoMin);
        for (int i = 0; i < particion.size(); i++) {
            Estado state = particion.get(i).get(0);
            Estado estadoActual = automataMinimizado.getEstados().get(i);
            
            for (Transicion t: state.getTransiciones()) {
                Estado estadoFinal = grupoMin.get(t.getEstadoFinal());
                estadoActual.addTransicion(new Transicion(t.getSimbolo(), estadoActual, estadoFinal));
            }
        }
        automataMinimizado.setAlfabeto(afd.getAlfabeto());
        System.out.println("*******Automata Min**************");
        System.out.println(automataMinimizado);
        System.out.println("***********--------**************");
        return null;
    }
}
