package com.hynial.preinter.dstructalgorithm.algorithm.find;

public class MatrixSearch {

    /**
     * <b>注意! 本题不要遍历二维数组. 要求时间复杂度严格低于n^2, 否则视为不得分 </b>
     * <p>
     * 现有一个n*n的二维正整数数组nums，每行元素保证递增，每列元素保证递增，求某正整数x是否存在于该二维数组中，需要尽量优化时间和空间复杂度；
     *
     * @param nums
     * @param  x 目标数
     * @return boolean
     */
    public static boolean searchMatrix(int[][] nums, int x) {
        int size = nums.length;
        if (x < nums[0][0] || x > nums[size - 1][size - 1]) {
            return false;
        }

        int i = 0, j = nums.length - 1;
        int middle = (i + j) >> 1;
        int mid = nums[middle][middle];

        while (i < j) {
            if (x > mid) {
                i = middle + 1;
            } else if (x < mid) {
                j = middle - 1;
            } else {
                return true;
            }

            middle = (i + j) >> 1;
            mid = nums[middle][middle];
        }

        int r = i;
        if (x > nums[r][r]) {
            for(int m = r + 1; m < size; m++){
                if(x == nums[r][m] || x == nums[m][r]) return true;
            }
        } else {
            for(int m = 0; m < r; m++){
                if(x == nums[r][m] || x == nums[m][r]) return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[][] nums = {{1, 5, 10, 15},
                {2, 6, 11, 16},
                {4, 8, 12, 18},
                {6, 10, 14, 20}};

        boolean r = searchMatrix(nums, 14);

        System.out.println(r);

    }
}
