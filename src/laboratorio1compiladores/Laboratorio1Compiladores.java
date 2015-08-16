/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio1compiladores;

import java.util.Scanner;

/**
 *
 * @author Anahi_Morales
 */
public class Laboratorio1Compiladores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ConvertidorAutomata convAuto = new ConvertidorAutomata();
        ArchivoTexto text = new ArchivoTexto();
        Scanner entrada = new Scanner(System.in);
        
        try {            
            System.out.println("Ingrese la expresión regular");
            String regEx = entrada.nextLine();

            Arbol navidad = new Arbol();
            navidad.setExpresionRegular(regEx);
            Nodo nodoRaiz = navidad.crearNodoRaiz();;

            BobAFNThompson constructor = new BobAFNThompson(regEx);
            double iniTime = System.currentTimeMillis();
            Automata automataAFN = constructor.leerExpresionRegular();
            double totalTime = System.currentTimeMillis() - iniTime;
            System.out.println("Tiempo para hacer el AFN: " + totalTime + " ms");
            
            System.out.println(automataAFN.toString());
            //Simulación de autómata AFN

            text.crearArchivoText("", "AutomataAFN.txt", automataAFN.toString());
            
            iniTime = System.currentTimeMillis();
            Automata automataAFD = convAuto.convertirAFNtoAFD(automataAFN);
            automataAFD.setAlfabeto(automataAFN.getAlfabeto());
            totalTime = System.currentTimeMillis() - iniTime;
            System.out.println("Tiempo para hacer el AFD: " + totalTime + " ms");
            
            System.out.println(automataAFD.toString());
            text.crearArchivoText("", "AutomataAFD.txt", automataAFD.toString());
            
            System.out.println("Ingrese la expresión regular");
            String expresionSimular = entrada.nextLine();
            
            boolean simularAFN = constructor.simular(expresionSimular);
            boolean simularAFD = convAuto.simular(expresionSimular);
            if(simularAFN) {
                System.out.println("La expresión en el AFN fue aceptada");
            } else {
                System.out.println("La expresión en el AFN NO fue aceptada");
            }

            if(simularAFD) {
                System.out.println("La expresión en el AFD fue aceptada");
            } else {
                System.out.println("La expresión en el AFD NO fue aceptada");
            }
            
        } catch (Exception e) {
            System.out.println("Expresión mal escrita");
        }

    }
    
}