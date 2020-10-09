import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Equation {
    
	private String equation;
    private char[] operators;
    private boolean printOperations;

    public Equation(String equ) {
        this.equation = clean(equ);
        this.operators = "^*/+-".toCharArray();
        this.printOperations = true;
    }

    private static String clean(String s) {
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

    public double solve() {
        String equ = this.equation;
        String operators = "^*/+-";
        char operator;
        Integer num1;
        Integer num2;
        ArrayList<Character> equArray = new ArrayList<Character>(10);
        ArrayList<Integer> numArray = new ArrayList<Integer>(10);
        ArrayList<Character> opArray = new ArrayList<Character>(10);
        for (char c : equ.toCharArray()) {
            equArray.add(c);
        }        
        for(int i = 0; i < equArray.size(); i++){

            if(equArray.get(i) > '0' &&  equArray.get(i) <= '9') {

                numArray.add(Character.getNumericValue(equArray.get(i)));
            }
            else if(equArray.get(i) == '('){
                opArray.add(equArray.get(i));
                
            }
            
            else if(equArray.get(i) == ')'){
                while(opArray.get(opArray.size() -1) != '('){
                    if(numArray.size() >= 2){
                        operator = opArray.get(opArray.size() -1);
                        num1 = numArray.get(numArray.size() -1);
                        numArray.remove(numArray.size() -1);
                        num2 = numArray.get(numArray.size() -1);
                        numArray.remove(numArray.size() -1);
                        opArray.remove(opArray.size() -1);
                        System.out.println((int)simpleSolve(Integer.toString(num1) + operator + Integer.toString(num2)));
                        numArray.add((int)simpleSolve(Integer.toString(num1) + operator + Integer.toString(num2)));
                    }
                    else{
                        return numArray.get(0);
                    }
                    
                }
                opArray.remove(opArray.size() -1);
            }
            else if(containsChar(operators, equArray.get(i))){
                while(opArray.size() != 0 && checkPrecedence(opArray.get(opArray.size() -1), equArray.get(i))){
                    operator = opArray.get(opArray.size() -1);
                    num1 = numArray.get(numArray.size() -1);
                    numArray.remove(numArray.size() -1);
                    num2 = numArray.get(numArray.size() -1);
                    numArray.remove(numArray.size() -1);
                    opArray.remove(opArray.size() -1);
                    System.out.println((int)simpleSolve(Integer.toString(num1) + operator + Integer.toString(num2)));
                    numArray.add((int)simpleSolve(Integer.toString(num1) + operator + Integer.toString(num2)));
                }
                opArray.add(equArray.get(i));
            }

        }
        while(opArray.size() != 0){
            operator = opArray.get(opArray.size() -1);
            num1 = numArray.get(numArray.size() -1);
            numArray.remove(numArray.size() -1);
            num2 = numArray.get(numArray.size() -1);
            numArray.remove(numArray.size() -1);
            opArray.remove(opArray.size() -1);
            System.out.println(Integer.toString(num1) + operator + Integer.toString(num2));
            System.out.println(simpleSolve(Integer.toString(num1) + operator + Integer.toString(num2)));
            numArray.add((int)simpleSolve(Integer.toString(num1) + operator + Integer.toString(num2)));
        }
        System.out.println(equ);
        return numArray.get(0);
    }

    private double simpleSolve(String equ) {
        /* This method solves equations following (P)EMDAS.
        It only works on 'simple' equations that do not have
        parenthesis and a defined order */

        double currentNum = 0;
        int splitArraySize;

        // Splits the string into an arraylist by numbers and operators
        for (char operator : this.operators) {
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
    public boolean containsChar(String s, char search) {
        if (s.length() == 0)
            return false;
        else
            return s.charAt(0) == search || containsChar(s.substring(1), search);
    }

    public boolean checkPrecedence(char orginal, char comparer){
        int originalPrecedence = 0;
        int compPrecedence = 0;
        if(orginal == '/' || orginal == '*'){
            originalPrecedence = 4;
        }
        else if(orginal == '-' || orginal == '+'){
            originalPrecedence = 1;
        }
        if(comparer == '/' || comparer == '*'){
            compPrecedence = 4;
        }
        else if(comparer == '-' || comparer == '+'){
            compPrecedence = 1;
        }
        return originalPrecedence >= compPrecedence;
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
        }
        return list;
    }

    private boolean isOperator(String a) {
        for (char c : this.operators) {
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