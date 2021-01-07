package com.hynial.preinter.dmodel.prototype;

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

        try {
            Money cloneMoney = money.clone();
            cloneMoney.setFaceValue(200);
            area.setUnit("美元");

            System.out.println("原型实例的面值：" + money.getFaceValue() + money.getArea().getUnit());
            System.out.println("拷贝实例的面值：" + cloneMoney.getFaceValue() + cloneMoney.getArea().getUnit());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
