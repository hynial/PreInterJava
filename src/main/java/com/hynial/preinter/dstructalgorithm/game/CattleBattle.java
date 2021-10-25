package com.hynial.preinter.dstructalgorithm.game;

import java.util.Arrays;
import java.util.Random;

public class CattleBattle {
    public static void main(String[] args) {
        CattleBattle cattleBattle = new CattleBattle();
        // test 1
        int[] ownPokes1 = new int[]{1,3,7,6,10}; // output 1/7
        int[] ownPokes2 = new int[]{10,11,1,12,9}; // output 1/0
        int[] ownPokes3 = new int[]{1,2,3,4,5}; // output 1/5
        int[] ownPokes4 = new int[]{8,2,8,9,4}; // output 1/1
        int[] ownPokes5 = new int[]{10,2,14,9,5}; // output 0/14

        int[] r;
        r = cattleBattle.doBattle(ownPokes1);
        System.out.println(Arrays.toString(r));
        r = cattleBattle.doBattle(ownPokes2);
        System.out.println(Arrays.toString(r));
        r = cattleBattle.doBattle(ownPokes3);
        System.out.println(Arrays.toString(r));
        r = cattleBattle.doBattle(ownPokes4);
        System.out.println(Arrays.toString(r));
        r = cattleBattle.doBattle(ownPokes5);
        System.out.println(Arrays.toString(r));

        // test 2
        int scale = 10;
        for(int i = 0; i < scale; i++ ){
            int[] c = randomInt5();
            int[] v = cattleBattle.doBattle(c);
            System.out.println(Arrays.toString(c) + "\t\t ==> \t" + v[0] + "-" + v[1]);
        }
    }

    static int[] randomInt5(){
//        return new Random().ints(5, 1, 16).toArray();
        int[] res = new int[5];
        Random rd = new Random();
        for (int i = 0; i < res.length; i++) {
            res[i] = rd.nextInt(15) + 1;
        }

        return res;
    }

    /**
     * 扑克牌数，14表示小王，15表示大王
     */
    private int[] pokes = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    public int[] doBattle(int[] dispatchedPokes) {
        assert dispatchedPokes != null && dispatchedPokes.length == 5;

        int[] result = new int[]{0, -1};
        long operate = 0;
        for (int i = 0; i < dispatchedPokes.length; i++) {
            int valChange1 = dispatchedPokes[i] < 10 ? dispatchedPokes[i] : 10;
            for (int j = i + 1; j < dispatchedPokes.length; j++) {
                int valChange2 = dispatchedPokes[j] < 10 ? dispatchedPokes[j] : 10;
                for (int z = j + 1; z < dispatchedPokes.length; z++) {
                    int valChange3 = dispatchedPokes[z] < 10 ? dispatchedPokes[z] : 10;
                    if ((valChange1 + valChange2 + valChange3) % 10 == 0){
                        result[0] = 1;
                        int lastVal = 0;
                        for(int k = 0; k < dispatchedPokes.length; k++){
                            if(k != i && k != j && k != z){
                                lastVal += dispatchedPokes[k] < 10 ? dispatchedPokes[k] : 10;
                            }
                        }
                        result[1] = lastVal % 10;
                        return result;
                    }
                    operate++;
                }
            }
        }

        result[1] = Arrays.stream(dispatchedPokes).max().getAsInt();
//        System.out.println("Operates:" + operate);
        return result;
    }
}
