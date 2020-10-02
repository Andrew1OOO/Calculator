public class Equation {
    
    private String equation;

    public Equation(String equ) {
        this.equation = equ;
    }

    public String clean(String s) {
        s = s.replaceAll(" ", "");
        if (s.charAt(0) == '(' && s.charAt(s.length()-1) == ')');
            s = s.substring(1, s.length());
        
        return s;
    }

    public void solve() {
        System.out.println(this.equation);
    }
}
