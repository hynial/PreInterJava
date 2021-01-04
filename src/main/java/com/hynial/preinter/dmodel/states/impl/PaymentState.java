package com.hynial.preinter.dmodel.states.impl;

import com.hynial.preinter.dmodel.states.VendingMachine;
import com.hynial.preinter.dmodel.states.ifc.State;

import java.util.Random;

public class PaymentState implements State {

    VendingMachine machine;

    public PaymentState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void choose() {
        System.out.println("商品已选购完成请勿重复挑选");
    }

    @Override
    public boolean payment() {
        Random random = new Random();
        int num = random.nextInt(10);
        if (num % 2 == 0) {
            System.out.println("付款成功！");
            machine.setState(machine.getDispenseCommodityState());
            return true;
        }
        System.out.println("付款失败，请重新支付！");
        return false;
    }

    @Override
    public void dispenseCommodity() {
        System.out.println("请先完成支付！");
    }
}