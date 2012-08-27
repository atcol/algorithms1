import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RandomizedQueueTest {

    private RandomizedQueue<Integer> q;
    
    @Before
    public void setUp() {
        resetQ();
    }

    private void resetQ() {
        q = new RandomizedQueue<Integer>();
    }

    @Test
    public void testRandomizedQueue() {
        resetQ();
        Assert.assertEquals(0, q.size());
        Assert.assertTrue(q.isEmpty());
        Assert.assertFalse(q.iterator().hasNext());
    }

    @Test
    public void testIsEmptyTrue() {
        resetQ();
        Assert.assertTrue(q.isEmpty());
        q.enqueue(0);
        Assert.assertFalse(q.isEmpty());
        q.dequeue();
        Assert.assertTrue(q.isEmpty());
    }
    
    @Test
    public void testIsEmptyFalse() {
        q.enqueue(0);
        Assert.assertFalse(q.isEmpty());
        q.dequeue();
        Assert.assertTrue(q.isEmpty());
        q.enqueue(0);
        q.enqueue(1);
        Assert.assertFalse(q.isEmpty());
    }

    @Test
    public void testSize() {
        resetQ();
        Assert.assertEquals(0, q.size());
        for (int n = 1; n <= 1000; n++) {
            q.enqueue(n);
            Assert.assertEquals(
                String.format(
                    "Size @ iteration %s should be %s but was %s", 
                    n, n, q.size()),
                n, q.size());
        }
    }

    @Test
    public void testEnqueue() {
        q.enqueue(0);
        Assert.assertEquals(0, (int) q.dequeue());
        for (int n = 1; n <= 1000; n++) {
            q.enqueue(n);
            Assert.assertEquals(
                String.format(
                    "Size @ iteration %s should be %s but was %s", 
                    n, n, q.size()),
                n, q.size());
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void testEnqueueThrowsOnNullInput() {
        q.enqueue(null);
    }

    @Test
    public void testDequeue() {
        q.enqueue(0);
        Assert.assertEquals(0, (int) q.dequeue());
        Assert.assertEquals(0, (int) q.size());
        Assert.assertTrue(q.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueThrowsWhenEmpty() {
        q.dequeue();
    }

    @Test
    public void testSample() {
        q.enqueue(0);
        Assert.assertEquals(0, (int) q.sample());

        resetQ();
        final String testNumbersStr = "0 1 2 3 4";
        q.enqueue(0);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        Assert.assertTrue(testNumbersStr.contains(q.sample().toString()));
        Assert.assertTrue(testNumbersStr.contains(q.sample().toString()));
        Assert.assertTrue(testNumbersStr.contains(q.sample().toString()));
        Assert.assertTrue(testNumbersStr.contains(q.sample().toString()));
        Assert.assertTrue(testNumbersStr.contains(q.sample().toString()));
        Assert.assertTrue(testNumbersStr.contains(q.sample().toString()));
    }

    @Test(expected = NoSuchElementException.class)
    public void testSampleThrowsWhenEmpty() {
        q.sample();
    }

    @Test
    public void testIterator() {
        for (int i = 1; i < 1000; i++) {
            q.enqueue(i);
        }
        
        Assert.assertEquals(999, q.size());
        final Iterator<Integer> it = q.iterator();
        Assert.assertTrue(it.hasNext());
        
        while (it.hasNext()) {
            final int item = it.next();
            Assert.assertTrue(String.format(
                    "%s item should be > 0", item),
                    0 < item);
            Assert.assertTrue(String.format(
                    "%s item should be < 1000", item),
                    item < 1000);
        }
        
        Assert.assertEquals("Iterator should be independent of queue",
                999, q.size());
    }
    
    @Test
    public void testIteratorHasNext() {
        final Iterator<Integer> it = q.iterator();
        Assert.assertFalse(it.hasNext());

        resetQ();
        q.enqueue(0);
        final Iterator<Integer> it2 = q.iterator();
        Assert.assertTrue(it2.hasNext());
        it2.next();
        Assert.assertFalse(it2.hasNext());
    }
    
    @Test
    public void testIteratorsIndependent() {
        final Iterator<Integer> it1 = q.iterator();
        final Iterator<Integer> it2 = q.iterator();
        Assert.assertFalse(it1.equals(it2));
        
        final int numOfTotalIterations = q.size() * q.size();
        int count = 0;
        for (int item : q) {
            for (int item2 : q) {
                Assert.assertNotSame(
                    String.format("%s and %s should NOT be same",
                    item, item2),
                    item, item2);
                count++;
            }
            count++;
        }
        Assert.assertEquals("Total iterations should be size^2", 
                numOfTotalIterations, count);
    }
    
    @Test
    public void testIteratorNextGivesRandomItem() {
        for (int i = 0; i < 1000; i++) {
            q.enqueue(i);
        }
        
        final Iterator<Integer> it1 = q.iterator();
        final Iterator<Integer> it2 = q.iterator();
        final int it1Item = it1.next();
        final int it2Item = it2.next();
        Assert.assertNotSame(String.format("%s and %s unlikely to be the same",
                it1Item, it2Item),
                it1Item, it2Item);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextThrowsOnEmpty() {
        new RandomizedQueue<String>().iterator().next();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextThrowsWhenEmpty() {
        q.enqueue(0);
        final Iterator<Integer> it = q.iterator();
        it.next();
        it.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorThrowsOnRemove() {
        new RandomizedQueue<String>().iterator().remove();
    }
    
    @Test
    public void testFill() {
//        1024
//        4096
//        16384
//        128000
//        256000
//        1024000
        long timeInMs = fill(q, 1024);
        Assert.assertTrue(timeInMs < 1000);
        resetQ();
        timeInMs = fill(q, 4096);
        Assert.assertTrue(timeInMs < 1000);
        
        resetQ();
        timeInMs = fill(q, 16384);
        Assert.assertTrue(timeInMs < 1000);
        
        resetQ();
        timeInMs = fill(q, 128000);
        Assert.assertTrue(timeInMs < 1000);
        
        resetQ();
        timeInMs = fill(q, 256000);
        Assert.assertTrue(timeInMs < 1000);
        
        resetQ();
        timeInMs = fill(q, 1024000);
        Assert.assertTrue(timeInMs < 1000);
    }
    
    @Test
    public void testFillNoReset() {
//        1024
//        4096
//        16384
//        128000
//        256000
//        1024000
        long timeInMs = fill(q, 1024);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 4096);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 16384);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 128000);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 256000);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 1024000);
        Assert.assertTrue(timeInMs < 1000);
    }
    
    @Test
    public void testFillClearNoReset() {
//        1024
//        4096
//        16384
//        128000
//        256000
//        1024000
        long timeInMs = fill(q, 1024);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 4096);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 16384);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 128000);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 256000);
        Assert.assertTrue(timeInMs < 1000);
        
        timeInMs = fill(q, 1024000);
        Assert.assertTrue(timeInMs < 1000);
        
        // now clear accordingly
        timeInMs = clear(q, 1024);
        Assert.assertTrue(String.format("%s should be < 1000", timeInMs),
                timeInMs < 1000);
        
        timeInMs = clear(q, 4096);
        Assert.assertTrue(String.format("%s should be < 1000", timeInMs),
                timeInMs < 1000);
        
        timeInMs = clear(q, 16384);
        Assert.assertTrue(String.format("%s should be < 1000", timeInMs),
                timeInMs < 1000);
        
        timeInMs = clear(q, 128000);
        Assert.assertTrue(String.format("%s should be < 1000", timeInMs),
                timeInMs < 1000);
        
        timeInMs = clear(q, 256000);
        Assert.assertTrue(String.format("%s should be < 1000", timeInMs),
                timeInMs < 1000);
        
        timeInMs = clear(q, 1024000);
        Assert.assertTrue(String.format("%s should be < 1000", timeInMs),
                timeInMs < 1000);
    }
    
    private long clear(final RandomizedQueue<Integer> queue, final int times) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            queue.dequeue();
        }
        return System.currentTimeMillis() - start;
    }
    
    private long fill(final RandomizedQueue<Integer> queue, final int times) {
        final long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            queue.enqueue(i);
        }
        return System.currentTimeMillis() - start;
    }
    
    @Test
    public void testResizing() {
        // 1 1 2 2 3 3 4 4
        int N = 5;
        for (int i = 0; i < N; i++) {
            q.enqueue(i);
            q.dequeue();
        }
//        final float p1 = 0.9f;
//        final float p2 = 0.1f;
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < p1 * 10; j++) {
//                q.enqueue(i);
//            }
//            for (int j = 0; j < p2 * 10; j++) {
//                q.dequeue();
//            }
//        }
//        Assert.assertTrue(condition);
    }
    
//    @Test
//    public void testZeroFind() {
//        for (int i = 0; i < 10; i++) {
//            q.enqueue(i);
//        }
//        System.out.println("Queue is " + q.size());
//        int[] triesArray = new int[100000];
//        for (int j = 0; j < 100000; j++) {
//            int tries = 0;
//            int i = -1;
//            while (i != 0) {
//                tries++;
//                try {
//                    i = q.dequeue();
//                } catch (final NoSuchElementException e) {
//                    Assert.fail(String.format(
//                        "Test failed with %s @ iteration %s for value %s"
//                            , e, j, i));
//                }
//            }
//            triesArray[j] = tries;
//        }
//        final double meanTries = StdStats.mean(triesArray);
//        Assert.assertTrue(String.format("Mean tries %s must be > 0", meanTries),
//                meanTries > 0);
//    }
//    private int runProbabilityTest(
//            final Deque<String> dq, final int N,
//            final double p1, final double p2,
//            final double p3, final double p4) {
//        int p1Calls = 0; // total addFirst
//        int p2Calls = 0; // total removeFirst
//        int p3Calls = 0; // total addLast
//        int p4Calls = 0; // total removeLast
//        
//        for (int i = 0; i < N; i++) {
//            final double random = StdRandom.uniform();
//            if (p1 > random) {
//                p1Calls++;
//                dq.addFirst(String.valueOf(i));
//                continue;
//            } 
//            
//            if (p3 > random) {
//                p3Calls++;
//                dq.addLast(String.valueOf(i));
//                continue;
//            }
//        }
//        
//        final int totalAdded = p1Calls + p3Calls;
//        
//        for (int i = 0; i < totalAdded; i++) {
//            final double random = StdRandom.uniform();
//            if (p2 > random) {
//                p2Calls++;
//                dq.removeFirst();
//                continue;
//            }
//            
//            if (p4 > random) {
//                p4Calls++;
//                dq.removeLast();
//                continue;
//            }
//        }
//        
//        final int totalRemoved = p2Calls + p4Calls;
//        return totalAdded - totalRemoved;
//    }
}
