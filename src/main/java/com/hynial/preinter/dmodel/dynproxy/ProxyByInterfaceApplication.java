package com.hynial.preinter.dmodel.dynproxy;

import com.hynial.preinter.dmodel.dynproxy.ifc.IUserDao;
import com.hynial.preinter.dmodel.dynproxy.impl.ProxyByCglibFactory;
import com.hynial.preinter.dmodel.dynproxy.impl.ProxyByInterfaceFactory;
import com.hynial.preinter.dmodel.dynproxy.impl.UserDao;

/**
 * 动态代理则在运行时借助于 JDK 动态代理、CGLIB 等在内存中”临时”生成 AOP 动态代理类，因此也被称为运行时增强。
 * AspectJ 亦提供 AOP 支持 - AspectJ 采用编译时生成 AOP 代理类，因此具有更好的性能，但需要使用特定的编译器进行处理。
 */
public class ProxyByInterfaceApplication {
    public static void main(String[] args) {
        // 同一个接口下的代理
        UserDao userDao = new UserDao();
        System.out.println(userDao.getClass());
        IUserDao userProxyDao = (IUserDao) new ProxyByInterfaceFactory(userDao).getProxyInstance();
        userProxyDao.save("张三");
        System.out.println(userProxyDao.getClass());

        // Cglib 代理
        IUserDao userCglibProxyDao = (IUserDao) new ProxyByCglibFactory(userDao).getProxyInstance();
        userCglibProxyDao.save("李四");


        System.out.println(userCglibProxyDao.getClass());
    }
}
