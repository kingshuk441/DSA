package L02DP;

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

        if (sr == ec && sc == ec) {
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

}
