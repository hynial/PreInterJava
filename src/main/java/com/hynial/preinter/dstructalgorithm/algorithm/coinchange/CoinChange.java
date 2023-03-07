package com.hynial.preinter.dstructalgorithm.algorithm.coinchange;

import java.util.Arrays;

public class CoinChange {
    public static void main(String[] args) {
        int[] coins = {22, 32, 52, 2, 5};
        int amount = 17;
//        int[] coins = {2, 3, 5};
//        int amount = 17;
//        int[] coins = {19, 3, 5, 12, 13, 21};
//        int amount = 170;
        System.out.println(lowestCoins(coins, amount, 0));
        System.out.println(lowestCoins(coins, amount, new int[amount]));
        System.out.println(lowestCoins(coins, amount));
        System.out.println(lowestCoinsBruteForce(coins, amount));

    }
    // https://leetcode-cn.com/problems/coin-change/solution/322-ling-qian-dui-huan-by-leetcode-solution/
    public static int lowestCoins(int[] coins, int amount, Integer result){
        // 此非最优解法，类似贪心算法，每次只吃最大的那个，最大的那个并不能满足最优 - 需要先排序
        // int[] coins = {22, 32, 52, 2, 5}; bug .e.g.
        //        int amount = 17;
        if(amount < coins[0]) return -1;
        if(amount == 0) return result;
        if(amount < 0) return -1;
        for (int i = coins.length - 1; i > -1; i--) {
            if(amount >= coins[i]){
                amount = amount - coins[i];
                lowestCoins(coins, amount, ++result);
            }
        }

        return result;
    }

    /**
     * Dynamic programming  - Top down
     * @param coins
     * @param amount
     * @param count
     * @return
     */
    public static int lowestCoins(int[] coins, int amount, int[] count){
        if(amount == 0) return 0;
        if(amount < 0) return -1;
        if(count[amount - 1] != 0){
            return count[amount - 1];
        }

        int min = Integer.MAX_VALUE;
        for(int c : coins){
            int res = lowestCoins(coins, amount - c, count);
            if(res >= 0 && res < min){
                min = res + 1;
            }
        }

        count[amount - 1] = (min == Integer.MAX_VALUE) ? -1 : min;

        return count[amount - 1];
    }

    /**
     * Dynamic programming - Bottom up
     * @param coins
     * @param amount
     * @return
     */
    public static int lowestCoins(int[] coins, int amount){
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for(int i = 1; i <= amount; i++){
            for(int j = 0; j < coins.length; j++){
                if(coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static int lowestCoinsBruteForce(int[] coins, int amount) {
        int[] highs = new int[coins.length];
        for (int i = 0; i < coins.length; i++) {
            highs[i] = amount / coins[i];
        }

        int result = Integer.MAX_VALUE;
        result = recurseByItem(coins.length - 1, coins, highs, amount, result, 0);

        return result < Integer.MAX_VALUE ? result : -1;
    }

    // 消除递归的方法，可以采用代码来写代码的方式，通过事先计算出循环的个数，第一份代码用来确定循环的个数并生成代码，然后执行所生成的代码便不存在递归。
    // 此法消除了递归后的代码，会存在大量嵌套循环，有点类似于编译器的循环展开之说。
    // 也叫做 跑在前面的时间，总能在一个抽象层级上左右后面时间所发生的效果。这个有点类似于JavaAgent,因为Agent在前面执行了，它定义好了一系列的规则，后面凡是触犯到了
    // 已定义的规则就会有对应的已定义的规则处理程序执行(这里就是写代码或者字节码的过程)，从而左右了输出结果。如果这是一个优势的话，就叫它先前优势。如果是劣势的话，就叫做谁叫你排在了后面。
    // 任何一件事的发生，若要确保精准如预期，一定要再三审查是否被事先插上了一腿，如果无法事先审查到，在运行期确得万分留意那细微的变化。
    // 这一腿，是否可以通过签名避免？安全还是来自于密码学? Confidence / BelieveOther ?
    private static int recurseByItem(int level, int[] coins, int[] highs, int amount, int result, int preCount) {
        if (level < 0) {
            return result;
        }

        for (int i = 0; i <= highs[level]; i++) {
            int tAmount = amount - i * coins[level];

            if (tAmount == 0) {
                if (preCount + i < result) {
                    result = preCount + i;
                }
            }

            if (tAmount > 0) {
                int t = recurseByItem(level - 1, coins, highs, tAmount, result, preCount + i);
                if (t < result) {
                    result = t;
                }
            }
        }

        return result;
    }
}
