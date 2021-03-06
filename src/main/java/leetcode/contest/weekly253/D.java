package leetcode.contest.weekly253;

import java.util.Arrays;

public class D {
    public int[] longestObstacleCourseAtEachPosition(int[] a) {
        final int n = a.length;
        final int[] result = new int[n];
        final int[] dp = new int[n + 2];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[1] = 0;
        dp[0] = 0;
        result[0] = 1;

        for (int i = 1; i < n; i++) {
            // DP[i]=j is the index in the array A, such that
            // there is a LIS of length i ending in DP[i]=j and A[j] is min.

            // Let's find a max element X in the array DP such that a[X]<=a[i]
            int x = find(a, dp, a[i], i + 1);

            // We can increase LIS ending in index X
            // Or if X is -1, then we can't increase anything
            if (x == -1) {
                if (a[i] < a[dp[1]]) {
                    dp[1] = i;
                }
                result[i] = 1;
            } else {
                if (dp[x+1] == Integer.MAX_VALUE || a[i] < a[dp[x + 1]]) {
                    dp[x + 1] = i;
                }
                result[i] = x + 1;
            }
        }

        return result;
    }

    // Array a[DP] is sorted
    // Max index J in DP[0 to rightIndex) such that
    // A[J] <= value. Or -1 if a[0] > value
    private int find(int[] a, int[] dp, int value, int rightIndex) {
        if (dp[1] != Integer.MAX_VALUE && a[dp[1]] > value) {
            return -1;
        }
        if (dp[rightIndex - 1] != Integer.MAX_VALUE && a[dp[rightIndex - 1]] <= value) {
            return rightIndex - 1;
        }

        int l = 0, r = rightIndex;
        while (l + 1 < r) {
            int w = (l + r) / 2;
            if (dp[w] != Integer.MAX_VALUE && a[dp[w]] <= value) {
                l = w;
            } else {
                r = w;
            }
        }
        return l;
    }

}
