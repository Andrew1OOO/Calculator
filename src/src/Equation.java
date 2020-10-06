import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.plaf.synth.SynthOptionPaneUI;
public class Equation {
    
    private String equation;
    private char[] operators;

    public Equation(String equ) {
        this.equation = clean(equ);
        this.operators = "^*/+-".toCharArray();
    }

    public static String clean(String s) {
        s = s.replaceAll(" ", "");
        if (s.charAt(0) == '(' && s.charAt(s.length()-1) == ')');
            s = s.substring(1, s.length()-1);
        
        return s;
    }

    public double solve() {
        return simpleSolve(this.equation);
    }

    public double simpleSolve(String equ) {

        double num = 0;
        double currentNum = 0;
        int currentOperator;
        int splitArraySize;

        // Splits the string into an array by numbesr and operators
        for (char operator : this.operators) {
            equ = equ.replace(""+operator, "#"+operator+"#");
        }
        ArrayList<String> splitEqu = new ArrayList<>(Arrays.asList(equ.split("#")));

        // Account for first number being "negative"

        if (splitEqu.get(0).equals(""))
        {
            splitEqu.remove(0);
        }

        for (String e : splitEqu) {
            System.out.println(e);
        }

        splitArraySize = splitEqu.size();
        for (int i=0; i < splitArraySize; i++) {
            if (splitEqu.get(i).equals("-")) {
                splitEqu.set(i, "+");
                splitEqu.set(i+1, "-"+splitEqu.get(i+1));
            }
        }
        
        for (char operator : this.operators) {
            currentOperator =  splitEqu.indexOf(Character.toString(operator));
            while ( currentOperator > -1) {
                System.out.println("Operating on: " + splitEqu.get(currentOperator-1) + " and " + splitEqu.get(currentOperator+1) + " with " + operator);
                if (operator == '^') {
                    currentNum = Math.pow(Double.parseDouble(splitEqu.get(currentOperator-1)), Double.parseDouble(splitEqu.get(currentOperator+1)));
                }
                else if (operator == '*') {
                    currentNum = Double.parseDouble(splitEqu.get(currentOperator-1)) * Double.parseDouble(splitEqu.get(currentOperator+1));
                }
                else if (operator == '/') {
                    currentNum = Double.parseDouble(splitEqu.get(currentOperator-1)) / Double.parseDouble(splitEqu.get(currentOperator+1));
                }
                else if (operator == '+') {
                    currentNum = Double.parseDouble(splitEqu.get(currentOperator-1)) + Double.parseDouble(splitEqu.get(currentOperator+1));
                }
                else if (operator == '-') {
                    currentNum = Double.parseDouble(splitEqu.get(currentOperator-1)) - Double.parseDouble(splitEqu.get(currentOperator+1));
                }
                
                num += currentNum;

                splitEqu.remove(currentOperator+1);
                splitEqu.set(currentOperator, ""+currentNum);
                splitEqu.remove(currentOperator-1);


                currentNum = 0;
                currentOperator =  splitEqu.indexOf(Character.toString(operator));
            }
        }

        return Double.parseDouble(splitEqu.get(0));
    }
}