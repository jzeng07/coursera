
class HelloWorld {
    private String s = "Life is so good";
    private int[] l = {1, 2, 3}; 
    private int a = 10;
    private int b = 20;

    private void print() {
        s += ", but sometime it is sad";
        System.out.println(s);
        System.out.println(l.length);
    }

    private int add() { return a+b; }

    public static void main(String[] args) {
        HelloWorld hw = new HelloWorld();
        hw.print();
        System.out.println(hw.add());

        System.out.println("Hello, world");
    }
}
