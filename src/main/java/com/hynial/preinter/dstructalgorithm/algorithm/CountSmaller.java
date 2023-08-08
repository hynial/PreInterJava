package com.hynial.preinter.dstructalgorithm.algorithm;

import java.util.*;
import java.util.stream.IntStream;

public class CountSmaller {
    public List<Integer> countSmaller(int[] nums) {
        if(nums == null) return null;
        if(nums.length == 0) return new ArrayList<>();

        int[] cp = Arrays.copyOf(nums, nums.length);
        Arrays.sort(cp);
        int[] indices = IntStream.range(0, nums.length).boxed().sorted(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return nums[integer] - nums[t1];
            }
        }).mapToInt(e -> {return e;}).toArray();

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int le = 0;
            for (int j = 0; j < cp.length && nums[i] > cp[j]; j++) {
                if(indices[j] > i) {
                    le++;
                }
            }

            result.add(le);
        }

        return result;
    }

    /**
     * 离散化树状数组 - fake
     * @param nums
     * @return
     */
    public List<Integer> countSmaller2(int[] nums) {
        if(nums == null) return null;
        if(nums.length == 0) return new ArrayList<>();

        Integer[] arr = new Integer[nums.length];
        List<Integer> result = new ArrayList<>();
        Collections.addAll(result, arr);

        Map<Integer, Integer> treeMap = new TreeMap<>();
        for(int i : nums){
            treeMap.put(i, 0);
        }

        for (int i = nums.length - 1; i > -1; i--) {
            int currentCount = treeMap.get(nums[i]);
            treeMap.put(nums[i], currentCount + 1);
            Iterator<Integer> keyIr = treeMap.keySet().iterator();
            int iCount = 0;
            while(keyIr.hasNext()){
                Integer keyCurrent = keyIr.next();
                if(keyCurrent < nums[i]) {
                    iCount += treeMap.get(keyCurrent);
                }else{
                    break;
                }
            }

            result.set(i, iCount);
        }

        return result;
    }

    public List<Integer> countSmaller3(int[] nums){
        if(nums == null) return null;
        if(nums.length == 0) return new ArrayList<>();

        List<Integer> result = new ArrayList<>();
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }

        int[] c = Arrays.stream(set.toArray()).sorted().mapToInt(e -> Integer.parseInt(e.toString())).toArray();
        int[] d = new int[c.length + 2];
        for (int i = nums.length - 1; i > -1; i--) {
            int idx = Arrays.binarySearch(c, nums[i]) + 1;
            int s = sum(d, idx - 1);
            update(d, idx, 1);
            result.add(s);
        }

        Collections.reverse(result);
        return result;
    }

    // k为i的二进制中从最低位到高位连续零的长度
    // 2^k = i&(i^(i-1));
    // 2^k = i&(-i);

    /*C[1] = A[1];
    C[2] = A[1] + A[2];
    C[3] = A[3];
    C[4] = A[1] + A[2] + A[3] + A[4];
    C[5] = A[5];
    C[6] = A[5] + A[6];
    C[7] = A[7];
    C[8] = A[1] + A[2] + A[3] + A[4] + A[5] + A[6] + A[7] + A[8];*/
    private int lowbit(int x){
        return x & (-x);
    }

    private void update(int[] c, int i, int delta){
        while( i <= c.length - 1 ){
            c[i] += delta;
            i += lowbit(i);
        }
    }

    private int sum(int[] c, int i){
        int ans = 0;
        while( i > 0 ){
            ans += c[i];
            i -= lowbit(i);
        }

        return ans;
    }

}
