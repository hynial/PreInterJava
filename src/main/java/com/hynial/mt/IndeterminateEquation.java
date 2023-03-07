package com.hynial.mt;

import com.hynial.tuple.Tuple2;
import com.hynial.tuple.util.Tuple;

public class IndeterminateEquation {

    // 贝祖定理/裴蜀定理
    // 若a,b是整数,且gcd(a,b)=d，那么对于任意的整数x,y,ax+by都一定是d的倍数，特别地，一定存在整数x,y，使ax+by=d成立。
    // 它的一个重要推论是：a,b互质的充分必要条件是存在整数x,y使ax+by=1. 这里用于求解模逆确实好用。

    // only one indeed  https://baike.baidu.com/item/%E8%A3%B4%E8%9C%80%E5%AE%9A%E7%90%86/5186593?fromtitle=%E8%B4%9D%E7%A5%96%E5%AE%9A%E7%90%86&fromid=5185441
    public static Tuple2<Integer, Integer> resolve2(Integer a, Integer b, Integer c) {
        if (b == 0 || a == 0) {
            throw new RuntimeException("ZeroFactorNotAllowed: %d, %d".formatted(a, b));
        }

        Integer max = Integer.MAX_VALUE;
        int x;
        for (int i = 0; i < max; i++) {
            x = i;
            if( (c - a * x) % b == 0 ) {
                return Tuple.tuple(x, (c - a * x) / b);
            }
        }

        return null;
    }

    public static Tuple2<String, String> common2(Integer a, Integer b, Integer c) {
        Tuple2<Integer, Integer> specialValue = resolve2(a, b, c);
        if (specialValue == null) return null;

        String x = specialValue.a1 + " + " + b + "t";
        String y = specialValue.a2 + " - " + a + "t";
        return Tuple.tuple(x, y);
    }
}
