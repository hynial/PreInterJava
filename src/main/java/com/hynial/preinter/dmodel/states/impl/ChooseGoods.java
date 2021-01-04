package com.hynial.preinter.dmodel.states.impl;

import com.hynial.preinter.dmodel.states.VendingMachine;
import com.hynial.preinter.dmodel.states.ifc.State;

public class ChooseGoods implements State {

    VendingMachine machine;

    public ChooseGoods(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void choose() {
        if (machine.getCount() > 0) {
            System.out.println("商品挑选成功，请及时付款！");
            machine.setState(machine.getPaymentState());
        } else {
            System.out.println("很遗憾，商品售罄了！");
            machine.setState(machine.getEmptyState());
        }
    }

    @Override
    public boolean payment() {
        System.out.println("请先挑选商品！");
        return false;
    }

    @Override
    public void dispenseCommodity() {
        System.out.println("请先挑选商品！");
    }
}