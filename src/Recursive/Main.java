import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String exit = "n";
        String input;
        Equation e;

        while (exit.toLowerCase().equals("n") || exit.toLowerCase().equals("no")) {
            System.out.println("Your expression: ");
            input = in.nextLine();

            /* The boolean parameter indicates whether or not to print
            the individual mathematical operations that occur
            when solving. By default, it is true. This can be
            disabled by changing the instantiation call line to:
            e = new Equation("("+input+")", false) */

            try {
                if (!Equation.invalidParentheses("("+input+")")){
                    System.out.println("Evaluating: " + "("+input+") ...");
                    e = new Equation("("+input+")");
                    System.out.println("Output: " + e.solve());
                }
                else {
                    System.out.println("Invalid parentheses order!");
                }
                
            }
            catch (java.lang.IndexOutOfBoundsException exception){
                System.out.println("\nInvalid parentheses order!");
            }
            catch (java.lang.NumberFormatException exception) {
                System.out.println("\nInvalid operator used/operator order!");
            }
            finally {
            }
            

            System.out.println("Would you like to exit? (y/n) : ");
            exit = in.nextLine();
        }
    }
}