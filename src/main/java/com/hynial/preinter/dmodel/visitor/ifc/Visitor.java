package com.hynial.preinter.dmodel.visitor.ifc;

import com.hynial.preinter.dmodel.visitor.impl.Engineer;
import com.hynial.preinter.dmodel.visitor.impl.Manager;

public interface Visitor {
    // 访问工程师类型
    void visit(Engineer engineer);
    // 访问经理类型
    void visit(Manager manager);
}
