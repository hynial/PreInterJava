package com.hynial.preinter.dmodel.states.impl;

import com.hynial.preinter.dmodel.states.VendingMachine;
import com.hynial.preinter.dmodel.states.ifc.State;

public class EmptyState implements State {

    VendingMachine machine;

    public EmptyState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void choose() {
        System.out.println("对不起商品已售罄！");
    }

    @Override
    public boolean payment() {
        System.out.println("对不起商品已售罄！");
        return false;
    }

    @Override
    public void dispenseCommodity() {
        System.out.println("对不起商品已售罄！");
    }
}