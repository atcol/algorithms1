import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private static final class NodeIterator<Item> implements Iterator<Item> {
        private Node<Item> next;

        public NodeIterator(final Deque<Item> deque) {
            if (deque.first != null) {
                this.next = deque.first;
            } else {
                this.next = deque.last;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final Item item = next.item;
            next = next.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;

        public Node(final Item i, final Node<Item> p, final Node<Item> n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Node<Item> first = null;
    private Node<Item> last = null;
    private int size;

    /**
     * Construct an empty deque
     */
    public Deque() {
    }

    /**
     * Is the deque empty?
     * 
     * @return true if there are no first or last items
     */
    public boolean isEmpty() {
        return first == null && last == null;
    }

    /**
     * Return the number of items on the deque
     * 
     * @return an int >= 0
     */
    public int size() {
        return size;
    }

    /**
     * Insert the item at the front
     * 
     * @param item
     */
    public void addFirst(final Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (first == null) {
            first = new Node<Item>(item, null, last);
        } else {
            final Node<Item> oldFirst = first;
            final Node<Item> newFirst = new Node<Item>(item, null, oldFirst);
            first = newFirst;
            oldFirst.prev = first;
            if (last == null) {
                last = first.next;
                last.prev = first;
            }
        }
        if (last == null) {
            last = first;
            last.prev = first;
        }
        
        size++;
    }

    /**
     * Insert the item at the end
     * 
     * @param item
     *            the item to add
     */
    public void addLast(final Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (last == null) {
            last = new Node<Item>(item, first, null);
            if (first != null) {
                first.next = last;
            }
        } else {
            final Node<Item> oldLast = last;
            final Node<Item> newLast = new Node<Item>(item, oldLast, null);
            oldLast.next = newLast;
            last = newLast;
            if (first == null) {
                first = oldLast;
            }
        }
        if (first == null) {
            first = last;
            last.prev = first;
        }
        size++;
    }

    /**
     * Delete and return the item at the front
     * 
     * @return the item removed
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        final Node<Item> oldFirst = first;
        if (oldFirst == last) {
            first = null;
            last = null;
        } else {
            final Node<Item> newFirst = first.next;
            newFirst.prev = null;
            first = newFirst;
        }
        size--;
        return oldFirst.item;
    }

    /**
     * Delete and return the item at the end
     * 
     * @return the item removed
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final Node<Item> oldLast = last;

        if (first == last) {
            // last item left
            first = null;
            last = null;
        } else if (first != null) {
            // find penultimate Node and set as last
            final Node<Item> newLast = last.prev;
            newLast.next = null;
            last = newLast;
        } else {
            last = null;
        }
        size--;

        if (oldLast != null) {
            return oldLast.item;
        } else {
            return first.item;
        }
    }

    /**
     * Return an iterator over items in order from front to end
     * 
     * @return an iterator over the items in this queue
     */
    public Iterator<Item> iterator() {
        return new NodeIterator<Item>(this);
    }
}