import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int subset = Integer.parseInt(args[0]);

        String[] sArray = StdIn.readAllStrings();
        for (int i = 0; i < sArray.length; i++) rq.enqueue(sArray[i]);

        int j = 0;
        for (String s : rq) {
            if (j >= subset) break;
            StdOut.println(s);
            j++;
        }
    }
}
