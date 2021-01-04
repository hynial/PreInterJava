package com.hynial.preinter.dmodel.dynproxy.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Java标准库提供了一种动态代理（Dynamic Proxy）的机制：可以在运行期动态创建某个interface的实例。
 */
public class ProxyByInterfaceFactory {
    private Object target;

    public ProxyByInterfaceFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before...");
                Object result = method.invoke(target, args);
                System.out.println("after...");
                System.out.println(result == null ? "result:null" : "result:" + result.toString());
                return result;
            }
        });
    }
}
