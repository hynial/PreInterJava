package com.hynial.preinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Finds {
    /**
     * 找出数组中每个数右边第一个比它大的元素--时间复杂度o(n)单调栈解法
     *
     * @param arr
     * @return
     */
    public int[] findMaxRight(int[] arr) {
        int[] maxRights = new int[arr.length];

        Stack<Integer> indexStack = new Stack<>();
        int i = 0;
        indexStack.push(i);
        while (i < arr.length) {
            if (!indexStack.empty() && arr[i] > arr[indexStack.peek()]) {
                maxRights[indexStack.peek()] = arr[i];
                indexStack.pop();
            } else {
                indexStack.push(i++);
            }
        }

        while (!indexStack.empty()) {
            maxRights[indexStack.pop()] = -1;
        }

        return maxRights;
    }

    public int[] findMaxRightCircle(int[] nums) {
        int n = nums.length;
        Stack<Integer> st = new Stack<>();
        int[] res = new int[nums.length];

        for (int i = 2 * n - 2; i >= 0; --i) {
            while (!st.empty() && nums[i % n] >= st.peek()) {
                st.pop();
            }

            res[i % n] = st.empty() ? -1 : st.peek();
            st.push(nums[i % n]);
        }

        return res;
    }

    /**
     * Next Permutation , 下一个排列
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        int i = len - 1;
        while (i > 0) {
            if (nums[i] > nums[i - 1]) {
                break;
            }
            i--;
        }

        if (i > 0) {
            int closest = len - 1;
            while (closest >= i) {
                if (nums[closest] > nums[i - 1]) {
                    break;
                }
                closest--;
            }

            int tmp = nums[i - 1];
            nums[i - 1] = nums[closest];
            nums[closest] = tmp;
        }

        int j = i, k = len - 1;
        while (j < k) {
            int tmp = nums[j];
            nums[j] = nums[k];
            nums[k] = tmp;
            j++;
            k--;
        }
    }

    /**
     * 全排列
     *
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
                for (int j = 0; j < l.size() + 1; j++) {
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

    /**
     * 组合数
     *
     * @param nums
     * @param n
     * @return
     */
    public List<List<Integer>> combination(int[] nums, int n) {
        List<List<Integer>> result = new ArrayList<>();

        List<Integer> src = Arrays.stream(nums).boxed().collect(Collectors.toList());
        List<Integer> tmp = new ArrayList<>(src.size());

        combine(src, 0, 0, n, tmp, result);

        return result;
    }

    /**
     * 递归找组合数
     *
     * @param src
     * @param srcIndex
     * @param i        tmp列表的下标
     * @param n
     * @param tmp      关键数据结构，用于存在每次生成的组合数
     * @param result
     */
    private void combine(List<Integer> src, int srcIndex, int i, int n, List<Integer> tmp, List<List<Integer>> result) {
        int j;
        for (j = srcIndex; j < src.size() - (n - 1); j++) {
            tmp.add(i, src.get(j));
            if (n == 1) {
                result.add(new ArrayList<Integer>(tmp.subList(0, i + 1)));
            } else {
                n--;
                i++;
                combine(src, j + 1, i, n, tmp, result);
                n++;
                i--;
            }
        }
    }

    public int combinationCount(int m, int n) {
        if (m < n)
            return 0; // 如果总数小于取出的数，直接返回0
        int k = 1;
        int j = 1;
        // 该种算法约掉了分母的(m-n)!,这样分子相乘的个数就是有n个了 ： m!/[n!(m-n)!], 如下k=m!/(m-n)!, 刚好有n次乘法
        for (int i = n; i >= 1; i--) {
            k = k * m;
            j = j * n;
            m--;
            n--;
        }

        return k / j;
    }
}
