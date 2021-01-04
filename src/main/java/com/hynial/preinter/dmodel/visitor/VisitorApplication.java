package com.hynial.preinter.dmodel.visitor;

import com.hynial.preinter.dmodel.visitor.impl.CEOVisitor;
import com.hynial.preinter.dmodel.visitor.impl.CTOVisitor;

/**
 * 访问者模式
 * 封装一些作用于某种数据结构中的各元素的操作， 它可以在不改变数据结构的前提下定义作用于这些元素的新的操作。
 */
public class VisitorApplication {
    public static void main(String[] args) {
        // 构建报表
        BusinessReport report = new BusinessReport();
        System.out.println("=========== CEO看报表 ===========");
        report.showReport(new CEOVisitor());
        System.out.println("=========== CTO看报表 ===========");
        report.showReport(new CTOVisitor());
    }
}
