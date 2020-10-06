import java.util.ArrayList;
import java.util.Arrays;

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

    public void solve() {
        for (int i=0; i < this.equation.length(); i++) {
            
        }
    }

    public double simpleSolve(String equ) {

        double num = 0;
        double currentNum = 0;
        int currentOperator;

        // Splits the string into an array by numbesr and operators
        for (char operator : this.operators) {
            equ = equ.replace(""+operator, "#"+operator+"#");
        }
        ArrayList<String> splitEqu = new ArrayList<>(Arrays.asList(equ.split("#")));

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