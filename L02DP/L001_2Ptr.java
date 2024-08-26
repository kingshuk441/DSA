package L02DP;

import java.util.Arrays;

public class L001_2Ptr extends PrintArr {
    // 1_Faith
    // 2_Recursive_Tree
    // 3_RecursiveCode->Memoization
    // 4_Observation
    // 5_Tabulation
    // 6_Optimization
    public int fibo_memo(int n, int dp[]) {
        if (n == 0 || n == 1)
            return dp[n] = n;
        if (dp[n] != -1)
            return dp[n];
        int res = fibo_memo(n - 1, dp);
        if (n - 2 >= 0)
            res += fibo_memo(n - 2, dp);
        return dp[n] = res;
    }

    public int fibo_dp(int N) {
        int dp[] = new int[N + 1];
        for (int n = 0; n <= N; n++) {
            if (n == 0 || n == 1) {
                dp[n] = n;
                continue;
            }
            int res = dp[n - 1];
            if (n - 2 >= 0)
                res += dp[n - 2];
            dp[n] = res;
        }
        return dp[N];
    }

    public int fibo_opti(int N) {
        if (N == 0)
            return 0;
        if (N == 1)
            return 1;
        int a = 0, b = 1;
        for (int i = 2; i <= N; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public int mazePath_memo(int sr, int sc, int er, int ec, int dp[][], int dir[][]) {
        if (sr == er && sc == ec) {
            return dp[sr][sc] = 1;
        }
        if (dp[sr][sc] != -1)
            return dp[sr][sc];
        int res = 0;
        for (int d[] : dir) {
            int r = sr + d[0];
            int c = sc + d[1];
            if (r <= er && c <= ec) {
                res += mazePath_memo(r, c, er, ec, dp, dir);
            }
        }
        return dp[sr][sc] = res;
    }

    public int mazePath_tabu(int SR, int SC, int er, int ec, int dir[][]) {
        int dp[][] = new int[er + 1][ec + 1];
        for (int sr = er; sr >= 0; sr--) {
            for (int sc = ec; sc >= 0; sc--) {
                if (sr == er && sc == ec) {
                    dp[sr][sc] = 1;
                    continue;
                }

                int res = 0;
                for (int d[] : dir) {
                    int r = sr + d[0];
                    int c = sc + d[1];
                    if (r <= er && c <= ec) {
                        res += dp[r][c];
                    }
                }
                dp[sr][sc] = res;
            }
        }
        return dp[SR][SC];
    }

    public int mazePath_Jump_tabu(int SR, int SC, int er, int ec, int dir[][]) {
        int dp[][] = new int[er + 1][ec + 1];
        for (int sr = er; sr >= 0; sr--) {
            for (int sc = ec; sc >= 0; sc--) {
                if (sr == er && sc == ec) {
                    dp[sr][sc] = 1;
                    System.out.println("ss");
                    continue;
                }

                int res = 0;
                for (int d[] : dir) {
                    int r = sr + d[0];
                    int c = sc + d[1];
                    while (r >= 0 && c >= 0 && r <= er && c <= ec) {
                        res += dp[r][c];
                        r = r + d[0];
                        c = c + d[1];
                    }
                }
                dp[sr][sc] = res;
            }
        }
        display2D(dp);
        return dp[SR][SC];
    }

    public int mazePath_Jump_memo(int sr, int sc, int er, int ec, int dir[][], int dp[][]) {

        if (sr == er && sc == ec) {
            return dp[sr][sc] = 1;
        }
        if (dp[sr][sc] != -1)
            return dp[sr][sc];
        int res = 0;
        for (int d[] : dir) {
            int r = d[0] + sr;
            int c = d[1] + sc;
            while (r >= 0 && c >= 0 && r <= er && c <= ec) {
                res += mazePath_Jump_memo(r, c, er, ec, dir, dp);
                r = r + d[0];
                c = c + d[1];
            }
        }
        return dp[sr][sc] = res;
    }

    public int uniquePathsWithObstacles(int[][] arr) {
        int dir[][] = { { 1, 0 }, { 0, 1 } };
        int n = arr.length, m = arr[0].length;
        int er = n - 1, ec = m - 1;
        if (arr[er][ec] == 1 || arr[0][0] == 1)
            return 0;
        int dp[][] = new int[er + 1][ec + 1];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        return mazePath_Obst_memo(0, 0, er, ec, dir, dp, arr);
    }

    private int mazePath_Obst_memo(int sr, int sc, int er, int ec, int[][] dir, int dp[][], int arr[][]) {
        if (sr == er && sc == ec) {
            return dp[sr][sc] = 1;
        }
        if (dp[sr][sc] != -1)
            return dp[sr][sc];

        int res = 0;
        for (int d[] : dir) {
            int r = d[0] + sr;
            int c = d[1] + sc;
            if (r >= 0 && c >= 0 && r <= er && c <= ec && arr[r][c] != 1) {
                res += mazePath_Obst_memo(r, c, er, ec, dir, dp, arr);
            }
        }
        return dp[sr][sc] = res;
    }

    public int climbStairs(int n) {
        int dp[] = new int[n + 1];
        Arrays.fill(dp, -1);
        return climbStairs(n, dp);
    }

    private int climbStairs(int n, int[] dp) {
        if (n <= 1) {
            return dp[n] = 1;
        }
        if (dp[n] != -1)
            return dp[n];
        int res = 0;
        res += climbStairs(n - 1, dp);
        if (n - 2 >= 0)
            res += climbStairs(n - 2, dp);

        return dp[n] = res;
    }

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int dp[] = new int[n + 1];
        Arrays.fill(dp, -1);
        return minCostClimbingStairs(cost, n, dp);
    }

    private int minCostClimbingStairs(int[] cost, int n, int[] dp) {
        if (n == 0 || n == 1) {
            return dp[n] = cost[n];
        }
        if (dp[n] != -1)
            return dp[n];

        int oneStep = minCostClimbingStairs(cost, n - 1, dp), twoStep = 0;
        if (n - 2 >= 0) {
            twoStep = minCostClimbingStairs(cost, n - 2, dp);
        }
        int res = Math.min(twoStep, oneStep) + (n < cost.length ? cost[n] : 0);

        return dp[n] = res;

    }

    public int boardPath_memo(int sp, int ep, int dp[]) {
        if (sp == ep) {
            return dp[sp] = 1;
        }
        if (dp[sp] != -1)
            return dp[sp];
        int res = 0;
        for (int dice = 1; dice <= 6; dice++) {
            if (sp + dice <= ep)
                res += boardPath_memo(sp + dice, ep, dp);
        }
        return dp[sp] = res;
    }

    public int boardPath_tabu(int SP, int EP) {
        int dp[] = new int[EP + 1];
        for (int sp = EP; sp >= SP; sp--) {

            if (sp == EP) {
                dp[sp] = 1;
                continue;
            }

            int res = 0;
            for (int dice = 1; dice <= 6; dice++) {
                if (sp + dice <= EP)
                    res += dp[sp + dice];
            }
            return dp[sp] = res;
        }
        return dp[SP];
    }

    public int numDecodings(String s) {
        int n = s.length();
        int dp[] = new int[n + 1];
        Arrays.fill(dp, -1);
        return numDecodings_memo(s, 0, dp);
    }

    private int numDecodings_memo(String s, int idx, int[] dp) {
        int n = s.length();
        if (idx == n) {
            return dp[idx] = 1;
        }
        if (dp[idx] != -1)
            return dp[idx];

        int ch1 = s.charAt(idx) - '0';
        if (ch1 == 0)
            return dp[idx] = 0;
        int res = 0;
        res += numDecodings_memo(s, idx + 1, dp);
        if (idx + 1 < n) {
            int ch2 = s.charAt(idx + 1) - '0';
            int value = (10 * ch1) + ch2;
            if (value <= 26) {
                res += numDecodings_memo(s, idx + 2, dp);
            }
        }
        return dp[idx] = res;

    }

    private int numDecodings_tabu(String s, int IDX) {
        int n = s.length();
        int[] dp = new int[n + 1];
        for (int idx = n - 1; idx >= 0; idx--) {
            if (idx == n) {
                dp[idx] = 1;
                continue;
            }

            int ch1 = s.charAt(idx) - '0';
            if (ch1 == 0) {
                dp[idx] = 0;
                continue;
            }
            int res = 0;
            res += dp[idx + 1];
            if (idx + 1 < n) {
                int ch2 = s.charAt(idx + 1) - '0';
                int value = (10 * ch1) + ch2;
                if (value <= 26) {
                    res += dp[idx + 2];
                }
            }
            dp[idx] = res;
        }
        return dp[IDX];
    }

    private int numDecodings_Opti(String s) {
        int a = 1, b = 0;
        for (int idx = s.length() - 1; idx >= 0; idx--) {

            int c = a;
            int ch1 = s.charAt(idx) - '0';
            if (ch1 == 0) {
                b = a;
                a = 0;
                continue;
            }
            if (idx + 1 < s.length()) {
                int ch2 = s.charAt(idx + 1) - '0';
                int value = (10 * ch1) + ch2;
                if (value <= 26)
                    c += b;
            }
            b = a;
            a = c;
        }

        return a;

    }

    int mod = (int) 1e9 + 7;

    public int numDecodings2(String s) {
        long dp[] = new long[s.length() + 1];
        Arrays.fill(dp, -1);
        return (int) numDecodings2_memo(s, 0, dp);
    }

    private long numDecodings2_memo(String s, int idx, long[] dp) {
        if (idx == s.length()) {
            return dp[idx] = 1;
        }
        if (dp[idx] != -1)
            return dp[idx];
        char ch1 = s.charAt(idx);
        if (ch1 == '0')
            return dp[idx] = 0;
        long res = 0;
        if (ch1 == '*') {
            res = (res % mod + (9 * numDecodings2_memo(s, idx + 1, dp) % mod)) % mod;
        } else {
            res = (res % mod + numDecodings2_memo(s, idx + 1, dp) % mod) % mod;
        }
        if (idx + 1 < s.length()) {
            char ch2 = s.charAt(idx + 1);
            if (ch2 == '*') {
                if (ch1 == '1') {
                    res = (res % mod + (9 * numDecodings2_memo(s, idx + 2, dp) % mod)) % mod;
                } else if (ch1 == '2') {
                    res = (res % mod + (6 * numDecodings2_memo(s, idx + 2, dp) % mod)) % mod;
                } else if (ch1 == '*') {
                    res = (res % mod + (15 * numDecodings2_memo(s, idx + 2, dp) % mod)) % mod;
                }
            } else {
                if (ch1 == '*' && ch2 >= '0' && ch2 <= '6') {
                    res = (res % mod + (2 * numDecodings2_memo(s, idx + 2, dp) % mod)) % mod;
                } else if (ch1 == '*' && ch2 >= '7' && ch2 <= '9') {
                    res = (res % mod + (1 * numDecodings2_memo(s, idx + 2, dp) % mod)) % mod;
                }
                if (ch1 != '*') {
                    int val = (ch1 - '0') * 10 + (ch2 - '0');
                    if (val <= 26) {
                        res = (res % mod + (1 * numDecodings2_memo(s, idx + 2, dp) % mod)) % mod;
                    }
                }
            }

        }
        return dp[idx] = res;

    }

    // (a+b)%c = (a%c + b%c)%c
    // (a-b)%c = (a%c - b%c + c)%c
    // (a*b)%c = (a%c * b%c )%c
    private long numDecodings2_opti(String s) {
        long res = 0;
        long a = 1, b = 0;
        for (int idx = s.length() - 1; idx >= 0; idx--) {
            char ch1 = s.charAt(idx);
            res = 0;
            if (ch1 == '0') {
                b = a;
                a = 0;
                continue;
            }

            if (ch1 == '*') {
                res = (res % mod + (9 * a % mod)) % mod;
            } else {
                res = (res % mod + a % mod) % mod;
            }

            if (idx + 1 < s.length()) {
                char ch2 = s.charAt(idx + 1);
                if (ch2 == '*') {
                    if (ch1 == '1') {
                        res = (res % mod + (9 * b % mod)) % mod;
                    } else if (ch1 == '2') {
                        res = (res % mod + (6 * b % mod)) % mod;
                    } else if (ch1 == '*') {
                        res = (res % mod + (15 * b % mod)) % mod;
                    }
                } else {
                    if (ch1 == '*' && ch2 >= '0' && ch2 <= '6') {
                        res = (res % mod + (2 * b % mod)) % mod;
                    } else if (ch1 == '*' && ch2 >= '7' && ch2 <= '9') {
                        res = (res % mod + (1 * b % mod)) % mod;
                    }
                    if (ch1 != '*') {
                        int val = (ch1 - '0') * 10 + (ch2 - '0');
                        if (val <= 26) {
                            res = (res % mod + (1 * b % mod)) % mod;
                        }
                    }

                }
            }
            b = a;
            a = res;
        }

        return a;
    }

    static int maxGold(int n, int m, int arr[][]) {
        int dp[][] = new int[n][m];
        for (int d[] : dp)
            Arrays.fill(d, -1);
        int max = 0;
        int dir[][] = { { 0, 1 }, { 1, 1 }, { -1, 1 } };
        int startingRow = 0;
        for (int i = 0; i < n; i++) {
            int res = maxGold(arr, i, 0, dp, dir);
            if (res > max) {
                max = res;
                startingRow = i;
            }

        }
        goldMine_backEng(dp, startingRow, 0, "", dir);
        return max;
    }

    private static void goldMine_backEng(int[][] dp, int sr, int sc, String asf, int dir[][]) {
        if (sc == dp.length - 1) {
            System.out.println(asf + "(" + sr + ", " + sc + ") ");
            return;
        }
        int max = 0, idx = -1;
        for (int d = 0; d < dp.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if (r >= 0 && c >= 0 && r < dp.length && c < dp[0].length && dp[r][c] > max) {
                max = dp[r][c];
                idx = d;
            }
        }
        if (idx != -1) {
            int r = sr + dir[idx][0], c = sc + dir[idx][1];
            goldMine_backEng(dp, r, c, asf + "(" + sr + ", " + sc + ") ", dir);
        }
    }

    private static int maxGold(int[][] arr, int sr, int sc, int[][] dp, int dir[][]) {
        if (sc == arr[0].length - 1) {
            return dp[sr][sc] = arr[sr][sc];
        }
        if (dp[sr][sc] != -1)
            return dp[sr][sc];
        int max = 0;
        for (int d[] : dir) {
            int r = sr + d[0];
            int c = sc + d[1];
            if (r >= 0 && c >= 0 && r < arr.length && c < arr[0].length) {
                max = Math.max(maxGold(arr, r, c, dp, dir) + arr[sr][sc], max);
            }
        }
        return dp[sr][sc] = max;
    }

    // https:// www.geeksforgeeks.org/min-cost-path-dp-6/
    int MOD = (int) 1e9 + 7;

    public long countFriendsPairings(int n) {
        long dp[] = new long[n + 1];
        Arrays.fill(dp, -1);
        return countFriendsPairings(n, dp);
    }

    private long countFriendsPairings(int n, long[] dp) {
        if (n <= 1) {
            return dp[n] = n;
        }
        if (dp[n] != -1)
            return dp[n];
        long singleP = countFriendsPairings(n - 1, dp) % MOD;
        long doubleP = 0;
        if (n - 2 >= 0)
            doubleP = (countFriendsPairings(n - 2, dp) % MOD * (n - 1) % MOD) % MOD;

        return dp[n] = singleP + doubleP;

    }

    public int divideInKGroup(int n, int k) {
        int dp[][] = new int[n + 1][k + 1];
        return divideInKGroup(n, k, dp);
    }

    private int divideInKGroup(int n, int k, int[][] dp) {
        if (n == k || k == 1) {
            return dp[n][k] = 1;
        }
        if (dp[n][k] != -1)
            return dp[n][k];
        int singleP = divideInKGroup(n - 1, k - 1, dp);
        int doubleP = divideInKGroup(n - 1, k, dp) * k;
        return dp[n][k] = singleP + doubleP;
    }

}
