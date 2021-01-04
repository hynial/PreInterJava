package com.hynial.preinter.dmodel.dynproxy.impl;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyByCglibFactory implements MethodInterceptor {
    private Object target;

    public ProxyByCglibFactory(Object target) {
        this.target = target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before:");
        Object resultObject = method.invoke(target, objects);
        if(method.getName().equals("save")){
            System.out.println("save called");
        }
        System.out.println("After:" + (resultObject == null ? "" : resultObject.toString()));

        return resultObject;
    }

    public Object getProxyInstance(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);

        return enhancer.create();
    }
}
