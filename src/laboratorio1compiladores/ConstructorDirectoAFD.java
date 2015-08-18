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
import java.util.TreeSet;

/**
 *
 * @author Anahi_Morales
 */
public class ConstructorDirectoAFD {
    private HashSet<Nodo> nodosArbol;
    private HashMap<Integer, ArrayList<Nodo>> tablaRelaciones;
    
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
            if (nullable(nodo.getNodoDerecho())) {
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
                ArrayList<Nodo> firstPos = firstPos(nodo.getNodoDerecho());
                for (int i = 0; i < firstPos.size(); i++) {
                    if (!nodos.contains(firstPos.get(i))) {
                        nodos.add(firstPos.get(i));
                    }
                }
                return nodos;
            }
        }
        if (simbolo.equals("*")) {
            ArrayList<Nodo> firstPos = firstPos(nodo.getNodoDerecho());
            for (int i = 0; i < firstPos.size(); i++) {
                if (!nodos.contains(firstPos.get(i))) {
                    nodos.add(firstPos.get(i));
                }
            }
            return nodos;
        }
        return nodos;
    }
    
    public HashMap<Integer, ArrayList<Nodo>> followPos(Nodo nodo) {

        String simbolo = nodo.getId();
        if (simbolo.equals("*")) {
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
                tablaRelaciones.put(id, firstPos);
            }
        }
        if (simbolo.equals(".")) {
            ArrayList<Nodo> firstPos = firstPos(nodo.getNodoDerecho());
            ArrayList<Nodo> lastPos = lastPos(nodo.getNodoIzquierdo());
            
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
                tablaRelaciones.put(id, firstPos);
            }
        }
        return tablaRelaciones;
    }
    
    public void numerarNodosArbol(TreeSet<Nodo> nodosArbol) {
        
    }
}
