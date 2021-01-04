package com.hynial.preinter.dmodel.dynproxy.inst;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
