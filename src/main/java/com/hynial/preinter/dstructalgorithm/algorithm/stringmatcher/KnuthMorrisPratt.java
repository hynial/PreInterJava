package com.hynial.preinter.dstructalgorithm.algorithm.stringmatcher;

import java.util.Arrays;

/**
 * KMP 算法
 * KMP算法(Knuth-Morris-Pratt)
 * KMP算法与前面的MP算法一脉相承，都是充分利用先前匹配的过程中已经得到的结果来避免频繁回溯。
 */
public class KnuthMorrisPratt {
    public void preKmp(char[] x, int m, int[] kmpNext) {
        int i, j;
        i = 0;
        j = kmpNext[0] = -1;
        while (i < m - 1) {
            while (j > -1 && x[i] != x[j])
                j = kmpNext[j];
            i++;
            j++;
            if (x[i] == x[j])
                kmpNext[i] = kmpNext[j];
            else
                kmpNext[i] = j;
        }
    }

    public void kmp(String p, String t) {
        int m = p.length();
        int n = t.length();

        if (m > n) {
            System.err.println("Unsuccessful match!");
            return;
        }

        char[] x = p.toCharArray();
        char[] y = t.toCharArray();

        int i = 0;
        int j = 0;
        int[] kmpNext = new int[m + 1];
        preKmp(x, m, kmpNext);

        while (j < n) {
            while (i > -1 && x[i] != y[j])
                i = kmpNext[i];
            i++;
            j++;
            if (i >= m) {
                System.out.println("Matching index found at: " + (j - i + 1));
                i = kmpNext[i];
            }
        }
    }

    // next [j] = k，代表j之前的字符串中有最大长度为k 的相同前缀后缀。
    // k是匹配下标。这里没有从最后一位开始和第一位开始分别比较前缀后缀，而是利用了next[i-1]的结果。
    void getNext(String p, int[] next) {//获取next数组
        char[] ptr = p.toCharArray();
        int i, n = next.length - 1, k;
        k = 0;
        for (i = 1; i < n; i++) {
            while (k > 0 && ptr[k] != ptr[i])
                k = next[k];
            if (ptr[k] == ptr[i]) k++;
            next[i + 1] = k;
            //next表示的是匹配长度
        }
    }

    int kmp2(String t, String p) { //匹配ab两串，a为父串
        int i = 0, j = 0;
        int len1 = t.length();
        int len2 = p.length();
        int[] next = new int[len2 + 1];

        getNext(p, next);
        System.out.println(Arrays.toString(next));
        char[] a = t.toCharArray();
        char[] b = p.toCharArray();
        while (i < len1 && j < len2) {
            if (j == 0 || a[i] == b[j]) {
                i++;
                j++;
            } else j = next[j + 1];//到前一个匹配点
        }
        if (j >= len2)
            return i - j;
        else return -1;
    }

    public static void main(String[] args) {
//        String P = "caatcat";
        String P = "ABCDABD";
        String T = "ctaatcacaatcat123";

        System.out.println(P);
        for (int i = 0; i < T.toCharArray().length; i++) {
            System.out.print(T.charAt(i) + "\t");
        }
        System.out.println();
        for (int i = 0; i < T.toCharArray().length; i++) {
            System.out.print(i + "\t");
        }

        System.out.println();

        KnuthMorrisPratt knuthMOrrisPratt = new KnuthMorrisPratt();
        int index = knuthMOrrisPratt.kmp2(T, P);
        System.out.println(index);
    }
}
