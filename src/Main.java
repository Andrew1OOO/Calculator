public class Main{
    private static Equation e;
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        e = new Equation("(-1+3)");
        System.out.println(e.solve());
    }
}
