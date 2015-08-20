/**
* Universidad del Valle de Guatemala
* Gustavo Adolfo Morales Martínez 13014
* 
* 04-ago-2015
* Descripción:
*/

package laboratorio1compiladores;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Anahi_Morales
 */
public class ArchivoTexto {
    FileWriter archivo = null;
    PrintWriter pw = null;
    
    public void crearArchivoText(String path, String nombreArchivo, String tipo, String texto, String tiempo) {
        try {
            archivo = new FileWriter(path + nombreArchivo);
            System.out.println("Archivo creado");
            pw = new PrintWriter(this.archivo);
            pw.print(tipo + "\r\n" + texto + "\r\n" + "Tiempo que tardó: " + tiempo + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != archivo)
                   archivo.close();
            } catch (Exception e2) {
                   e2.printStackTrace();
            }
        }
    }
}
