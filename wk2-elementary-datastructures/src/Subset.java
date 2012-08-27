
public class Subset {

    public static void main(final String[] args) {
        final RandomizedQueue<String> q = new RandomizedQueue<String>();
        final int k = getK(args);
        final String[] strings = StdIn.readStrings();
        for (final String str : strings) {
            q.enqueue(str);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }

    private static int getK(final String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (final NumberFormatException nfe) {
            throw new IllegalArgumentException(nfe);
        }
    }

}
