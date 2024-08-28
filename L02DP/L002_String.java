package L02DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class L002_String extends PrintArr {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int dp[][] = new int[n][n];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return longestPalindromeSubseq_memo(s, 0, n - 1, dp);
    }

    private int longestPalindromeSubseq_memo(String s, int i, int j, int[][] dp) {
        if (i >= j) {
            return dp[i][j] = (i > j) ? 0 : 1;
        }
        if (dp[i][j] != -1)
            return dp[i][j];
        if (s.charAt(i) == s.charAt(j)) {
            return dp[i][j] = longestPalindromeSubseq_memo(s, i + 1, j - 1, dp) + 2;
        } else {
            return dp[i][j] = Math.max(
                    longestPalindromeSubseq_memo(s, i + 1, j, dp),
                    longestPalindromeSubseq_memo(s, i, j - 1, dp));
        }

    }

    private int longestPalindromeSubseqDP(String s, int I, int J) {
        int n = s.length();
        int dp[][] = new int[n][n];
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; j++, i++) {
                if (i >= j) {
                    dp[i][j] = (i > j) ? 0 : 1;
                    continue;
                }

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j],
                            dp[i][j - 1]);
                }
            }
        }
        return dp[I][J];
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int dp[][] = new int[n + 1][m + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return longestCommonSubsequence_memo(text1, text2, n, m, dp);
    }

    private int longestCommonSubsequence_memo(String text1, String text2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            return dp[n][m] = 0;
        }
        if (dp[n][m] != -1)
            return dp[n][m];
        if (text1.charAt(n - 1) == text2.charAt(m - 1)) {
            return dp[n][m] = 1 + longestCommonSubsequence_memo(text1, text2, n - 1, m - 1, dp);
        } else {
            return dp[n][m] = Math.max(longestCommonSubsequence_memo(text1, text2, n, m - 1, dp),
                    longestCommonSubsequence_memo(text1, text2, n - 1, m, dp));
        }
    }

    private int longestCommonSubsequence_DP(String text1, String text2, int N, int M) {
        int[][] dp = new int[N + 1][M + 1];
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                if (text1.charAt(n - 1) == text2.charAt(m - 1)) {
                    dp[n][m] = 1 + dp[n - 1][m - 1];

                } else {
                    dp[n][m] = Math.max(dp[n - 1][m],
                            dp[n][m - 1]);
                }
            }
        }
        return dp[N][M];
    }

    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int dp[][] = new int[n + 1][m + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return numDistinct_memo(s, t, n, m, dp);
    }

    private int numDistinct_memo(String s, String t, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            return dp[n][m] = (m == 0) ? 1 : 0;
        }
        if (dp[n][m] != -1)
            return dp[n][m];
        if (s.charAt(n - 1) == t.charAt(m - 1)) {
            return dp[n][m] = numDistinct_memo(s, t, n - 1, m - 1, dp) + numDistinct_memo(s, t, n - 1, m, dp);
        } else {
            return dp[n][m] = numDistinct_memo(s, t, n - 1, m, dp);
        }
    }

    private int numDistinct_DP(String s, String t, int N, int M) {
        int[][] dp = new int[N + 1][M + 1];
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = (m == 0) ? 1 : 0;
                    continue;
                }

                if (s.charAt(n - 1) == t.charAt(m - 1)) {
                    dp[n][m] = dp[n - 1][m - 1] + dp[n - 1][m];
                } else {
                    dp[n][m] = dp[n - 1][m];
                }
            }
        }
        return dp[N][M];
    }

    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int dp[][] = new int[n + 1][m + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return minDistance_memo(word1, word2, n, m, dp);
    }

    private int minDistance_memo(String word1, String word2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            return dp[n][m] = n != 0 ? n : m;

        }
        if (dp[n][m] != -1)
            return dp[n][m];
        if (word1.charAt(n - 1) == word2.charAt(m - 1)) {
            return dp[n][m] = minDistance_memo(word1, word2, n - 1, m - 1, dp);
        } else {
            int replace = minDistance_memo(word1, word2, n - 1, m - 1, dp);
            int insert = minDistance_memo(word1, word2, n, m - 1, dp);
            int delete = minDistance_memo(word1, word2, n - 1, m, dp);
            return dp[n][m] = 1 + Math.min(insert, Math.min(delete, replace));
        }
    }

    // cost : {insert = a, replace = b, delete = c}
    public int minDistanceWithCost(String word1, String word2, int cost[]) {
        int n = word1.length();
        int m = word2.length();
        int dp[][] = new int[n + 1][m + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return minDistanceCost_memo(word1, word2, cost, n, m, dp);
    }

    private int minDistanceCost_memo(String word1, String word2, int cost[], int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            return dp[n][m] = n != 0 ? n * cost[2] : m * cost[0];

        }
        if (dp[n][m] != -1)
            return dp[n][m];
        if (word1.charAt(n - 1) == word2.charAt(m - 1)) {
            return dp[n][m] = minDistanceCost_memo(word1, word2, cost, n - 1, m - 1, dp);
        } else {
            int replace = minDistanceCost_memo(word1, word2, cost, n - 1, m - 1, dp) + cost[1];
            int insert = minDistanceCost_memo(word1, word2, cost, n, m - 1, dp) + cost[0];
            int delete = minDistanceCost_memo(word1, word2, cost, n - 1, m, dp) + cost[2];
            return dp[n][m] = 1 + Math.min(insert, Math.min(delete, replace));
        }
    }

    public boolean isMatch(String s, String p) {
        p = removeStars(p);
        int n = s.length();
        int m = p.length();
        int dp[][] = new int[n + 1][m + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return isMatchWildcard(s, p, n, m, dp) == 1;

    }

    private int isMatchWildcard(String s, String p, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            if (n == 0 && m == 0)
                return dp[n][m] = 1;
            if (n != 0 && m == 1 && p.charAt(m - 1) == '*') {
                return dp[n][m] = 1;
            }
            return dp[n][m] = 0;
        }

        if (dp[n][m] != -1)
            return dp[n][m];

        if (s.charAt(n - 1) == p.charAt(m - 1)) {
            return dp[n][m] = isMatchWildcard(s, p, n - 1, m - 1, dp);
        } else {
            if (p.charAt(m - 1) == '?') {
                return dp[n][m] = isMatchWildcard(s, p, n - 1, m - 1, dp);
            } else if (p.charAt(m - 1) == '*') {
                boolean res = isMatchWildcard(s, p, n, m - 1, dp) == 1 || isMatchWildcard(s, p, n - 1, m, dp) == 1;
                return dp[n][m] = (res ? 1 : 0);
            } else {
                return dp[n][m] = 0;
            }
        }

    }

    private String removeStars(String p) {
        if (p.length() == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        sb.append(p.charAt(0));
        for (int i = 1; i < p.length(); i++) {
            char ch = p.charAt(i);
            if (ch == '*' && sb.charAt(sb.length() - 1) == ch) {
                continue;
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean dp[] = new boolean[n + 1];
        dp[0] = true;
        HashSet<String> set = new HashSet<>();
        int maxLenWord = 0;
        for (String t : wordDict) {
            maxLenWord = Math.max(maxLenWord, t.length());
            set.add(t);
        }
        for (int i = 0; i <= n; i++) {
            if (!dp[i]) {
                continue;
            }
            for (int j = 1; j <= maxLenWord && i + j <= n; j++) {
                String subString = s.substring(i, i + j);
                if (set.contains(subString)) {
                    dp[i + j] = true;
                }
            }
        }
        return dp[n];
    }

    public List<String> wordBreakPrint(String s, List<String> wordDict) {
        int n = s.length();
        boolean dp[] = new boolean[n + 1];
        dp[0] = true;
        HashSet<String> set = new HashSet<>();
        int maxLenWord = 0;
        for (String t : wordDict) {
            maxLenWord = Math.max(maxLenWord, t.length());
            set.add(t);
        }
        for (int i = 0; i <= n; i++) {
            if (!dp[i]) {
                continue;
            }
            for (int j = 1; j <= maxLenWord && i + j <= n; j++) {
                String subString = s.substring(i, i + j);
                if (set.contains(subString)) {
                    dp[i + j] = true;
                }
            }
        }
        List<String> ans = new ArrayList<>();
        wordBreakPrint(s, 0, dp, ans, set, "", maxLenWord);
        return ans;
    }

    private void wordBreakPrint(String s, int idx, boolean[] dp, List<String> ans, HashSet<String> set, String asf,
            int maxLenWord) {
        int n = s.length();
        if (idx >= n) {
            ans.add(asf.substring(0, asf.length() - 1));
            return;
        }

        for (int j = 1; j <= maxLenWord && j + idx <= n; j++) {
            if (dp[idx + j]) {
                String subString = s.substring(idx, idx + j);
                if (set.contains(subString)) {
                    wordBreakPrint(s, idx + j, dp, ans, set, asf + subString + " ", maxLenWord);
                }
            }
        }
    }

}
