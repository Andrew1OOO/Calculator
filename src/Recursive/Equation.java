import java.util.ArrayList;
import java.util.Arrays;

public class Equation {
    
    private String equation;
    boolean printOperations;
    public static final char[] operators = "^*/+-".toCharArray();

    public Equation(String equ) {
        this.equation = clean(equ);
        this.printOperations = true;
    }
    public Equation(String equ, boolean printOperations) {
        this.equation = clean(equ);
        this.printOperations = printOperations;
    }
    
    public static boolean invalidParentheses(String s) {
        int openCount = 0;
        int closedCount = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                openCount ++;
            }
            else if (c == ')') {
                closedCount ++;
            }
        }

        return !(openCount == closedCount);
    }

    private static String clean(String s) {
        s = insertMultiplication(s);
        s = s.replaceAll(" ", "");
        boolean flag = false;

        /* Removes the first and last parentheses if they make up
        one complete expression */
        if (s.charAt(0) == '(' && s.charAt(s.length()-1) == ')');
            int parenthesisCount = 0;
            for (int i=0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    parenthesisCount++;
                }
                else if (s.charAt(i) == ')') {
                    parenthesisCount--;
                }
                if (parenthesisCount == 0 && i != s.length()-1) {
                    flag = true;
                }
            }
            if (!flag) {
                s = s.substring(1, s.length()-1);
            }
        return s;
    }

    private static String insertMultiplication(String a) {
        /* Checks and accounts for if the equation is trying
        to mulitply operands without the "*" operator.
        "2(9)" gets turned into "2*(9)"*/

        for (int i=1; i<a.length()-1; i++) {
            if (a.charAt(i) == '(') {
             if (Character.isDigit(a.charAt(i-1))) {
                 a = a.substring(0, i) + "*" + a.substring(i);
                 i++;
             }
            }
            else if (a.charAt(i) == ')') {
                if (Character.isDigit(a.charAt(i+1)) || a.charAt(i+1) == '(') {
                    a = a.substring(0, i+1) + "*" + a.substring(i+1);
                    i++;
                }
            }
        }
        return a;
     }

    public double solve() {
        if (this.equation.contains("(")) {
            /* This 'portion' of the method, recursively breaks down parenthetical statements
            into simple expressions sequentially. It then takes the outputs of all the
            parenthetical statements and calculates the rest of the expression using
            simpleSolve() */
            
            int parenthesisCount = 0;
            char[] charEquation = this.equation.toCharArray();
            String finalEquation = "";
            int index1 = -1;
            int index2 = -1;

            for (int i=0; i < charEquation.length; i++) {
                if (charEquation[i] == '(') {
                    if (index1 == -1) {
                        index1 = i;
                    }
                    parenthesisCount ++;
                }
                else if (charEquation[i] == ')') {
                    parenthesisCount --;
                    /* When the parenthesis count is at 0 on a closed parenthesis
                    that means that a parenthetical expression has ended. This is to
                    be parsed, and solved in a new Equation object */
                    if (parenthesisCount == 0) {
                        index2 = i;
                        
                        finalEquation += new Equation(this.equation.substring(index1, index2+1), this.printOperations).solve();  
                        index1 = -1;
                    }
                   
    
                }
                else if (parenthesisCount == 0) {
                    finalEquation += charEquation[i];
                }
            }

            return simpleSolve(finalEquation);
        }
        // If there are no parentheses, "order" does not matter
        else {
            return simpleSolve(this.equation);
        }
    }

    private double simpleSolve(String equ) {
        /* This method solves equations following (P)EMDAS.
        It only works on 'simple' equations that do not have
        parenthesis and a defined order */

        double currentNum = 0;
        int splitArraySize;

        // Splits the string into an arraylist by numbers and operators
        for (char operator : operators) {
            equ = equ.replace(""+operator, "#"+operator+"#");
        }
        ArrayList<String> splitEqu = new ArrayList<>(Arrays.asList(equ.split("#")));

        /* Account for first number being "negative",
        sometimes it adds an empty item */
        if (splitEqu.get(0).equals(""))
        {
            splitEqu.remove(0);
        }

        // Converts "subtracting" numbers into negative numbers
        // {"-", "1"} becomes {"-1"} and {"1","-","2"} becmomes {"1","+","-2"}
        splitArraySize = splitEqu.size();
        for (int i=0; i < splitArraySize; i++) {
            if (splitEqu.get(i).equals("-")) {

                if (i == 0) {
                splitEqu.set(i+1, "-"+splitEqu.get(i+1));
                splitEqu.remove(i);
                splitArraySize--;
                }
                else {
                    splitEqu.set(i+1, "-"+splitEqu.get(i+1));
                    splitEqu.set(i, "+");
                    splitArraySize--;
                }
            }
        }

        // Clean array here
        splitEqu = simpleClean(splitEqu);

        // 1 is defined  as the highest priority, and 3 is lowest
        // starts at 1, ends at 3 (inclusive)
        String item;
        for (int i=1; i<= 3; i++) {
            for (int c=0; c<splitEqu.size(); c++) {
                item = splitEqu.get(c);
                if (isOperator(item)) {
                    if (getOperatorPriority(item) == i) {

                        if (this.printOperations) {
                            System.out.print("Operation: " + splitEqu.get(c-1)+" "+item+" "+splitEqu.get(c+1));
                        }        
                
                        if (item.equals("^")) {
                            currentNum = Math.pow(Double.parseDouble(splitEqu.get(c-1)), Double.parseDouble(splitEqu.get(c+1)));
                        }
                        else if (item.equals("*")) {
                            currentNum = Double.parseDouble(splitEqu.get(c-1)) * Double.parseDouble(splitEqu.get(c+1));
                        }
                        else if (item.equals("/")) {
                            currentNum = Double.parseDouble(splitEqu.get(c-1)) / Double.parseDouble(splitEqu.get(c+1));
                        }
                        else if (item.equals("+")) {
                            currentNum = Double.parseDouble(splitEqu.get(c-1)) + Double.parseDouble(splitEqu.get(c+1));
                        }
                        else if (item.equals("-")) {
                            currentNum = Double.parseDouble(splitEqu.get(c-1)) - Double.parseDouble(splitEqu.get(c+1));
                        }

                        // Removes the used numbers and replaces with the new value
                        splitEqu.remove(c+1);
                        splitEqu.set(c, ""+currentNum);
                        splitEqu.remove(c-1);

                        if (this.printOperations) {
                            System.out.println(" = " + currentNum);
                        }  
                        c = 0;
                    }
                }
            }   
        }

        return Double.parseDouble(splitEqu.get(0));
    }

    private ArrayList<String> simpleClean(ArrayList<String> list) {
        /* Provides some basic cleanup for random empty
        indexes and doubled operators */

        int listLen = list.size();
        for (int i=0; i<listLen; i++) {
            if (list.get(i).equals("") || list.get(i).equals(" ") || list.get(i).equals(null)) {
                list.remove(list.get(i));
                listLen--;
                i--;
            }

            if (i < listLen-1) {
                /* Exception if 2 operators are next to each other. 
                This can only happen with "negative number math" */
                if (isOperator(list.get(i)) && isOperator(list.get(i+1))) {
                    list.remove(i+1);
                    listLen--;
                    i--;
                }
            }
            if (list.get(i).equals("--")) {
                /* Exception if subtracting 2 negative numbers */
                list.set(i, "-");
                list.set(i+1, "-"+list.get(i+1));
                list.remove(i-1);
                i--;
                listLen--;
            }
        }
        return list;
    }

    private boolean isOperator(String a) {
        for (char c : operators) {
            if (a.equals(Character.toString(c))) {
                return true;
            }
        }
        return false;
    }

    private int getOperatorPriority(String a) {
        // Lower value means greater importance
        if (a.equals("^")) {
            return 1;
        }
        else if (a.equals("*") || a.equals("/")) {
            return 2;
        }
        else {
            return 3;
        }
    }

}