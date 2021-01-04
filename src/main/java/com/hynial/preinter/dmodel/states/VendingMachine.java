package com.hynial.preinter.dmodel.states;

import com.hynial.preinter.dmodel.states.ifc.State;
import com.hynial.preinter.dmodel.states.impl.ChooseGoods;
import com.hynial.preinter.dmodel.states.impl.DispenseCommodityState;
import com.hynial.preinter.dmodel.states.impl.EmptyState;
import com.hynial.preinter.dmodel.states.impl.PaymentState;

public class VendingMachine {
    // 表示当前状态
    private State state = null;
    // 商品数量
    private int count = 0;
    private State chooseGoods = new ChooseGoods(this);
    private State paymentState = new PaymentState(this);
    private State dispenseCommodityState = new DispenseCommodityState(this);
    private State emptyState = new EmptyState(this);

    public VendingMachine(int count) {
        this.count = count;
        this.state = this.getChooseGoods();
    }

    // 购买商品
    public void purchase() {
        // 挑选商品
        state.choose();
        // 支付成功
        if (state.payment()) {
            // 分发商品
            state.dispenseCommodity();
        }
    }

    // 获取商品后将商品减一
    public int getCount() {
        return count--;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public State getChooseGoods() {
        return chooseGoods;
    }

    public void setChooseGoods(State chooseGoods) {
        this.chooseGoods = chooseGoods;
    }

    public State getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(State paymentState) {
        this.paymentState = paymentState;
    }

    public State getDispenseCommodityState() {
        return dispenseCommodityState;
    }

    public void setDispenseCommodityState(State dispenseCommodityState) {
        this.dispenseCommodityState = dispenseCommodityState;
    }

    public State getEmptyState() {
        return emptyState;
    }

    public void setEmptyState(State emptyState) {
        this.emptyState = emptyState;
    }
}
