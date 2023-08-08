package com.hynial.preinter.dstructalgorithm.algorithm.coinchange;

import java.util.Arrays;
import java.util.Collections;

/**
 * Return the number of combinations that make up that amount. If that amount of money cannot be made up by any combination of the coins, return 0.
 * https://leetcode.cn/problems/coin-change-ii/solution/ling-qian-dui-huan-iihe-pa-lou-ti-wen-ti-dao-di-yo/
 */
public class CoinChange2 {

    public static void main(String[] args) {
        int[] coins = {2, 1, 5};
        int amount = 11;
//        int[] coins = {3,5,7,8,9,10,11};
//        int amount = 500;
//        int[] coins = {1, 2};
//        int amount = 3;

        Arrays.sort(coins);
        for (int left = 0, right = coins.length - 1; left < right; left++, right--) {
            int temp = coins[left];
            coins[left] = coins[right];
            coins[right] = temp;
        }

        Arrays.stream(coins).forEach(System.out::println);
        System.out.println(lowestCoinsBruteForce(coins, amount));
        System.out.println(topDown(amount, coins, new int[amount][coins.length], 0));
        System.out.println(findWays(coins.length - 1, amount, coins));
        System.out.println(changeMemoized(amount, coins));
        System.out.println(bottomUp(amount, coins));
        System.out.println(change(amount, coins));
    }

    public static int lowestCoinsBruteForce(int[] coins, int amount) {
        int[] highs = new int[coins.length];
        for (int i = 0; i < coins.length; i++) {
            highs[i] = amount / coins[i];
        }

        return recurseByItem(coins.length - 1, coins, highs, amount);
    }

    private static int recurseByItem(int level, int[] coins, int[] highs, int amount) {
        int result = 0;
        if (level < 0) {
            return result;
        }

        for (int i = 0; i <= highs[level]; i++) {
            int tAmount = amount - i * coins[level];

            if (tAmount == 0) {
                result++;
            }

            if (tAmount > 0) {
                result += recurseByItem(level - 1, coins, highs, tAmount);
            }
        }

        return result;
    }

    // 前面用过的硬币后面不能再用
    public static int topDown(int amount, int[] coins, int[][] mark, int cur) {
        int r = 0;
        if (amount == 0) return 1;
        if (amount < 0) return 0;

        if (mark[amount - 1][cur] > 0) {
            return mark[amount - 1][cur];
        }

        r += topDown(amount - coins[cur], coins, mark, cur);

        for (int c = cur + 1; c < coins.length; c++) {
            r += topDown(amount - coins[c], coins, mark, c);
        }

        mark[amount - 1][cur] = r;

        return r;
    }

    // 类似于组合加法：C(m, n) = C(m-1, n) + C(m-1, n-1)
    // 少见的通过两个维度来寻求子问题的解 1 + 1 ，是我少见多怪了
    public static int findWays(int ind, int amount, int[] coins) {
        if (ind == 0) {
            if (amount % coins[0] == 0) return 1;
            else return 0;
        }
        int dontPick = 0 + findWays(ind - 1, amount - 0, coins);
        int pick = 0;
        if (coins[ind] <= amount) pick = findWays(ind, amount - coins[ind], coins);
        return dontPick + pick;
    }

    public static int findWays2(int ind, int amount, int[] coins, int[][] dp) {
        if (ind == 0) {
            if (amount % coins[0] == 0) return 1;
            else return 0;
        }
        if (dp[ind][amount] != -1) return dp[ind][amount];
        int dontPick = 0 + findWays2(ind - 1, amount - 0, coins, dp);

        int pick = 0;
        if (coins[ind] <= amount) pick = findWays2(ind, amount - coins[ind], coins, dp);

        return dp[ind][amount] = dontPick + pick;
    }

    public static int changeMemoized(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];
        for (int[] arr : dp) Arrays.fill(arr, -1);
        return findWays2(n - 1, amount, coins, dp);
    }

    public static int bottomUp(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];

        for (int i = 0; i <= amount; i++) {
            if (i % coins[0] == 0) dp[0][i] = 1;
            else dp[0][i] = 0;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= amount; j++) {
                int dontPick = dp[i - 1][j];
                int pick = 0;
                if (coins[i] <= j) pick = dp[i][j - coins[i]];
                dp[i][j] = dontPick + pick;
            }
        }

        return dp[n - 1][amount];
    }

    // 对比上述方法，道理相同，写法不同，代码更简洁，空间占用更少
    public static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
}
