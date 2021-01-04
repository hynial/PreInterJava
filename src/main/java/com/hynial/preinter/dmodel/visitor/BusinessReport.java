package com.hynial.preinter.dmodel.visitor;

import com.hynial.preinter.dmodel.visitor.ifc.Visitor;
import com.hynial.preinter.dmodel.visitor.impl.Engineer;
import com.hynial.preinter.dmodel.visitor.impl.Manager;
import com.hynial.preinter.dmodel.visitor.sup.Staff;

import java.util.LinkedList;
import java.util.List;

public class BusinessReport {
    private List<Staff> mStaffs = new LinkedList<>();
    public BusinessReport() {
        mStaffs.add(new Manager("经理-A"));
        mStaffs.add(new Engineer("工程师-A"));
        mStaffs.add(new Engineer("工程师-B"));
        mStaffs.add(new Manager("经理-B"));
        mStaffs.add(new Engineer("工程师-C"));
    }
    /**
     * 为访问者展示报表
     * @param visitor 公司高层，如 CEO、CTO
     */
    public void showReport(Visitor visitor) {
        for (Staff staff : mStaffs) {
            staff.accept(visitor);
        }
    }
}
