/**
* Universidad del Valle de Guatemala
* Gustavo Adolfo Morales Martínez 13014
* 
* 16-ago-2015
* Descripción:
*/

package laboratorio1compiladores;

/**
 *
 * @author Anahi_Morales
 */
public class Nodo {
    private String id;
    private String expresion;
    private Nodo nodoDerecho;
    private Nodo nodoIzquierdo;
    private int idNodoHoja;
    private boolean isHoja;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public Nodo getNodoDerecho() {
        return nodoDerecho;
    }

    public void setNodoDerecho(Nodo nodoDerecho) {
        this.nodoDerecho = nodoDerecho;
    }

    public Nodo getNodoIzquierdo() {
        return nodoIzquierdo;
    }

    public void setNodoIzquierdo(Nodo nodoIzquierdo) {
        this.nodoIzquierdo = nodoIzquierdo;
    }

    public int getIdNodoHoja() {
        return idNodoHoja;
    }

    public void setIdNodoHoja(int idNodoHoja) {
        this.idNodoHoja = idNodoHoja;
    }

    public boolean getIsHoja() {
        return isHoja;
    }

    public void setIsHoja(boolean isHoja) {
        this.isHoja = isHoja;
    }
    
    public String toString() {
        return "idNodo: " + this.id + "IdNodoHoja: " + this.idNodoHoja;
    } 
}
