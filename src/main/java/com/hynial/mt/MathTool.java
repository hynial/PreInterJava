package com.hynial.mt;

import com.hynial.tuple.Tuple3;
import com.hynial.tuple.util.Tuple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MathTool {

    // Greatest Common Divisor 最大公约数 欧几里得 GCD
    public static int gcdByEuclidsAlgorithm(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclidsAlgorithm(n2, n1 % n2);
    }

    public static int gcdByEuclidsAlgorithmWithoutRecurse(int n1, int n2) {
        int t = n1 % n2;
        while(t != 0) {
            n1 = n2;
            n2 = t;
            t = n1 % n2;
        }

        return n2;
    }

    // Stein's Algorithm or Binary GCD Algorithm
    public static int gcdBySteinsAlgorithm(int n1, int n2) {
        if (n1 == 0) {
            return n2;
        }

        if (n2 == 0) {
            return n1;
        }

        int n;
        for (n = 0; ((n1 | n2) & 1) == 0; n++) {
            n1 >>= 1;
            n2 >>= 1;
        }

        while ((n1 & 1) == 0) {
            n1 >>= 1;
        }

        do {
            while ((n2 & 1) == 0) {
                n2 >>= 1;
            }

            if (n1 > n2) {
                int temp = n1;
                n1 = n2;
                n2 = temp;
            }
            n2 = (n2 - n1);
        } while (n2 != 0);
        return n1 << n;
    }

    // java-least-common-multiple
    public static int lcm(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        int absNumber1 = Math.abs(number1);
        int absNumber2 = Math.abs(number2);
        int absHigherNumber = Math.max(absNumber1, absNumber2);
        int absLowerNumber = Math.min(absNumber1, absNumber2);
        int lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    // Using the Euclidean Algorithm : gcd(a, b) * lcm(a, b) = |a * b|.
    public static int lcm2(int number1, int number2) {
        if (number1 == 0 || number2 == 0)
            return 0;
        else {
            int gcd = gcdByEuclidsAlgorithm(number1, number2);
            return Math.abs(number1 * number2) / gcd;
        }
    }

    //every integer greater than one as a product of powers of prime numbers. for any integer N > 1, we have N = (2k1) * (3k2) * (5k3) *…
//    When, |a| = (2p1) * (3p2) * (5p3) * …
//    and |b| = (2q1) * (3q2) * (5q3) * …
//    then, lcm(a, b) = (2max(p1, q1)) * (3max(p2, q2)) * (5max(p3, q3)) …
    public static Map<Integer, Integer> getPrimeFactors(int number) {
        int absNumber = Math.abs(number);

        Map<Integer, Integer> primeFactorsMap = new HashMap<Integer, Integer>();

        for (int factor = 2; factor <= absNumber; factor++) {
            while (absNumber % factor == 0) {
                Integer power = primeFactorsMap.get(factor);
                if (power == null) {
                    power = 0;
                }
                primeFactorsMap.put(factor, power + 1);
                absNumber /= factor;
            }
        }

        return primeFactorsMap;
    }

    public static int lcm3(int number1, int number2) {
        if(number1 == 0 || number2 == 0) {
            return 0;
        }

        Map<Integer, Integer> primeFactorsForNum1 = getPrimeFactors(number1);
        Map<Integer, Integer> primeFactorsForNum2 = getPrimeFactors(number2);

        Set<Integer> primeFactorsUnionSet = new HashSet<>(primeFactorsForNum1.keySet());
        primeFactorsUnionSet.addAll(primeFactorsForNum2.keySet());

        int lcm = 1;
        for (Integer primeFactor : primeFactorsUnionSet) {
            lcm *= Math.pow(primeFactor,
                    Math.max(primeFactorsForNum1.getOrDefault(primeFactor, 0),
                            primeFactorsForNum2.getOrDefault(primeFactor, 0)));
        }

        return lcm;
    }

    /*
    * Returns a three-tuple (gcd, x, y) such that
    a * x + b * y == gcd, where gcd is the greatest
    common divisor of a and b.

    This function implements the extended Euclidean
    algorithm and runs in O(log b) in the worst case.
    * */
    public static Tuple3<Integer, Integer, Integer> extendedEuclideanAlgorithm(int a, int b) {
        int s = 0, old_s = 1;
        int t = 1, old_t = 0;
        int r = b, old_r = a;

        while (r != 0) {
            int quotient = old_r / r;
            int temp = old_r;
            old_r = r;
            r = temp - quotient * r;

            temp = old_s;
            old_s = s;
            s = temp - quotient * s;

            temp = old_t;
            old_t = t;
            t = temp - quotient * t;
        }

        return Tuple.tuple(old_r, old_s, old_t);
    }

    /**
     * 求解模逆元
     * Returns the multiplicative inverse of
     *     n modulo p.
     *
     *     This function returns an integer m such that
     *     (n * m) % p == 1.
     * @param n
     * @param p
     * @return
     */
    public static int inverseOf(int n, int p) {
        Tuple3<Integer, Integer, Integer> gcd3 = extendedEuclideanAlgorithm(n, p);
        Integer gcd = gcd3.a1;
        Integer x = gcd3.a2;
        Integer y = gcd3.a3;
        assert (n * x + p * y) % p == gcd;

        if (gcd != 1) {
            // Either n is 0, or p is not a prime number.
            throw new RuntimeException("%d has no multiplicative inverse modulo %d".formatted(n, p));
        } else {
            return x % p;
        }
    }
}
