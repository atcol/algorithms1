import java.util.Iterator;

public class DequeTestMain {
    public static void main(final String[] args) {
        Deque<String> dek = new Deque<String>();
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";
        boolean testb = false;

        StdOut.println("START TESTS");
        // size at start
        StdOut.println("    size at start");
        if (dek.size() != 0) {
            StdOut.println("FAIL: Size at start == " + dek.size());
        }
        // add remove one front
        StdOut.println("    add remove one front");
        dek.addFirst(test1);
        if (dek.size() != 1) {
            StdOut.println("FAIL: inserting one, front, size == " + dek.size());
        }
        if (dek.removeFirst() != test1) {
            StdOut.println("FAIL: inserting one, front, remove, not correct item");
        }
        if (dek.size() != 0) {
            StdOut.println("FAIL: inserting one, front, remove, size == "
                    + dek.size());
        }
        // add remove one back
        StdOut.println("    add remove one back");
        dek.addLast(test1);
        if (dek.size() != 1) {
            StdOut.println("FAIL: inserting one, back, size == " + dek.size());
        }
        if (dek.removeLast() != test1) {
            StdOut.println("FAIL: inserting one, back, remove, not correct item");
        }
        if (dek.size() != 0) {
            StdOut.println("FAIL: inserting one, back, remove, size == "
                    + dek.size());
        }
        // add two front, remove two back
        StdOut.println("    add two front, remove two back");
        dek.addFirst(test1);
        dek.addFirst(test2);
        if (dek.size() != 2) {
            StdOut.println("FAIL: add two front, size == " + dek.size());
        }
        if (dek.removeLast() != test1) {
            StdOut.println("FAIL: add two front, remove one back, not correct item");
        }
        if (dek.size() != 1) {
            StdOut.println("FAIL: add two front, remove one back, size == "
                    + dek.size());
        }
        if (dek.removeLast() != test2) {
            StdOut.println("FAIL: add two front, remove two back, not correct item");
        }
        if (dek.size() != 0) {
            StdOut.println("FAIL: add two front, remove two back, size == "
                    + dek.size());
        }
        // add two back, remove two front
        StdOut.println("    add two back, remove two front");
        dek.addLast(test1);
        dek.addLast(test2);
        if (dek.size() != 2) {
            StdOut.println("FAIL: add two back, size == " + dek.size());
        }
        if (dek.removeFirst() != test1) {
            StdOut.println("FAIL: add two back, remove one front, not correct item");
        }
        if (dek.size() != 1) {
            StdOut.println("FAIL: add two back, remove one front, size == "
                    + dek.size());
        }
        if (dek.removeFirst() != test2) {
            StdOut.println("FAIL: add two back, remove two front, not correct item");
        }
        if (dek.size() != 0) {
            StdOut.println("FAIL: add two back, remove two front, size == "
                    + dek.size());
        }
        // add front, add back, remove two back
        StdOut.println("    add front, add back, remove two back");
        dek.addFirst(test1);
        dek.addLast(test2);
        if (dek.size() != 2) {
            StdOut.println("FAIL: add front, add back, size == " + dek.size());
        }
        if (dek.removeLast() != test2) {
            StdOut.println("FAIL: add front, add back, remove one back, not correct item");
        }
        if (dek.size() != 1) {
            StdOut.println("FAIL: add front, add back, remove one back, size == "
                    + dek.size());
        }
        if (dek.removeLast() != test1) {
            StdOut.println("FAIL: add front, add back, remove two back, not correct item");
        }
        if (dek.size() != 0) {
            StdOut.println("FAIL: add front, add back, remove two back, size == "
                    + dek.size());
        }
        // add back, add front, remove two front
        StdOut.println("    add back, add front, remove two front");
        dek.addLast(test1);
        dek.addFirst(test2);
        if (dek.size() != 2) {
            StdOut.println("FAIL: add back, add front, size == " + dek.size());
        }
        if (dek.removeFirst() != test2) {
            StdOut.println("FAIL: add back, add front, remove one front, not correct item");
        }
        if (dek.size() != 1) {
            StdOut.println("FAIL: add back, add front, remove one front, size == "
                    + dek.size());
        }
        if (dek.removeFirst() != test1) {
            StdOut.println("FAIL: add back, add front, remove two front, not correct item");
        }
        if (dek.size() != 0) {
            StdOut.println("FAIL: add back, add front, remove two front, size == "
                    + dek.size());
        }
        // remove last, empty deque
        StdOut.println("    remove last, empty deque");
        testb = false;
        try {
            dek.removeLast();
        } catch (java.util.NoSuchElementException e) {
            testb = true;
        }
        if (!testb) {
            StdOut.println("FAIL: empty deque, remove back, exception not thrown");
        }
        // remove first, empty deque
        StdOut.println("    remove first, empty deque");
        testb = false;
        try {
            dek.removeFirst();
        } catch (java.util.NoSuchElementException e) {
            testb = true;
        }
        if (!testb) {
            StdOut.println("FAIL: empty deque, remove front, exception not thrown");
        }
        // add null, front
        StdOut.println("    add null, front");
        testb = false;
        try {
            dek.addFirst(null);
        } catch (java.lang.NullPointerException e) {
            testb = true;
        }
        if (!testb) {
            StdOut.println("FAIL: add null, front, exception not thrown");
        }
        // add null, back
        StdOut.println("    add null, back");
        testb = false;
        try {
            dek.addLast(null);
        } catch (java.lang.NullPointerException e) {
            testb = true;
        }
        if (!testb) {
            StdOut.println("FAIL: add null, back, exception not thrown");
        }
        // iterator test, order
        StdOut.println("    iterator test, order of elements");
        dek.addLast(test2);
        dek.addLast(test3);
        dek.addFirst(test1);
        String temp_iterator = "";
        for (String s : dek) {
            temp_iterator = temp_iterator + s;
        }
        if (!temp_iterator.equals(test1 + test2 + test3)) {
            StdOut.println("FAIL: iterator, not correct");
        }
        dek = new Deque<String>();
        // iterator test, remove
        StdOut.println("    iterator test, remove");
        testb = false;
        try {
            Iterator itr1 = dek.iterator();
            itr1.remove();
        } catch (java.lang.UnsupportedOperationException e) {
            testb = true;
        }
        if (!testb) {
            StdOut.println("FAIL: iterator test, remove, exception not thrown");
        }
        dek = new Deque<String>();
        // iterator test, next, hasNext
        StdOut.println("    iterator test, next, hasNext");
        dek.addFirst(test1);
        dek.addLast(test2);
        Iterator itr2 = dek.iterator();
        if (itr2.hasNext() == false) {
            StdOut.println("FAIL: iterator, two items, hasNext, should have");
        }
        if (itr2.next() != test1) {
            StdOut.println("FAIL: iterator, two items, next, wrong item");
        }
        if (itr2.hasNext() == false) {
            StdOut.println("FAIL: iterator, one item, hasNext, should have");
        }
        if (itr2.next() != test2) {
            StdOut.println("FAIL: iterator, one item, next, wrong item");
        }
        if (itr2.hasNext() == true) {
            StdOut.println("FAIL: iterator, one item, hasNext, shouldn't have");
        }
        dek.removeFirst();
        dek.removeFirst();
        dek = new Deque<String>();
        // iterator test, empty next
        StdOut.println("    iterator test, remove");
        testb = false;
        try {
            Iterator itr3 = dek.iterator();
            itr3.next();
        } catch (java.util.NoSuchElementException e) {
            testb = true;
        }
        if (!testb) {
            StdOut.println("FAIL: iterator test, empty next, exception not thrown");
        }
        dek = new Deque<String>();
        StdOut.println("END TESTS");
    }
}
