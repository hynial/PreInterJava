package com.hynial.mt;

import com.hynial.tuple.Tuple2;

public class MathApplication {
    public static void main(String[] args) {
//        int a = 11, b = 15, c = 7;
        int a = 29, b = 37, c = 7 * 2;
        Tuple2<Integer, Integer> value = IndeterminateEquation.resolve2(a, b, c);

        System.out.println("GCD:" + MathTool.gcdByEuclidsAlgorithm(a,b));
        System.out.println("GCD:" + MathTool.gcdByEuclidsAlgorithmWithoutRecurse(a,b));
        System.out.println("GCD:" + MathTool.gcdBySteinsAlgorithm(a,b));

        System.out.println("LCM:" + MathTool.lcm(a, b));
        System.out.println("LCM:" + MathTool.lcm2(a, b));
        System.out.println("LCM:" + MathTool.lcm3(a, b));

        System.out.println("%dx + %dy = %d, Value:%s".formatted(a, b, c, value));
        System.out.printf("Common2: %dx + %dy = %d, Value:%s \n", a, b, c, IndeterminateEquation.common2(a, b, c));

        int n1 = 9, p = 23;
        System.out.println("ExtendedEuclidean:%d, %d: %s".formatted(n1, p, MathTool.extendedEuclideanAlgorithm(n1, p)));
        System.out.println("InverseOf[%d]Mod:%d Value:%d".formatted(n1, p, MathTool.inverseOf(n1, p)));
    }
}
