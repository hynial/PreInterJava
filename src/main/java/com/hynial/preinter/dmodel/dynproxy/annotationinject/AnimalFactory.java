package com.hynial.preinter.dmodel.dynproxy.annotationinject;

import com.hynial.preinter.dmodel.dynproxy.annotationinject.annotation.AnnotationInjector;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AnimalFactory {
    private static Object getAnimalBase(Object obj) {
        //get proxy object
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if("say".equals(method.getName())) {
                    System.out.println("Before:");
                }
                Object result = method.invoke(AnnotationInjector.getBean(obj), args);
                if("say".equals(method.getName())) {
                    System.out.println("After:");
                }
                return result;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAnimal(Object obj) {
        return (T) getAnimalBase(obj);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAnimal(String className) {
        Object obj = null;
        try {
            obj = getAnimalBase(Class.forName(className).newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAnimal(Class clz) {
        Object obj = null;
        try {
            obj = getAnimalBase(clz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }
}
