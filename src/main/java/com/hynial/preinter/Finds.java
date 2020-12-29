package com.hynial.preinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Finds {
    /**
     * 找出数组中每个数右边第一个比它大的元素--时间复杂度o(n)单调栈解法
     * @param arr
     * @return
     */
    public int[] findMaxRight(int[] arr){
        int[] maxRights = new int[arr.length];

        Stack<Integer> indexStack = new Stack<>();
        int i = 0;
        indexStack.push(i);
        while(i < arr.length){
            if(!indexStack.empty() && arr[i] > arr[indexStack.peek()]){
                maxRights[indexStack.peek()] = arr[i];
                indexStack.pop();
            }else{
                indexStack.push(i++);
            }
        }

        while(!indexStack.empty()){
            maxRights[indexStack.pop()] = -1;
        }

        return maxRights;
    }

    /**
     * Next Permutation , 下一个排列
     * @param nums
     */
    public void nextPermutation(int[] nums){
        int len = nums.length;
        int i = len - 1;
        while(i > 0){
            if(nums[i] > nums[i - 1]){
                break;
            }
            i--;
        }

        if(i > 0){
            int closest = len - 1;
            while(closest >= i){
                if (nums[closest] > nums[i - 1]){
                    break;
                }
                closest--;
            }

            int tmp = nums[i - 1];
            nums[i - 1] = nums[closest];
            nums[closest] = tmp;
        }

        int j = i, k = len - 1;
        while( j < k){
            int tmp = nums[j];
            nums[j] = nums[k];
            nums[k] = tmp;
            j++;
            k--;
        }
    }

    /**
     * 全排列
     * @param num
     * @return
     */
    public List<List<Integer>> permute(int[] num) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        //start from an empty list
        result.add(new ArrayList<Integer>());

        for (int i = 0; i < num.length; i++) {
            //list of list in current iteration of the array num
            ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();

            for (List<Integer> l : result) {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size()+1; j++) {
                    // + add num[i] to different locations
                    l.add(j, num[i]);

                    ArrayList<Integer> temp = new ArrayList<Integer>(l);
                    current.add(temp);

                    // - remove num[i] add
                    l.remove(j);
                }
            }

            result = new ArrayList<List<Integer>>(current);
        }

        return result;
    }
}
