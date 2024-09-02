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

    public int longestPalinSubseq_print(String s) {
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
        String ans = longestPalinSubseq_print(s, 0, n - 1, dp);
        return ans.length();
    }

    private String longestPalinSubseq_print(String s, int i, int j, int[][] dp) {
        if (i >= j) {
            return (i == j) ? s.charAt(i) + "" : "";
        }
        if (s.charAt(i) == s.charAt(j)) {
            String returnString = s.charAt(i) + longestPalinSubseq_print(s, i + 1, j - 1, dp) + s.charAt(i);
            return returnString;
        } else {
            if (dp[i][j - 1] > dp[i + 1][j]) {
                return longestPalinSubseq_print(s, i, j - 1, dp);
            } else {
                return longestPalinSubseq_print(s, i + 1, j, dp);
            }
        }
    }

    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int dp[][] = new int[n + 1][m + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return maxDotProduct(nums1, nums2, n, m, dp);
    }

    private int maxDotProduct(int[] nums1, int[] nums2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            return dp[n][m] = -(int) 1e9;
        }
        if (dp[n][m] != -1)
            return dp[n][m];
        int val = nums1[n - 1] * nums2[m - 1];

        return dp[n][m] = Math.max(val + maxDotProduct(nums1, nums2, n - 1, m - 1, dp), Math.max(
                maxDotProduct(nums1, nums2, n, m - 1, dp),
                Math.max(maxDotProduct(nums1, nums2, n - 1, m, dp), val)));
    }

    public int longestCommonSubstr(String str1, String str2) {
        int n = str1.length(), m = str2.length();
        int max = 0;
        int dp[][] = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }

    public String longestPalindrome(String s) {
        int n = s.length();
        boolean dp[][] = new boolean[n][n];
        int len = 1, st = 0;
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (gap == 0) {
                    dp[i][j] = true;
                    continue;
                }
                if (gap == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                }
                if (s.charAt(i) == s.charAt(j)) {
                    if (dp[i + 1][j - 1]) {
                        dp[i][j] = true;
                    }
                }
                if (dp[i][j]) {
                    if (gap + 1 > len) {
                        len = gap + 1;
                        st = j - gap;
                    }
                }
            }
        }
        return s.substring(st, st + len);

    }

    public int minCut(String s) {
        int n = s.length();
        boolean pdp[][] = new boolean[n][n];
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (gap == 0) {
                    pdp[i][j] = true;
                    continue;
                }
                if (gap == 1) {
                    pdp[i][j] = (s.charAt(i) == s.charAt(j));
                }
                if (s.charAt(i) == s.charAt(j)) {
                    if (pdp[i + 1][j - 1]) {
                        pdp[i][j] = true;
                    }
                }
            }
        }
        int dp[] = new int[n + 1];
        Arrays.fill(dp, (int) 1e9);
        return palindromePartioning2(s, 0, pdp, dp);

    }

    private int palindromePartioning2(String s, int idx, boolean[][] pdp, int[] dp) {
        if (idx == s.length()) {
            return dp[idx] = -1;
        }
        // if (pdp[idx][s.length() - 1])
        // return 0;
        if (dp[idx] != (int) 1e9)
            return dp[idx];
        int minCuts = (int) 1e8;
        for (int cut = idx; cut < s.length(); cut++) {
            if (pdp[idx][cut])
                minCuts = Math.min(minCuts, palindromePartioning2(s, cut + 1, pdp, dp) + 1);
        }

        return dp[idx] = minCuts;
    }

    int MOD = (int) 1e9 + 7;

    public int countSubSeq(String s) {
        int n = s.length();
        int empty = 1, aSeq = 0, bSeq = 0, cSeq = 0;
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == 'a') {
                int sum = 2 * aSeq + empty;
                aSeq = sum;
            } else if (ch == 'b') {
                int sum = 2 * bSeq + aSeq;
                bSeq = sum;
            } else {
                int sum = 2 * cSeq + bSeq;
                cSeq = sum;
            }
        }
        return cSeq;
    }

    public boolean isMatchRE(String s, String p) {
        int dp[][] = new int[s.length() + 1][p.length() + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        p = removeStars(p);
        return regularExpression(s, p, s.length(), p.length(), dp) == 1;
    }

    private int regularExpression(String s, String p, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            if (n == 0 && m == 0)
                return dp[n][m] = 1;
            else if (n == 1 && m == 1 && p.charAt(m - 1) == '.')
                return dp[n][m] = 1;
            else if (n != 0 && m == 2 && p.charAt(m - 1) == '*') {
                char prevChar = p.charAt(m - 2);
                while (n-- > 0) {
                    if (prevChar != s.charAt(n - 1))
                        return dp[n][m] = 0;
                }
                return dp[n][m] = 1;
            } else if (n == 0 && m > 0 && p.charAt(m - 1) == '*') {
                int k = m;

                while (k > 0) {
                    if (p.charAt(k - 1) != '*') {
                        return dp[n][m] = 0;
                    }
                    k -= 2;
                }
                return dp[n][m] = 1;
            }
            return dp[n][m] = 0;
        }
        if (dp[n][m] != -1)
            return dp[n][m];
        if (s.charAt(n - 1) == p.charAt(m - 1) || p.charAt(m - 1) == '.') {
            return dp[n][m] = regularExpression(s, p, n - 1, m - 1, dp);
        } else {
            char ch = p.charAt(m - 1);
            if (ch == '*') {
                if (m - 2 >= 0) {
                    char prevChar = p.charAt(m - 2);
                    int zero = regularExpression(s, p, n, m - 2, dp), notZero = 0;
                    if (prevChar == s.charAt(n - 1) || prevChar == '.') {
                        notZero = regularExpression(s, p, n - 1, m, dp);
                    }
                    return dp[n][m] = zero == 1 ? zero : notZero;
                }
            }

        }
        return dp[n][m] = 0;

    }

    public int palindromePartition(String s, int k) {
        int n = s.length();
        int minChanges[][] = getMinChangesToMakePalindrome(s);
        int dp[][] = new int[n + 1][k + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return palindromePartioning3(s, k, minChanges, 0, dp);
    }

    private int palindromePartioning3(String s, int k, int[][] minChanges, int idx, int[][] dp) {
        if (idx >= s.length()) {
            if (k == 0)
                return dp[idx][k] = 0;
            else
                return dp[idx][k] = (int) 1e8;
        }
        if (k == 1 || s.length() - idx == k) {
            if (k == 1)
                return dp[idx][k] = minChanges[idx][s.length() - 1];
            return dp[idx][k] = 0;
        }

        if (dp[idx][k] != -1)
            return dp[idx][k];
        int min = (int) 1e9;
        for (int j = idx; j < s.length(); j++) {
            int convertFirstPart = minChanges[idx][j];
            int secondPart = palindromePartioning3(s, k - 1, minChanges, j + 1, dp);
            min = Math.min(min, convertFirstPart + secondPart);
        }
        return dp[idx][k] = min;

    }

    private int[][] getMinChangesToMakePalindrome(String s) {
        int n = s.length();
        int minChanges[][] = new int[n][n];
        for (int gap = 1; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; j++, i++) {
                if (gap == 1) {
                    minChanges[i][j] = s.charAt(i) == s.charAt(j) ? 0 : 1;
                } else {
                    minChanges[i][j] = s.charAt(i) == s.charAt(j) ? minChanges[i + 1][j - 1]
                            : minChanges[i + 1][j - 1] + 1;
                }
            }
        }
        return minChanges;
    }

}
