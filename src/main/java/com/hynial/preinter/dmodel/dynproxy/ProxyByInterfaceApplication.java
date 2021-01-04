package com.hynial.preinter.dmodel.dynproxy;

import com.hynial.preinter.dmodel.dynproxy.impl.IUserDao;
import com.hynial.preinter.dmodel.dynproxy.inst.ProxyByInterfaceFactory;
import com.hynial.preinter.dmodel.dynproxy.inst.UserDao;

public class ProxyByInterfaceApplication {
    public static void main(String[] args) {
        // 同一个接口下的代理
        UserDao userDao = new UserDao();
        IUserDao userProxyDao = (IUserDao) new ProxyByInterfaceFactory(userDao).getProxyInstance();

        userProxyDao.save("张三");


    }
}
