package com.hynial.preinter.dstructalgorithm.algorithm.find;

import java.util.*;

public class LeafNodeSearch {
    /**
     * 对任意一个Map<String, Object>, 其 key 为 String,
     * 其 value 为 Map<String, Object> Object[] Number String 中的任意一种,
     * 显然叶子节点是 value 类型为 Number 或 String的节点,
     * 将 Map 转为多条字符串, 每条字符串表达其中一个叶子节点,
     * 比如:
     * {"a":{"b":["v",2,{"c":0}]},"d":[1,null,3]}
     * 将转化为以下这些字符串
     * a.b[0] = v
     * a.b[1] = 2
     * a.b[2].c = 0
     * d[0] = 1
     * d[1] = null
     * d[2] = 3
     *
     * @param map 上述的 map
     * @return 所有的字符串
     */
    public static Set<String> showMap(Map<String, Object> map) {
        Set<String> result = new TreeSet<>();
        if (map == null) {
            result.add("null");
            return result;
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();

            if (entry.getValue() instanceof Map) {
                Set<String> subResult = showMap((Map<String, Object>) entry.getValue());
                for (String s : subResult) {
                    result.add(key + "." + s);
                }
            } else if (entry.getValue() instanceof Object[]) {
                int i = 0;
                for (Object obj : (Object[]) entry.getValue()) {
                    if (obj instanceof Integer) {
                        result.add(key + "[" + i + "]" + " = " + ((Integer) obj).intValue());
                    } else if (obj instanceof String) {
                        result.add(key + "[" + i + "]" + " = " + obj.toString());
                    } else if (obj instanceof Map) {
                        Set<String> subResult = showMap((Map<String, Object>) obj);
                        for (String s : subResult) {
                            result.add(key + "[" + i + "]." + s);
                        }
                    }else if(obj == null){
                        result.add(key + "[" + i + "] = null");
                    }

                    i++;
                }
            } else {
                result.add(key + " = " + ((entry.getValue() == null) ? "null" : entry.getValue().toString()));
            }

        }

        return result;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> subMap = new HashMap<>();
        Map<String, Object> thirdMap = new HashMap<>();
        thirdMap.put("c", 0);
        Object[] objects = new Object[]{"v", 2, thirdMap};
        subMap.put("b", objects);
        map.put("a", subMap);
        Object[] secondObjects = new Object[]{1, null, 3};
        map.put("d", secondObjects);
        map.put("e", null);

        Set<String> result = showMap(map);

        result.forEach(s -> System.out.println(s));
    }
}
