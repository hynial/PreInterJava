package com.hynial.preinter.dmodel.visitor.sup;

import com.hynial.preinter.dmodel.visitor.ifc.Visitor;

import java.util.Random;

public abstract class Staff {
    public String name;
    // 员工KPI
    public int kpi;

    public Staff(String name) {
        this.name = name;
        kpi = new Random().nextInt(10);
    }

    // 核心方法，接受Visitor的访问
    public abstract void accept(Visitor visitor);
}
