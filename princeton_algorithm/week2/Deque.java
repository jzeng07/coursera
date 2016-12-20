import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    
    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (first == null) {
            first = new Node();
            first.item = item;
            last = first;
            size++;
            return;
        }

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        oldfirst.prev = first;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (last == null) {
            last = new Node();
            last.item = item;
            first = last;
            size++;
            return;
        }

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        oldlast.next = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        if (size > 1) {
            first = first.next;
            first.prev = null;
        } else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        if (size > 1) {
            last = last.prev;
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        try { deque.removeFirst(); }
        catch (NoSuchElementException e) {
            StdOut.println("First element is null");
        }
            
        try { deque.removeLast(); }
        catch (NoSuchElementException e) {
            StdOut.println("Last element is null");
        }

        try { deque.addFirst(null); }
        catch (NullPointerException e) {
            StdOut.println("Add null to first");
        }

        try { deque.addLast(null); }
        catch (NullPointerException e) {
            StdOut.println("Add null to last");
        }

        Iterator<Integer> dequeIterator = deque.iterator();
        try { dequeIterator.remove(); }
        catch (UnsupportedOperationException e) {
            StdOut.println("Not support iterator.remove()");
        }

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int i = 0; i < numbers.length; i++) {
            if (i % 2 == 0) {
                deque.addLast(numbers[i]);
            } else {
                deque.addFirst(numbers[i]);
            }
            StdOut.println("current size: " + deque.size);
        }
        for (int k : deque) StdOut.println(k);


        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
            StdOut.println("current size: " + deque.size);
        }
        for (int k : deque) StdOut.println(k);

        for (int i = 0; i < 10; i++) {
            deque.removeLast();
            deque.removeFirst();
        }

    }
}
