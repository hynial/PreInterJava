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

    /**
     * 改进的简单选择排序
     * @param arr
     * @param len
     */
    public void chooseSortUpgrade(int[] arr, int len){
        int half = len / 2;
        for(int i = 0; i < half; i++){
            int arrIndex = i,tmp;
            int arrIndex1 = len - 1 - i,tmp1;
            //        Method 1
            //        for(int j = i+1;j < len - i + 1; j++){
            //            if(arr[arrIndex] > arr[j]){
            //                arrIndex = j;
            //            }
            //        }
            //        if(i != arrIndex){
            //            tmp = arr[i];
            //            arr[i] = arr[arrIndex];
            //            arr[arrIndex] = tmp;
            //        }
            //        for(int j = i;j < len - i - 1; j++){
            //            if(arr[arrIndex1] < arr[j]){
            //                arrIndex1 = j;
            //            }
            //        }
            //        if(i != len - 1 - i && len - 1 - i != arrIndex1){
            //            tmp1 = arr[len - 1 - i];
            //            arr[len - 1 - i] = arr[arrIndex1];
            //            arr[arrIndex1] = tmp1;
            //        }
            //        Method 2
            for(int j = i;j < len - i; j++){
                if(arr[arrIndex] > arr[j]){
                    arrIndex = j;
                }
                if(arr[arrIndex1] < arr[j]){
                    arrIndex1 = j;
                }
            }
            if(i != arrIndex){
                tmp = arr[i];
                arr[i] = arr[arrIndex];
                arr[arrIndex] = tmp;
            }
            if(arrIndex1 == i){
                arrIndex1 = arrIndex;
            }
            if(i != len - 1 - i && len - 1 - i != arrIndex1){
                tmp1 = arr[len - 1 - i];
                arr[len - 1 - i] = arr[arrIndex1];
                arr[arrIndex1] = tmp1;
            }
        }
    }

    /**
     * 冒泡排序
     * @param arr
     * @param len
     */
    public void bubbleSort(int arr[],int len){
        //    Method 1
        //    int tmp;
        //    for(int i = 0; i < len - 1; i++){
        //        for(int j = 0; j < len - 1 - i; j++){
        //            if(arr[j] > arr[j+1]){
        //                tmp = arr[j];
        //                arr[j] = arr[j+1];
        //                arr[j+1] = tmp;
        //            }
        //        }
        //    }
        //    Method 2
        int tmp;
        for (int i =0 ;i < len - 1 ;i++)
            for (int j=i+1;j < len;j++)
            {
                if(arr[i]>arr[j])
                {
                    tmp =arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp ;
                }
            }
    }

    /**
     * 归并排序
     * @param nums
     */
    public void mergeSort(int[] nums){
        int len = nums.length;
        int i = 0, j = len - 1;

        int[] temp = new int[len];
        mSort(nums, i, j, temp);
    }

    private void mSort(int[] nums, int l, int r, int[] temp){
        if(l < r){
            int m = (l + r) / 2;
            mSort(nums, l, m, temp);
            mSort(nums, m + 1, r, temp);
            merge(nums, l, m, r, temp);
        }
    }

    private void merge(int[] nums, int l, int m, int r, int[] temp){
        int t = 0;
        int i = l;
        int j = m + 1;
        while(i <= m && j <= r){
            if(nums[i] >= nums[j]){
                temp[t++] = nums[j++];
            }else{
                temp[t++] = nums[i++];
            }
        }

        while(i <= m){
            temp[t++] = nums[i++];
        }

        while(j <= r){
            temp[t++] = nums[j++];
        }

        t = 0;
        while(l <= r){
            nums[l++] = temp[t++];
        }
    }
}
