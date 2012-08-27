import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DequeTests {
    
    private Deque<String> d = null;

    @Before
    public void setUp() {
        resetD();
    }

    private void resetD() {
        d = new Deque<String>();
    }

    @Test
    public void testDeque() {
        Assert.assertEquals(0, d.size());
        Assert.assertTrue(d.isEmpty());
    }

    @Test
    public void testIsEmptyTrue() {
        resetD();
        Assert.assertTrue(d.isEmpty());
        d.addFirst("Moo");
        Assert.assertEquals(1, d.size());
        d.removeFirst();
        Assert.assertTrue(d.isEmpty());
        Assert.assertEquals(0, d.size());
        d.addLast("Last");
        Assert.assertEquals(1, d.size());
        d.removeLast();
        Assert.assertTrue(d.isEmpty());
        Assert.assertEquals(0, d.size());
    }

    @Test
    public void testIsEmptyFalse() {
        resetD();
        Assert.assertTrue(d.isEmpty()); // forces this to fail from the outset
        d.addFirst("First");
        Assert.assertFalse(d.isEmpty());
        d.removeFirst();
        Assert.assertTrue(d.size() == 0);
        d.addLast("Last");
        Assert.assertFalse(d.isEmpty());
    }

    @Test
    public void testSize() {
        resetD();
        Assert.assertEquals(0, d.size());
        for (int n = 1; n <= 1000; n++) {
            d.addFirst(String.format("Str %s", n));
            Assert.assertEquals(String.format(
                    "Size @ iteration %s should be %s but was %s", 
                    n, n, d.size()),
                    n,
                    d.size());
        }
    }

    @Test
    public void testAddFirst() {
        d.addFirst("First");
        Assert.assertEquals("First", d.iterator().next());
        Assert.assertEquals(1, d.size());
        
        d.addFirst("Second");
        Assert.assertEquals(2, d.size());
        final Iterator<String> it = d.iterator();
        Assert.assertEquals("Second", it.next());
        Assert.assertEquals("First", it.next());
    }

    @Test(expected = NullPointerException.class)
    public void testAddFirstThrowsNPEOnNullInput() {
        new Deque<String>().addFirst(null);
    }
    
    @Test
    public void testAddLast() {
        resetD();
        d.addLast("Last");
        Assert.assertEquals(1, d.size());
        Assert.assertEquals("Last", d.iterator().next());
        d.addLast("Next last");
        Assert.assertEquals(2, d.size());
        final Iterator<String> iterator = d.iterator();
        iterator.next(); // skip "Last"
        Assert.assertEquals("Next last", iterator.next());
    }
    
    @Test(expected = NullPointerException.class)
    public void testAddLastThrowsNPEOnNullInput() {
        new Deque<String>().addLast(null);
    }

    @Test
    public void testRemoveFirst() {
        d.addLast("Last");
        d.addFirst("First");
        Assert.assertEquals("First", d.iterator().next());
        Assert.assertEquals("First", d.removeFirst());
        Assert.assertEquals(1, d.size());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstThrowsNoSuchElementWhenEmpty() {
        resetD();
        d.removeFirst();
    }

    @Test
    public void testRemoveLast() {
        d.addLast("Last");
        d.addFirst("Middle");
        d.addFirst("First");
        Assert.assertEquals(3, d.size());
        Assert.assertEquals("Last", d.removeLast());
        Assert.assertEquals(2, d.size());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastThrowsNoSuchElementWhenEmpty() {
        resetD();
        d.removeLast();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastThrowsNoSuchElementWhenExhausted() {
        d.addFirst("First");
        final Iterator<String> iterator = d.iterator();
        iterator.next();
        iterator.next();
    }

    @Test
    public void testIterator() {
        final Deque<Integer> de = new Deque<Integer>();
        de.addLast(0);
        final int N = 1000;
        for (int i = 1; i < N; i++) {
            de.addLast(i);
        }
        
        final Iterator<Integer> iterator = de.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(N, de.size());
        
        for (int i = 0; i < N; i++) {
            final int next = iterator.next();
            Assert.assertEquals(String.format(
                    "Item @ iteration %s was %s not %s",
                    i, next, i),
                    i, next);
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveThrowsUnsupportedOpException() {
        d.iterator().remove();
    }
    
    @Test
    public void testRandomCalls() {
        final float p1 = 0.9f;
        final float p2 = 0.1f;
        for (int i = 1; i <= 50; i++) {
            for (int j = 0; j < p1 * 10; j++) {
                d.addFirst("" + j);
            }
            for (int k = 0; k < p2 * 10; k++) {
                d.removeLast();
            }
            Assert.assertEquals(8 * i, d.size());
        }
    }
    
    @Test
    public void testAddTwoFrontRemoveOneBack() {
        d.addFirst("First");
        d.addFirst("Second");
        Assert.assertEquals("First", d.removeLast());
    }
    
    @Test
    public void testAddTwoFrontRemoveTwoBack() {
        d.addFirst("First");
        d.addFirst("Second");
        Assert.assertEquals("First", d.removeLast());
        Assert.assertEquals("Second", d.removeLast());
    }
    
    @Test
    public void testAddFrontAddBackIterator() {
        d.addFirst("First");
        d.addLast("Last");
        final Iterator<String> it = d.iterator();
        Assert.assertEquals("First", it.next());
        Assert.assertEquals("Last", it.next());
    }
    
    @Test
    public void testAddTwoBackRemoveOneFront() {
        d.addLast("First");
        d.addLast("Second");
        Assert.assertEquals("First", d.removeFirst());
    }
    
    @Test
    public void testRandomFillAndEmptyPrefillAndRunLow() {
        Deque<String> dq = new Deque<String>();
        String qMirror = "";
        int size = 0;

        // fill up first so we can drain the queue
        for (int i = 0; i < 200; i++) {             
                size++;
                String add = "" + (char) (int) (65 + Math.random() * 26);
                // add item
                if (Math.random() > .5) {
                    dq.addFirst(add);
                    qMirror = add + qMirror;
                } else {
                    dq.addLast(add);
                    qMirror = qMirror + add;
                }
        }
        
        Assert.assertFalse(dq.isEmpty());
        Assert.assertEquals(200, dq.size());

        for (int i = 0; i < 1000; i++) {             
            // favour emptying the queue
            if (Math.random() > .3 && !dq.isEmpty()) {
                // empty in 20%
                if (Math.random() > .5) {
                    String rVal = dq.removeFirst();
                    String rMir = qMirror.substring(0, 1);
                    Assert.assertEquals("Should match F", rMir, rVal);
                    qMirror = qMirror.substring(1);
                    size--;
                } else {
                    String rVal = dq.removeLast();
                    String rMir = qMirror.substring(qMirror.length() - 1);
                    Assert.assertEquals("Should match L", rMir, rVal);
                    qMirror = qMirror.substring(0, qMirror.length() - 1);
                    size--;
                }
            }  else {
                size++;
                String add = "" + (char) (int) (65 + Math.random() * 26);
                // add item
                if (Math.random() > .5) {
                    dq.addFirst(add);
                    qMirror = add + qMirror;
                } else {
                    dq.addLast(add);
                    qMirror = qMirror + add;
                }
            }
        }

        String qLeft = "";
        for (String s : dq) 
            qLeft += s;
        Assert.assertEquals("Queue should report size", size , dq.size());
        Assert.assertEquals("random test left over", qLeft, qMirror);
    }
    
    @Test
    public void testNonEmptyToEmptyToNonEmpty() {
        d.addFirst("First");
        Assert.assertEquals(1, d.size());
        d.removeLast();
        Assert.assertEquals(0, d.size());
        d.addLast("Second");
        Assert.assertEquals(1, d.size());
        d.removeFirst();
        Assert.assertEquals(0, d.size());
    }
    
    @Test
    public void testConstantTimeOps() {
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 16384; i++) {
            d.addLast(String.valueOf(i));
        }
        
        long end = start-System.currentTimeMillis();
        Assert.assertTrue(end < 10);
        
        start = System.currentTimeMillis();
        for (int i = 1; i <= 16384; i++) {
            d.removeLast();
        }
        end = start-System.currentTimeMillis();
        Assert.assertTrue(end < 10);
    }
    
    @Test
    public void testAddFirstRemoveLastRandom() {
        final float p1 = 0.9f;
        final float p2 = 0.0f;
        final float p3 = 0.0f;
        final float p4 = 0.1f;
        
        final Deque<String> deque = new Deque<String>();
        final int expectedSize = runProbabilityTest(deque, 5, p1, p2, p3, p4);
        Assert.assertEquals(expectedSize, deque.size());
    }
    
    @Test
    public void test50AddFirstRemoveLastRandom() {
        final double p1 = 0.9d;
        final double p2 = 0.0d;
        final double p3 = 0.0d;
        final double p4 = 0.1d;
        
        final Deque<String> deque = new Deque<String>();
        final int expectedSize = runProbabilityTest(deque, 50, p1, p2, p3, p4);
        Assert.assertEquals(expectedSize, deque.size());
    }
    
    @Test
    public void test500AddFirstRemoveLastRandom() {
        final double p1 = 0.9d;
        final double p2 = 0.0d;
        final double p3 = 0.0d;
        final double p4 = 0.1d;
        
        final Deque<String> deque = new Deque<String>();
        final int expectedSize = runProbabilityTest(deque, 500, p1, p2, p3, p4);
        Assert.assertEquals(expectedSize, deque.size());
    }
    
    @Test 
    public void test5AddFirstAddLastRemoveFirstRemoveLastProbability() {
        // p1 = 0.4, p2 = 0.4, p3 = 0.1, p4 = 0.1        
        
        final double p1 = 0.4d;
        final double p2 = 0.4d;
        final double p3 = 0.1d;
        final double p4 = 0.1d;
        
        final Deque<String> deque = new Deque<String>();
        final int N = 5;
        final int expectedSize = runProbabilityTest(deque, N, p1, p2, p3, p4);
        Assert.assertEquals(expectedSize, deque.size());
    }
    
    @Test 
    public void test50AddFirstAddLastRemoveFirstRemoveLastProbability() {
        // p1 = 0.4, p2 = 0.4, p3 = 0.1, p4 = 0.1        
        
        final double p1 = 0.4d;
        final double p2 = 0.4d;
        final double p3 = 0.1d;
        final double p4 = 0.1d;
        
        final Deque<String> deque = new Deque<String>();
        final int N = 50;
        final int expectedSize = runProbabilityTest(deque, N, p1, p2, p3, p4);
        Assert.assertEquals(expectedSize, deque.size());
    }
    
    @Test 
    public void test500AddFirstAddLastRemoveFirstRemoveLastProbability() {
        // p1 = 0.4, p2 = 0.4, p3 = 0.1, p4 = 0.1        
        
        final double p1 = 0.4d;
        final double p2 = 0.4d;
        final double p3 = 0.1d;
        final double p4 = 0.1d;
        
        final Deque<String> deque = new Deque<String>();
        final int N = 500;
        final int expectedSize = runProbabilityTest(deque, N, p1, p2, p3, p4);
        Assert.assertEquals(expectedSize, deque.size());
    }

    private int runProbabilityTest(
            final Deque<String> dq, final int N,
            final double p1, final double p2,
            final double p3, final double p4) {
        int p1Calls = 0; // total addFirst
        int p2Calls = 0; // total removeFirst
        int p3Calls = 0; // total addLast
        int p4Calls = 0; // total removeLast
        
        for (int i = 0; i < N; i++) {
            final double random = StdRandom.uniform();
            if (p1 > random) {
                p1Calls++;
                dq.addFirst(String.valueOf(i));
                continue;
            } 
            
            if (p3 > random) {
                p3Calls++;
                dq.addLast(String.valueOf(i));
                continue;
            }
        }
        
        final int totalAdded = p1Calls + p3Calls;
        
        for (int i = 0; i < totalAdded; i++) {
            final double random = StdRandom.uniform();
            if (p2 > random) {
                p2Calls++;
                dq.removeFirst();
                continue;
            }
            
            if (p4 > random) {
                p4Calls++;
                dq.removeLast();
                continue;
            }
        }
        
        final int totalRemoved = p2Calls + p4Calls;
        return totalAdded - totalRemoved;
    }
}
