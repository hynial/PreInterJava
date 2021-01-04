package com.hynial.preinter.dmodel.visitor.impl;

import com.hynial.preinter.dmodel.visitor.ifc.Visitor;
import com.hynial.preinter.dmodel.visitor.sup.Staff;

import java.util.Random;

public class Engineer extends Staff {
    public Engineer(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    // 工程师一年的代码数量
    public int getCodeLines() {
        return new Random().nextInt(10 * 10000);
    }
}
