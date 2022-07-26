package com.hynial.preinter.dstructalgorithm.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PermutationsJava {
    public List<String> permutations(char[] chars){
        List<String> ans = new ArrayList<>();
        if(chars.length == 1) {
            ans.add(new String(chars));
            return ans;
        }

        char[] cs = new char[chars.length - 1];
        System.arraycopy(chars, 1, cs, 0, chars.length - 1);
        List<String> subs = permutations(cs);
        for (int i = 0; i < subs.size(); i++) {
            String s = subs.get(i);
            char[] combine = (chars[0] + s).toCharArray();
            ans.add(new String(combine));
            for (int j = 1; j < combine.length; j++) {
                swap(combine, 0, j);
                ans.add(new String(combine));
            }
        }

        return ans;
    }

    public void swap(char[] chars, int i, int j){
        char t = chars[i];
        chars[i] = chars[j];
        chars[j] = t;
    }

    public List<String> permutation(String str){
        List<String> list = new ArrayList<>();
        if(str.length() == 1){
            list.add(str);
        }else{
            for (int i = 0; i < str.length(); i++) {
                List<String> temps = permutation(str.substring(0, i) + str.substring(i+1));
                for(String s : temps){
                    list.add(str.charAt(i) + s);
                }
            }
        }

        return list;
    }

    public List<String> permutation(String prefix, String str){
        List<String> list = new ArrayList<>();
        if(str.length() == 0){
            list.add(prefix);
        }else
            for (int i = 0; i < str.length(); i++) {
                list.addAll(permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1)));
            }

        return list;
    }

    public <T> List<T[]> permutations2(int n, T[] elements){
        List<T[]> ans = new ArrayList<>();
        int[] indexes = new int[n];
        List<int[]> indList = new ArrayList<>();

        ans.add(elements.clone());
        int i = 0;
        while (i < n) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ?  0: indexes[i], i);
                ans.add(elements.clone());
                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
            indList.add(indexes.clone());
            System.out.println(Arrays.toString(indexes.clone()) + Arrays.toString(elements.clone()));
        }

        return ans;
    }

    private <T> void swap(T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    private String change(String a, int i, int j){
        char[] arr = a.toCharArray();
        char t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
        return String.valueOf(arr);
    }

    public void permute(String a, int l, int r){
        if(l == r){
            System.out.println(a);
        }else{
            for (int i = l; i <= r; i++) {
                a = change(a, l, i);
                permute(a, l + 1, r);
                a = change(a, l, i);
            }
        }
    }

    public <T extends Comparable<T>> void printAllOrdered(T[] elements, char delimiter) {
        Arrays.sort(elements);
        boolean hasNext = true;

        while(hasNext) {
            printArray(elements, delimiter);
            int k = 0, l = 0;
            hasNext = false;
            for (int i = elements.length - 1; i > 0; i--) {
                if (elements[i].compareTo(elements[i - 1]) > 0) {
                    k = i - 1;
                    hasNext = true;
                    break;
                }
            }

            for (int i = elements.length - 1; i > k; i--) {
                if (elements[i].compareTo(elements[k]) > 0) {
                    l = i;
                    break;
                }
            }

            swap(elements, k, l);
            //System.out.println(Arrays.toString(elements));
            List<T> list = Arrays.asList(elements).subList(k + 1, elements.length);
            System.out.println(Arrays.toString(list.stream().toArray()));
            Collections.reverse(Arrays.asList(elements).subList(k + 1, elements.length));
        }
    }

    private <T> void printArray(T[] input, char delimiter) {
        System.out.print('\n');
        for(int i = 0; i < input.length; i++) {
            System.out.print(input[i]);
            System.out.print(delimiter);
        }
    }


    /**
     * insert - recommend
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

    /**
     * swap - recursive - recommend
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteRecursive(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        helper(0, nums, result);
        return result;
    }

    private void helper(int start, int[] nums, List<List<Integer>> result){
        if(start==nums.length-1){
            ArrayList<Integer> list = new ArrayList<>();
            for(int num: nums){
                list.add(num);
            }
            result.add(list);
            return;
        }

        for(int i=start; i<nums.length; i++){
            swap(nums, i, start);
            helper(start+1, nums, result);
            swap(nums, i, start);
        }
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
