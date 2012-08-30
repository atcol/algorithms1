import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private static final String UNCHECKED = "unchecked";

    private static final class NodeIterator<Item> implements Iterator<Item> {
        private final RandomizedQueue<Item> q;

        public NodeIterator(final RandomizedQueue<Item> queue) {
            q = new RandomizedQueue<Item>();
            while (q.size() != queue.size()) {
                q.enqueue(queue.sample());
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

    private Item[] items;
    
    private int size = 0;

    public RandomizedQueue() {
        items = newItemsArray(16);
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
        if (size == items.length) {
            // resize array
            final int newSize = items.length * 2;
            final Item[] newItems = newItemsArray(newSize);
            int c = 0;
            for (Item i : items) {
                newItems[c] = i;
                c++;
            }
            items = newItems;
        } 
        items[size] = item;
        size++;
    }


    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        Item randomItem = dequeRandomItem();
        size--;
        
        final int quarterOfLength = items.length / 4;
        if (size ==  quarterOfLength) {
            shrinkItems(items.length / 2);
        }
        return randomItem;
    }

    private void shrinkItems(final int newSize) {
        // shrink items to half its length 
        final Item[] newItems = newItemsArray(newSize);
        int i = 0, j = 0;
        while (i < items.length) {
            final Item item = items[i];
            if (item != null) {
                newItems[j] = item;
                j++;
            }
            i++;
        }
//        int c = 0;
//        while (c < newSize) {
//            final Item item = items[c];
//            if (item != null) {
//                newItems[c] = item;
//            }
//                c++;
//        }
        items = newItems;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return randomItem();
    }

    public Iterator<Item> iterator() {
        return new NodeIterator<Item>(this);
    }
    
    private Item randomItem() {
        final int randomItemIdx = StdRandom.uniform(size);
        return items[randomItemIdx];
    }
    
    @SuppressWarnings(UNCHECKED) private Item[] newItemsArray(final int newSize) {
        return (Item[]) new Object[newSize];
    }
    
    private Item dequeRandomItem() {
        int randomItemIdx = StdRandom.uniform(items.length);
        Item randomItem = null;
        while ((randomItem = items[randomItemIdx]) == null) {
            randomItemIdx = StdRandom.uniform(items.length);
        }
        items[randomItemIdx] = null;
        return randomItem;
    }

}