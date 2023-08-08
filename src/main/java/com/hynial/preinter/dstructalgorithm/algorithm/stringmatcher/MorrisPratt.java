package com.hynial.preinter.dstructalgorithm.algorithm.stringmatcher;

/**
 * MP算法
 * https://www.yijiyong.com/algorithm/strmatch/03-mp.html
 * MP 算法（Morris-Pratt算法）是一种快速串匹配算法，它是詹姆斯·莫里斯（James Morris）和沃恩·普莱特（Vaughan Pratt）在1970年提出的一种快速匹配算法
 */
public class MorrisPratt {

    /**
     * MP算法的失效函数
     *
     * @param x
     * @param m
     * @param mpNext 发生实配时，进行下一轮比较过程中模式P的起始比较地址
     */
    void preMp(char x[], int m, int mpNext[]) {
        int i, j;
        i = 0;
        j = mpNext[0] = -1;
        while (i < m) {
            while (j > -1 && x[i] != x[j])
                j = mpNext[j];
            mpNext[++i] = ++j;
        }
    }

    /**
     * MP算法
     *
     * @param p 模式串
     * @param t 目标串
     */
    void mp(String p, String t) {
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
        int[] mpNext = new int[m + 1];
        preMp(x, m, mpNext);

        while (j < n) {
            while (i > -1 && x[i] != y[j])
                i = mpNext[i];
            i++;
            j++;
            if (i >= m) {
                System.out.println("Matching index found at: " + (j - i + 1));
                i = mpNext[i];
            }
        }
    }

    public static void main(String[] args) {
        String P = "caatcat";
        String T = "ctaatcacaatcat123";

        for (int i = 0; i < T.toCharArray().length; i++) {
            System.out.print(T.charAt(i) + "\t");
        }
        System.out.println();
        for (int i = 0; i < T.toCharArray().length; i++) {
            System.out.print(i + "\t");
        }

        System.out.println();
        MorrisPratt morrisPratt = new MorrisPratt();
        morrisPratt.mp(P, T);
    }
}
