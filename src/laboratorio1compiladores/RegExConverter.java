/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio1compiladores;

/**
 *
 * @author Anahi_Morales
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class RegExConverter {

	/** Operators precedence map. */
	private static final Map<Character, Integer> precedenceMap;
	static {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		map.put('(', 1);
		map.put('|', 2);
		map.put('.', 3); // explicit concatenation operator
		map.put('?', 4);
		map.put('*', 4);
		map.put('+', 4);
		map.put('^', 5);
		precedenceMap = Collections.unmodifiableMap(map);
	};

	/**
	 * Get character precedence.
	 * 
	 * @param c character
	 * @return corresponding precedence
	 */
	private Integer getPrecedence(Character c) {
		Integer precedence = precedenceMap.get(c);
		return precedence == null ? 6 : precedence;
	}

	/**
	 * Transform regular expression by inserting a '.' as explicit concatenation
	 * operator.
	 */
	private String formatRegEx(String regex) {
		String res = new String();
		List<Character> allOperators = Arrays.asList('|', '?', '+', '*', '^');
		List<Character> binaryOperators = Arrays.asList('^', '|');

		for (int i = 0; i < regex.length(); i++) {
			Character c1 = regex.charAt(i);

			if (i + 1 < regex.length()) {
				Character c2 = regex.charAt(i + 1);

				res += c1;

				if (!c1.equals('(') && !c2.equals(')') && !allOperators.contains(c2) && !binaryOperators.contains(c1)) {
					res += '.';
				}
			}
		}
		res += regex.charAt(regex.length() - 1);

		return res;
	}
        
        public String realizarConversiones(String expresion) {
            String exp = convertirSignoInterrogacion(expresion);
            exp = convertirCerraduraPositiva(exp);
            return infixToPostfix(exp);
        }
	
        /**
	 * Convert regular expression from infix to postfix notation using
	 * Shunting-yard algorithm.
	 * 
	 * @param regex infix notation
	 * @return postfix notation
	 */
	public String infixToPostfix(String regex) {
		String postfix = new String();

		Stack<Character> stack = new Stack<Character>();

		String formattedRegEx = formatRegEx(regex);

		for (Character c : formattedRegEx.toCharArray()) {
			switch (c) {
				case '(':
					stack.push(c);
					break;

				case ')':
					while (!stack.peek().equals('(')) {
						postfix += stack.pop();
					}
					stack.pop();
					break;

				default:
					while (stack.size() > 0) {
						Character peekedChar = stack.peek();

						Integer peekedCharPrecedence = getPrecedence(peekedChar);
						Integer currentCharPrecedence = getPrecedence(c);

						if (peekedCharPrecedence >= currentCharPrecedence) {
							postfix += stack.pop();
						} else {
							break;
						}
					}
					stack.push(c);
					break;
			}

		}

		while (stack.size() > 0)
			postfix += stack.pop();

		return postfix;
	}
        
    public String convertirCerraduraPositiva(String regex) {

        for (int i = 0; i < regex.length(); i++) {
            Character c_actual = regex.charAt(i);
            
            if (c_actual == '+') {
                Character c_anterior = regex.charAt(i - 1);
                
                if (c_anterior != ')') {
                    regex = insertCharAt(regex, i, c_anterior + "*");
                }
                
                if (c_anterior == ')') {
                    int index = i - 1;
                    int contadorParentecisApertura = 0;
                    int contadorParentecisCierre = 0;
                    
                    while (index != -1) {
                        if (regex.charAt(index) == ')') {
                            contadorParentecisCierre++;
                        }
                        if (regex.charAt(index) == '(') {
                            contadorParentecisApertura++;
                        }
                        
                        if (contadorParentecisCierre == contadorParentecisApertura) {
                            break;
                        }
                        index--;
                    }
                    regex = insertCharAt(regex, i, regex.substring(index, i) + "*");
                }
            }

        }
        return regex;
    }
    
    public String convertirSignoInterrogacion(String regex) {

        for (int i = 0; i < regex.length(); i++) {
            Character c_actual = regex.charAt(i);
            
            if (c_actual == '?') {
                Character c_anterior = regex.charAt(i - 1);
                
                if (c_anterior != ')') {
                    regex = insertCharAt(regex, i, "|" + "€");
                }
                
                if (c_anterior == ')') {
                    int index = i - 1;
                    int contadorParentecisApertura = 0;
                    int contadorParentecisCierre = 0;
                    
                    while (index != -1) {
                        if (regex.charAt(index) == ')') {
                            contadorParentecisCierre++;
                        }
                        if (regex.charAt(index) == '(') {
                            contadorParentecisApertura++;
                        }
                        
                        if (contadorParentecisCierre == contadorParentecisApertura) {
                            break;
                        }
                        index--;
                    }
                    regex = insertCharAt(regex, i, "|" + "€");
                }
            }

        }
        return regex;
    }
    
    public String insertCharAt(String cadena, int posicion, Object caracterIngreso) {
        return cadena.substring(0, posicion) + caracterIngreso + cadena.substring(posicion + 1);
    }
}