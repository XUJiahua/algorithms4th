import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
Performance requirements.
Your deque implementation must support each deque operation (including construction) in constant worst-case time.
A deque containing n items must use at most 48n + 192 bytes of memory and use space proportional to the number of items currently in the deque.
Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.
*/
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldFirst = first;

        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;

        n++;
        if (n == 1) {
            last = first;
        } else {
            oldFirst.previous = first;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldLast = last;

        last = new Node<Item>();
        last.item = item;
        last.previous = oldLast;

        n++;
        if (n == 1) {
            first = last;
        } else {
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;

        first = first.next;

        n--;
        if (n == 0) {
            last = first;
        } else {
            first.previous = null;
        }

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;

        last = last.previous;

        n--;
        if (n == 0) {
            first = last;
        } else {
            last.next = null;
        }

        return item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addLast("A");
        deque.addLast("C");
//        deque.removeFirst();
//        deque.removeLast();
//        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());

//        deque.addFirst("B");
//
//        deque.addLast("D");
//        deque.removeFirst();
//        deque.removeFirst();

        for (String e : deque) {
            System.out.println(e);
        }

        System.out.println(deque.size());

    }
}
