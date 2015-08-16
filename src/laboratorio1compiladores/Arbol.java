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
    private String expresionRegular;
    private Stack<Nodo> pilaNodos;
    
    public void leerExpresion() {
        for (int i = 0; i < this.expresionRegular.length(); i++) {
            if (expresionRegular.charAt(i) != '|' ||
                expresionRegular.charAt(i) != '*' ||
                expresionRegular.charAt(i) != '.'
            ) {
                Nodo nodo = new Nodo();
                nodo.setId(String.valueOf(expresionRegular.charAt(i)));
                nodo.setExpresion(String.valueOf(expresionRegular.charAt(i + 1)));
            }
            
            if (expresionRegular.charAt(i) == '|') {
            
            }
            if (expresionRegular.charAt(i) == '*') {
            
            }
            if (expresionRegular.charAt(i) == '.') {
            
            }
        }
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = new StringBuffer(expresionRegular).reverse().toString();
    }
}
