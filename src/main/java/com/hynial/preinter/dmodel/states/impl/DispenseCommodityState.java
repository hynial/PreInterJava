package com.hynial.preinter.dmodel.states.impl;

import com.hynial.preinter.dmodel.states.VendingMachine;
import com.hynial.preinter.dmodel.states.ifc.State;

public class DispenseCommodityState implements State {
    VendingMachine machine;

    public DispenseCommodityState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void choose() {
        System.out.println("请及时取走您的商品！");
    }

    @Override
    public boolean payment() {
        System.out.println("请及时取走您的商品！");
        return false;
    }

    @Override
    public void dispenseCommodity() {
        System.out.println("请及时取走您的商品！");
        machine.setState(machine.getChooseGoods());
    }
}
