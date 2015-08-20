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
import java.util.Stack;

/**
 *
 * @author Anahi_Morales
 */
public class Simulacion {
    
    public HashSet<Estado> eClosure(Estado estado) {
        Stack<Estado> estados =  new Stack();
        HashSet<Estado> estadosFinales = new HashSet();
        estados.push(estado);
        estadosFinales.add(estado);
        
        while (!estados.isEmpty()) {
            Estado estado_actual = estados.pop();
            
            for (Transicion t: estado_actual.getTransiciones()) {
                if (t.getSimbolo().equals("€") && !estadosFinales.contains(t.getEstadoFinal())) {
                    estados.push(t.getEstadoFinal());
                    estadosFinales.add(t.getEstadoFinal());
                }
            }
        }
        return estadosFinales;
    }
    
    public HashSet<Estado> move(HashSet<Estado> estados, String simbolo) {
        HashSet<Estado> estados_finales = new HashSet();
        
        for (Estado e: estados) {
            for (Transicion t: e.getTransiciones()) {
                if (t.getSimbolo().equals(simbolo)) {
                    estados_finales.add(t.getEstadoFinal());
                }
            }
        }
        return estados_finales;
    }
    
    public Estado move(Estado estado, String simbolo) {
        Estado estadoAlcanzado = new Estado();
        
        for (Transicion t: estado.getTransiciones()) {
            Estado siguiente = t.getEstadoFinal();
            if (t.getSimbolo().equals(simbolo)) {
                estadoAlcanzado = t.getEstadoFinal();
            }
        }
        return estadoAlcanzado;
    }
}
