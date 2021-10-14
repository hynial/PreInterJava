package com.hynial.preinter.dstructalgorithm.algorithm.interview;

import java.util.Arrays;
import java.util.OptionalInt;

/**
 * 网宿科技 - java 开发面试题
 * 牛客题 - 容器盛最多水问题
 * 对比LeetCode 11 题
 * 一个由正整数组成的数组中，每个数组元素表示从地上依次叠起的砖头数量，假设砖头之间都不漏水并且考虑重力的情况下，可以在砖头上盛满多少水，一块砖头相当于一格水。
 */
public class MostWater {
    public static void main(String[] args) {
        int[] bricks = new int[]{1,8,6,2,5,4,8,3,7};
        int runTimes = 1;
        OptionalInt maxOpt = Arrays.stream(bricks).max();
        int max = maxOpt.getAsInt();
        System.out.println("Max:" + max);
        long start1 = System.nanoTime();
        int water = waterCalculate(bricks, max);
        for(int t = 1; t < runTimes; t++){
            waterCalculate(bricks, max);
        }
        long delta1 = System.nanoTime() - start1;
        System.out.println("Ans1:" + water + ", consume（ms ， 1ms = 1000000 ns）:" + delta1/1000000.0);
        long start2 = System.nanoTime();
        int water2 = waterCalculate2(bricks);
        for(int t = 1; t < runTimes; t++){
            waterCalculate2(bricks);
        }
        long delta2 = System.nanoTime() - start2;
        System.out.println("Ans2:" + water2 + ", consume:" + delta2/1000000.0);

        long start21 = System.nanoTime();
        int water21 = waterCalculate2_1(bricks);
        long delta21 = System.nanoTime() - start21;
        System.out.println("Ans2_1:" + water21 + ", consume:" + delta21/1000000.0);


        long start3 = System.nanoTime();
        int water3 = waterCalculate3(bricks, max);
        for(int t = 1; t < runTimes; t++){
            waterCalculate3(bricks, max);
        }
        long delta3 = System.nanoTime() - start3;
        System.out.println("Ans3:" + water3 + ", consume:" + delta3/1000000.0);

        // leet-code 11
        int maxArea = maxArea(bricks);
        System.out.println("MaxArea:" + maxArea);
    }

    /**
     * 方法一
     * 以每一格水为研究对象，每一格水存在的条件是左右两侧都必须是有水或者砖头。
     *
     * @param bricks
     * @return
     */
    private static int waterCalculate(int[] bricks, int max) {
        int ans = 0;
        int len = bricks.length;
        int[][] flags = new int[len][max];
        for (int i = 0; i < len; i++) {
            int bs = bricks[i];
            for (int j = 0; j < bs; j++) {
                flags[i][j] = 1;
            }
        }

        printDimension(flags);
        System.out.println("----------");
        printRotation90(flags, max);
        System.out.println("----------");

        for(int i = 1; i < len; i++){
            for(int j = bricks[i]; j < max; j++) {
                if(flags[i - 1][j] == 1){
                    for(int z = i + 1; z <= len - 1; z++){
                        if(flags[z][j] == 1){
                            flags[i][j] = 1;
                            ans++;
                            break;
                        }
                    }
                }

                if(flags[i][j] != 1){
                    break;
                }
            }
        }
//        System.out.println("----------");
//        printDimension(flags);
        return ans;
    }

    private static void printDimension(int[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
//                if (d[i][j] > 0) {
//                    System.out.print(String.format("%d ", d[i][j]));
//                } else {
//                    break;
//                }
                System.out.print(String.format("%d ", d[i][j]));
            }
            System.out.println();
        }
    }

    private static void printRotation90(int[][] d, int max){
        for(int i = max - 1; i >= 0; i--){
            for(int j = 0; j < d.length; j++){
                System.out.print(String.format("%d", d[j][i]));
            }
            System.out.println();
        }
    }

    /**
     * 方法二
     * 以数组最前和最后两端的高度作为研究对象，一开始计算出由低的那端平行能盛满的水量，再依次计算向内高于低端的高度和高高端的水量，直到两者索引距离小于1则找出所有水量。
     *
     * @param bricks
     * @return
     */
    private static int waterCalculate2(int[] bricks) {
        int ans = 0;
        int left = 0, right = bricks.length - 1, deep = 0;

        while(right >= left + 2) {
            int localAns = currentScale(bricks, left, right, deep);
            ans += localAns;

            deep = bricks[left] <= bricks[right] ? bricks[left] : bricks[right];
            if(bricks[left] <= bricks[right]){
                for(int i = left + 1; i <= right; i++){
                    if(bricks[i] >= bricks[left]){
                        left = i;
                        break;
                    }
                }
            }else{
                for(int j = right - 1; j >= left; j--){
                    if(bricks[j] >= bricks[right]){
                        right = j;
                        break;
                    }
                }
            }
        }

        return ans;
    }

    private static int currentScale(int[] bricks, int leftIndex, int rightIndex, int deep){
        int miner = (bricks[leftIndex] <= bricks[rightIndex] ? bricks[leftIndex] : bricks[rightIndex]);
        int ans = 0;
        for(int i = leftIndex + 1; i < rightIndex; i++){
            int delta = miner - bricks[i];
            if(delta > 0){
                ans += (delta - (deep > bricks[i] ? (deep - bricks[i]) : 0));
            }
        }

        return ans;
    }

    private static int waterCalculate2_1(int[] bricks) {
        int left = 0;
        int right = bricks.length - 1;
        int leftMax = bricks[left], rightMax = bricks[right];
        int ans = 0;

        while(left < right){
            if(bricks[left] < bricks[right]){
                left++;
                leftMax = bricks[left] > leftMax ? bricks[left] : leftMax;
                ans += leftMax - bricks[left];
            }else{
                right--;
                rightMax = bricks[right] > rightMax ? bricks[right] : rightMax;
                ans += rightMax - bricks[right];
            }
        }

        return ans;
    }

    /**
     * 方法三
     * 假设1代表砖头，0代表空（可留住水，不可留住水），找出所有的可留住水，即为所有水量。可通过计算所有的0个数，然后去掉所有的不可留住水。
     * 不可留住水为两侧连续的0的个数。
     * @param bricks
     * @return
     */
    private static int waterCalculate3(int[] bricks, int max){
        // 分配二维数组空间
        int ans = 0;
        int len = bricks.length;
        int[][] flags = new int[max][len];
        // 按列标记
        for (int i = 0; i < len; i++) {
            int bs = bricks[i];
            for (int j = 0; j < bs; j++) {
                flags[j][i] = 1;
            }
        }

//        printDimension(flags);

        // 按行取值
        for(int i = 0; i < flags.length; i++){
            int rowAns = 0, leftSideIndex = -1, rightSideIndex = -1;

            for(int j = 0; j < flags[i].length; j++){
                if(flags[i][j] == 1 && leftSideIndex == -1){
                    leftSideIndex = j;
                }else if(flags[i][j] == 1){
                    rightSideIndex = j;
                }
            }

            if(leftSideIndex >= rightSideIndex - 1){
                break;
            }

            for(int j = leftSideIndex + 1; j < rightSideIndex; j++){
                if(flags[i][j] == 0){
                    rowAns++;
                }
            }

            ans += rowAns;
        }

        return ans;
    }

    /**
     * leetCode 11
     * 求能盛满水的最大面积
     * @param bricks
     * @return
     */
    private static int maxArea(int[] bricks){
        int left = 0;
        int right = bricks.length - 1;
        int maxArea = 0;
        while(left < right){
            int current = Math.min(bricks[left], bricks[right]) * (right - left);
            maxArea = Math.max(maxArea, current);

            if(bricks[left] < bricks[right]){
                left++;
            }else{
                right--;
            }
        }

        return maxArea;
    }
}
