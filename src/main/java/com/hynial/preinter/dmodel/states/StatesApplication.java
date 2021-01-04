package com.hynial.preinter.dmodel.states;

/**
 * 当一个需求有很多状态，并且状态之间会进行转换，不同状态还对应不同的行为时就可以考虑使用“状态模式”。
 * 状态模式将每个状态所对应的行为封装到一个类中，大大提高了代码的可读性。并且通过这样的设计还可以消除多余的if-else语句，方便代码的维护。每个具体状态类指明如何过渡到下一个状态
 * 最显著的问题是，每个状态都要对应一个类，当状态过多时会产生大量的类，从而加大维护成本。
 */
public class StatesApplication {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine(2);
        for (int i = 1; i < 4; i++) {
            System.out.println("第" + i + "次购买。");
            machine.purchase();
        }
    }
}
