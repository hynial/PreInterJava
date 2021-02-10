package com.hynial.preinter.dstructalgorithm.algorithm;

public class ReverseInt {
    public int reverse(int x) { // overflow !!!

        int base = 1;
        int bitCount = 0;
        while (x / base != 0) {
            bitCount++;
            base *= 10;
        }
        base = 1;
        int[] r = new int[bitCount];
        int i = 0;
        while (x / base != 0) {
            base *= 10;
            int tmp = x % base;
            if (base == 10) {
                r[i++] = tmp;
            } else {
                r[i++] = tmp / (base / 10);
            }
        }

        int result = 0;
        base = 1;
        for (int j = bitCount - 1; j > -1; j--) {
            result += r[j] * base;
            base *= 10;
        }

        return result * 1;
    }

    public int reverse2(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }

    public static void main(String[] args) {
        ReverseInt reverseInt = new ReverseInt();
        System.out.println(reverseInt.reverse(-2147483648));
    }
}
