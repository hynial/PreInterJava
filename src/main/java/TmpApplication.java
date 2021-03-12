public class TmpApplication {
    public static void main(String[] args) {
        int[] coins = {1, 3, 5};
        System.out.println(lowestCoins(coins, 11, 0));
    }

    // https://leetcode-cn.com/problems/coin-change/solution/322-ling-qian-dui-huan-by-leetcode-solution/
    public static int lowestCoins(int[] coins, int amount, Integer result){
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
}


// C B D D C B D B A C B A C B C