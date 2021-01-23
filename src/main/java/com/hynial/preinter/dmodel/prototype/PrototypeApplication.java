package com.hynial.preinter.dmodel.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * 序列化实现深拷贝
 * 原型模式之一
 */
public class PrototypeApplication {
    public static void main(String[] args) {
        Area area = new Area();
        area.setUnit("RMB");

        // 原型实例，100RMB的钞票
        Money money = new Money(100, area);

        List<Area> areas = new ArrayList<>();
        areas.add(new Area("$"));
        money.setAreaList(areas);

        try {
            Money cloneMoney = money.clone();
            cloneMoney.setFaceValue(200);
            area.setUnit("美元");
            areas.get(0).setUnit("*");

            System.out.println("原型实例的面值：" + money.getFaceValue() + money.getArea().getUnit() + money.getAreaList().get(0).getUnit());
            System.out.println("拷贝实例的面值：" + cloneMoney.getFaceValue() + cloneMoney.getArea().getUnit() + cloneMoney.getAreaList().get(0).getUnit());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
