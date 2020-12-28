package com.hynial.preinter;

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
}
