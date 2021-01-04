package com.hynial.preinter.dmodel.dynproxy.impl;

import com.hynial.preinter.dmodel.dynproxy.ifc.IUserDao;

public class UserDao implements IUserDao {

    @Override
    public int save(String name) {
        System.out.println("Saved:"  + name);
        return 0;
    }
}
