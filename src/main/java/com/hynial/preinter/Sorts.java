package com.hynial.preinter;

public class Sorts {

    /**
     * 快排
     *
     * @param arr
     * @param l
     * @param r
     */
    public void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = adjustsArr(arr, l, r);
            quickSort(arr, l, m - 1);
            quickSort(arr, m + 1, r);
        }
    }

    private int adjustsArr(int[] arr, int l, int r) {
        int i = l, j = r;
        int temp = arr[i];

        while (i < j) {
            while (i < j && arr[j] > temp) {
                j--;
            }

            if (i < j) {
                arr[i++] = arr[j];
            }

            while (i < j && arr[i] < temp) {
                i++;
            }

            if (i < j) {
                arr[j--] = arr[i];
            }
        }

        arr[i] = temp;

        return i;
    }

    public void quickSort2(int[] arr, int l, int r) {
        if ( l < r) {
            int i = l + 1, k = i;
            for (; i <= r && arr[i] <= arr[l]; i++) {
                k++;
            }

            for (i++; i <= r; i++) {
                if (arr[i] < arr[l]) {
                    int t = arr[i];
                    arr[i] = arr[k];
                    arr[k++] = t;
                }
            }

            int t = arr[l];
            arr[l] = arr[k - 1];
            arr[k - 1] = t;

            quickSort2(arr, l, k - 2);
            quickSort2(arr, k, r);
        }
    }

    /**
     * 简单选择排序
     *
     * @param arr
     * @param len
     */
    public void chooseSort(int[] arr, int len) {
        for (int i = 0; i < len - 1; i++) {
            int minIndex = i, tmp = arr[i];
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
    }

    /**
     * 改进的简单选择排序
     *
     * @param arr
     * @param len
     */
    public void chooseSortUpgrade(int[] arr, int len) {
        int half = len / 2;
        for (int i = 0; i < half; i++) {
            int arrIndex = i, tmp;
            int arrIndex1 = len - 1 - i, tmp1;
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
            for (int j = i; j < len - i; j++) {
                if (arr[arrIndex] > arr[j]) {
                    arrIndex = j;
                }
                if (arr[arrIndex1] < arr[j]) {
                    arrIndex1 = j;
                }
            }
            if (i != arrIndex) {
                tmp = arr[i];
                arr[i] = arr[arrIndex];
                arr[arrIndex] = tmp;
            }
            if (arrIndex1 == i) {
                arrIndex1 = arrIndex;
            }
            if (i != len - 1 - i && len - 1 - i != arrIndex1) {
                tmp1 = arr[len - 1 - i];
                arr[len - 1 - i] = arr[arrIndex1];
                arr[arrIndex1] = tmp1;
            }
        }
    }

    /**
     * 冒泡排序
     *
     * @param arr
     * @param len
     */
    public void bubbleSort(int arr[], int len) {
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
        for (int i = 0; i < len - 1; i++)
            for (int j = i + 1; j < len; j++) {
                if (arr[i] > arr[j]) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
    }

    /**
     * 归并排序
     * https://www.cnblogs.com/chengxiao/p/6194356.html
     *
     * @param nums
     */
    public void mergeSort(int[] nums) {
        int len = nums.length;
        int i = 0, j = len - 1;

        int[] temp = new int[len];
        mSort(nums, i, j, temp);
    }

    private void mSort(int[] nums, int l, int r, int[] temp) {
        if (l < r) {
            int m = (l + r) / 2;
            mSort(nums, l, m, temp);
            mSort(nums, m + 1, r, temp);
            merge(nums, l, m, r, temp);
        }
    }

    private void merge(int[] nums, int l, int m, int r, int[] temp) {
        int t = 0;
        int i = l;
        int j = m + 1;
        while (i <= m && j <= r) {
            if (nums[i] >= nums[j]) {
                temp[t++] = nums[j++];
            } else {
                temp[t++] = nums[i++];
            }
        }

        while (i <= m) {
            temp[t++] = nums[i++];
        }

        while (j <= r) {
            temp[t++] = nums[j++];
        }

        t = 0;
        while (l <= r) {
            nums[l++] = temp[t++];
        }
    }

    /**
     * 堆排序是一种选择排序，整体主要由构建初始堆+交换堆顶元素和末尾元素并重建堆两部分组成。
     * 其中构建初始堆经推导复杂度为O(n)，在交换并重建堆的过程中，需交换n-1次，
     * 而重建堆的过程中，根据完全二叉树的性质，[log2(n-1),log2(n-2)...1]逐步递减，近似为nlogn
     */
    public static class HeapSort {
        public static void sort(int[] arr) {
            //1.构建大顶堆
            for (int i = arr.length / 2 - 1; i >= 0; i--) {
                //从第一个非叶子结点从下至上，从右至左调整结构
                adjustHeap(arr, i, arr.length);
            }
            //2.调整堆结构+交换堆顶元素与末尾元素
            for (int j = arr.length - 1; j > 0; j--) {
                swap(arr, 0, j);//将堆顶元素与末尾元素进行交换
                adjustHeap(arr, 0, j);//重新对堆进行调整
            }
        }

        /**
         * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
         *
         * @param arr
         * @param i
         * @param length
         */
        public static void adjustHeap(int[] arr, int i, int length) {
            int temp = arr[i];//先取出当前元素i
            for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {//从i结点的左子结点开始，也就是2i+1处开始
                if (k + 1 < length && arr[k] < arr[k + 1]) {//如果左子结点小于右子结点，k指向右子结点
                    k++;
                }
                if (arr[k] > temp) {//如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                    arr[i] = arr[k];
                    i = k;
                } else {
                    break;
                }
            }

            arr[i] = temp;//将temp值放到最终的位置
        }

        /**
         * 交换元素
         *
         * @param arr
         * @param a
         * @param b
         */
        public static void swap(int[] arr, int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }

    /**
     * 希尔排序
     * 希尔排序(Shell's Sort)是插入排序的一种又称“缩小增量排序”（Diminishing Increment Sort），是直接插入排序算法的一种更高效的改进版本。希尔排序是非稳定排序算法。该方法因D.L.Shell于1959年提出而得名。
     * https://www.cnblogs.com/chengxiao/p/6104371.html
     * 其最坏时间复杂度依然为O(n2)，一些经过优化的增量序列如Hibbard经过复杂证明可使得最坏时间复杂度为O(n3/2)
     * @param arr
     */
    public void shellSort(int[] arr) {
        int d = arr.length;
        while (true) {
            d = d / 2;
            for (int x = 0; x < d; x++) {
                for (int i = x + d; i < arr.length; i = i + d) {
                    int temp = arr[i];
                    int j;
                    for (j = i - d; j >= 0 && arr[j] > temp; j = j - d) {
                        arr[j + d] = arr[j];
                    }
                    arr[j + d] = temp;
                }
            }
            if (d == 1) {
                break;
            }
        }
    }
}
