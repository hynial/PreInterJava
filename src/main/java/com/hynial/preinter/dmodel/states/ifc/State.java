package com.hynial.preinter.dmodel.states.ifc;

public interface State {
    // 挑选商品
    void choose();

    // 付款
    boolean payment();

    // 分发商品
    void dispenseCommodity();
}
