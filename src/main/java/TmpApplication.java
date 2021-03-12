import java.util.Arrays;

public class TmpApplication {
    public static void main(String[] args) {
        int[] coins = {1, 3, 5};
        System.out.println(lowestCoins(coins, 11, 0));
        System.out.println(lowestCoins(coins, 11, new int[11]));
        System.out.println(lowestCoins(coins, 11));
    }

    // https://leetcode-cn.com/problems/coin-change/solution/322-ling-qian-dui-huan-by-leetcode-solution/
    public static int lowestCoins(int[] coins, int amount, Integer result){
        // 此非最优解法，类似贪心算法，每次只吃最大的那个，最大的那个并不能满足最优
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
}