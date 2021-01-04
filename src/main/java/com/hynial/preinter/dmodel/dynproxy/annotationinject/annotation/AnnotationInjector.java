package com.hynial.preinter.dmodel.dynproxy.annotationinject.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationInjector {
    // 可以搞个缓存 TODO
    public static Object getBean(Object obj) {
        //get Property
        try {
            Field f[] = obj.getClass().getDeclaredFields();
            //遍历属性
            for (Field currentField : f) {
                //获得属性上的注释
                Fluid flu = currentField.getAnnotation(Fluid.class);
                if (null != flu) {
                    System.out.println("注入" + currentField.getName() + "属性" + " 它的值是" + flu.value());
                    obj.getClass().getMethod("set" + currentField.getName().substring(0, 1).toUpperCase() + currentField.getName().substring(1), new Class[]{String.class}).invoke(obj, flu.value());
                }
            }
            //get all method
            Method m[] = obj.getClass().getDeclaredMethods();
            for (Method currentMethod : m) {
                Fluid flm = currentMethod.getAnnotation(Fluid.class);
                if (null != flm) {
                    System.out.println("注入" + currentMethod.getName() + "方法" + " 方法属性" + flm.Property());
                    currentMethod.invoke(obj, flm.Property());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
}
