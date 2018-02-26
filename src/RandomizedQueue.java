import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] q;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        q = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (n == q.length) resize(2 * q.length);
        q[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int rndIdx = StdRandom.uniform(n);
        Item item = q[rndIdx];

        q[rndIdx] = q[n - 1];
        q[n - 1] = null;
        n--;

        if (n > 0 && n == q.length / 4) resize(q.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int rndIdx = StdRandom.uniform(n);
        return q[rndIdx];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indexes;
        private int current;

        public RandomizedQueueIterator() {
            indexes = StdRandom.permutation(n);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return q[indexes[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");

        System.out.println(queue.sample());


        queue.dequeue();
        queue.dequeue();

        for (String e : queue) {
            System.out.println(e);
        }

        for (String e : queue) {
            System.out.println(e);
        }

        System.out.println(queue.size());
    }
}