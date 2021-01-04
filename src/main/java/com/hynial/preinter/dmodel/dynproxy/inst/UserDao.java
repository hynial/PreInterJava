package com.hynial.preinter.dmodel.dynproxy.inst;

import com.hynial.preinter.dmodel.dynproxy.impl.IUserDao;

public class UserDao implements IUserDao {

    @Override
    public int save(String name) {
        System.out.println("Saved:"  + name);
        return 0;
    }
}
