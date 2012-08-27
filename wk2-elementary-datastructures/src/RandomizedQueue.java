import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private static final class NodeIterator<Item> implements Iterator<Item> {
        private final RandomizedQueue<Item> q;

        public NodeIterator(final RandomizedQueue<Item> queue) {
            q = new RandomizedQueue<Item>();
            if (queue != null && queue.first != null) {
                Node<Item> nodeToCopy = queue.first;
                q.enqueue(nodeToCopy.item);
                while (nodeToCopy.next != null) {
                    q.enqueue(nodeToCopy.next.item);
                    nodeToCopy = nodeToCopy.next;
                }
            }
        }
        
        @Override
        public boolean hasNext() {
            return !q.isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return q.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class Node<Item> {
        private Item item;
        private Node<Item> next;
        
        public Node(final Item i, final Node<Item> n) {
            item = i;
            next = n;
        }
    }
    
    private Node<Item> first = null;
    private int size = 0;

    public RandomizedQueue() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(final Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        // add the item
        size++;
        if (first == null) {
            first = new Node<Item>(item, null);
        } else {
            final Node<Item> oldFirst = first;
            final Node<Item> newFirst = new Node<Item>(item, oldFirst);
            first = newFirst;
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final int randomItem = StdRandom.uniform(size);
        Node<Item> node = null;
        
        int i = 0;
        node = first;
        Node<Item> prev = null;
        while (i < randomItem) {
            prev = node;
            node = node.next;
            i++;
        }
        if (prev != null) {
            prev.next = node.next; // "wipe" out the random node
        } else {
            // prev is null, we're at 0
            first = node.next;
        }
        size--;
        return node.item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return randomNode().item;
    }

    public Iterator<Item> iterator() {
        return new NodeIterator<Item>(this);
    }
    
    private Node<Item> randomNode() {
        final int randomItem = StdRandom.uniform(size);
        Node<Item> node = first;
        int i = 0;
        while (i < randomItem) {
            node = node.next;
            i++;
        }
        return node;
    }
}