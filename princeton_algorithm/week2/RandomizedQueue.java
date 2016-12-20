import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(N);
        Item item = s[idx];
        s[idx] = s[--N];
        s[N] = null;
        if (N > 0 && N < s.length/4) resize(s.length/2);
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(N);
        return s[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private int[] random_ids = new int[N];

        public RandomizedQueueIterator() {
            for (int i = 0; i < N; i++) {
                random_ids[i] = i;
            }
            StdRandom.shuffle(random_ids);
        }

        public boolean hasNext() {
            return i < random_ids.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return s[random_ids[i++]];
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        try { rq.enqueue(null); }
        catch (NullPointerException e) {
            StdOut.println("Null item when do rq.enqueue()");
        }

        try { rq.sample(); }
        catch (NoSuchElementException e) {
            StdOut.println("No element when do rq.sample()");
        }

        try { rq.dequeue(); }
        catch (NoSuchElementException e) {
            StdOut.println("No element when do rq.dequeue()");
        }

        int[] numbers = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        for (int i = 0; i < numbers.length; i++) {
            rq.enqueue(numbers[i]);
        }
       
        for (int k : rq) {
            for (int m : rq) {
                StdOut.print(m + " ");
            }
            StdOut.println("\n" + k);
        }

        StdOut.println("");
        StdOut.println("Testing dequeue");
        for (int i = 0; i < 20; i++) {
            StdOut.println(rq.dequeue());
            for (int k : rq) StdOut.print(k + " ");
            StdOut.println("");
        }
    }
}
