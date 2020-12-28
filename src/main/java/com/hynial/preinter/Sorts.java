package com.hynial.preinter;

public class Sorts {

    /**
     * 快排
     * @param arr
     * @param l
     * @param r
     */
    public void quickSort(int[] arr, int l, int r){
        if(l < r) {
            int m = adjustsArr(arr, l, r);
            quickSort(arr, l, m - 1);
            quickSort(arr, m + 1, r);
        }
    }

    private int adjustsArr(int[] arr, int l, int r){
        int i = l, j = r;
        int temp = arr[i];

        while(i < j){
            while(i < j && arr[j] > temp){
                j--;
            }

            if(i < j){
                arr[i++] = arr[j];
            }

            while(i < j && arr[i] < temp){
                i++;
            }

            if(i < j){
                arr[j--] = arr[i];
            }
        }

        arr[i] = temp;

        return i;
    }

    /**
     * 简单选择排序
     * @param arr
     * @param len
     */
    public void chooseSort(int[] arr, int len){
        for(int i = 0; i < len - 1; i++){
            int minIndex = i, tmp = arr[i];
            for(int j = i + 1; j < len; j++){
                if(arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }

            if(i != minIndex){
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
    }
}
