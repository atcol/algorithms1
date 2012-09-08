
public class TestInc {
    public static void main(final String[] args) {
        final int N = 10;
        int[] nums = new int[N];
        int c = 0;
        for (int i = 0; i < N; i++) {
            nums[c++] = i;
        }
        for (final int num : nums) {
            System.out.println(num);
        }
    }
}
