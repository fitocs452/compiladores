/**
* Universidad del Valle de Guatemala
* Gustavo Adolfo Morales Martínez 13014
* 
* 16-ago-2015
* Descripción:
*/

package laboratorio1compiladores;

import java.util.Stack;

/**
 *
 * @author Anahi_Morales
 */
public class Arbol {
    private RegExConverter convertidor = new RegExConverter();
    private String expresionRegular;
    private Stack<Nodo> pilaNodos = new Stack();
    private Nodo nodoRaiz;
    
    public Nodo crearNodoRaiz() {
        Nodo nodo = new Nodo();
        nodo.setExpresion(expresionRegular);
        crearNodo(nodo);
        return pilaNodos.pop();
    }
    
    public void crearNodo(Nodo nodo) {
        String expresion = nodo.getExpresion();
        if (!expresion.isEmpty()) {
            if (expresion.charAt(0) != '|' &&
                expresion.charAt(0) != '*' &&
                expresion.charAt(0) != '.'
            ) {
                Nodo nodoActual = new Nodo();

                nodoActual.setId(String.valueOf(nodo.getExpresion().charAt(0)));
                nodoActual.setExpresion(nodo.getExpresion().substring(1));
                nodoActual.setIsHoja(true);
                pilaNodos.push(nodoActual);

                crearNodo(nodoActual);
            }
            if (nodo.getExpresion().charAt(0) == '|') {
                Nodo nodoActual = new Nodo();

                nodoActual.setId(String.valueOf(expresion.charAt(0)));
                nodoActual.setExpresion(expresion.substring(1));
                nodoActual.setNodoIzquierdo(pilaNodos.pop());
                nodoActual.setNodoDerecho(pilaNodos.pop());

                pilaNodos.push(nodoActual);
                crearNodo(nodoActual);
            }
            if (nodo.getExpresion().charAt(0) == '*') {
                Nodo nodoActual = new Nodo();

                nodoActual.setId(String.valueOf(nodo.getExpresion().charAt(0)));
                nodoActual.setExpresion(expresion.substring(1));
                nodoActual.setNodoDerecho(pilaNodos.pop());
                
                pilaNodos.push(nodoActual);
                crearNodo(nodoActual);
            }
            if (nodo.getExpresion().charAt(0) == '.') {
                Nodo nodoActual = new Nodo();
                nodoActual.setId(String.valueOf(nodo.getExpresion().charAt(0)));
                nodoActual.setExpresion(expresion.substring(1));
                nodoActual.setNodoDerecho(pilaNodos.pop());
                nodoActual.setNodoIzquierdo(pilaNodos.pop());

                pilaNodos.push(nodoActual);
                crearNodo(nodoActual);
            }
        }    
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = convertidor.realizarConversiones(expresionRegular);
        System.out.println(this.expresionRegular);
    }

    public Nodo getNodoRaiz() {
        return nodoRaiz;
    }

    public void setNodoRaiz(Nodo nodoRaiz) {
        this.nodoRaiz = nodoRaiz;
    }
}
