package L02DP;

import java.util.Arrays;

public class L003LIS {
    public int lengthOfLIS(int[] arr) {
        int n = arr.length;
        int dp[] = new int[n + 1];
        Arrays.fill(dp, -1);
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, longestIncreasingSubsequence_memo(arr, i, dp));
        }
        return maxLen;
    }

    private int longestIncreasingSubsequence_memo(int[] arr, int idx, int[] dp) {
        if (dp[idx] != -1) {
            return dp[idx];
        }
        int maxLen = 1;
        for (int i = idx - 1; i >= 0; i--) {

            if (arr[i] < arr[idx]) {
                maxLen = Math.max(maxLen, longestIncreasingSubsequence_memo(arr, i, dp) + 1);
            }
        }
        return dp[idx] = maxLen;
    }

    private int longestIncreasingSubsequence_dp(int[] arr) {
        int max = 0, n = arr.length;
        int dp[] = new int[n];
        for (int i = 0; i < n; i++) {
            int maxLen = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] < arr[i]) {
                    maxLen = Math.max(maxLen, dp[j] + 1);
                }
            }
            dp[i] = maxLen;
            max = Math.max(dp[i], max);
        }
        return max;
    }

    public int findNumberOfLIS(int[] arr) {
        int n = arr.length;
        int maxLen = 0, maxCount = 0;
        int dp[] = new int[n];
        int count[] = new int[n];
        for (int i = 0; i < n; i++) {
            int len = 1, csf = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] < arr[i]) {
                    int updatedLen = dp[j] + 1;
                    if (updatedLen > len) {
                        len = updatedLen;
                        csf = count[j];
                    } else if (updatedLen == len) {
                        csf += count[j];
                    }
                }
            }
            dp[i] = len;
            count[i] = csf;
            if (len > maxLen) {
                maxLen = len;
                maxCount = csf;
            } else if (len == maxLen) {
                maxCount += csf;
            }

        }
        return maxCount;
    }
}
