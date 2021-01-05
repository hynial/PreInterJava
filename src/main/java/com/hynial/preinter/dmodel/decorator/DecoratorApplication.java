package com.hynial.preinter.dmodel.decorator;

import com.hynial.preinter.dmodel.decorator.ifc.TheGreatestSage;
import com.hynial.preinter.dmodel.decorator.impl.Birder;
import com.hynial.preinter.dmodel.decorator.impl.Fisher;
import com.hynial.preinter.dmodel.decorator.impl.Monkey;

/**
 * 装饰模式与继承关系的目的都是要拓展对象的功能，但是装饰模式可以提供比继承更多的灵活性。
 * 装饰模式允许系统动态决定“贴上”一个需要的“装饰”，或者“除掉”一个不需要的“装饰”。继承关系则不同，继承关系是静态的，它在系统运行前就决定了。
 * 通过不同的具体装饰类以及这些装饰类的排列组合，设计师可以创造出更多不同行为的组合。
 */
public class DecoratorApplication {
    public static void main(String[] args) {
        TheGreatestSage beforeWrapper = new Monkey();
        // Decorate 1
        System.out.println("Add fish skill");
        TheGreatestSage fisher = new Fisher(beforeWrapper);
        fisher.move();

        // Decorator 2
        System.out.println("Add fish bird skill");
        TheGreatestSage birder = new Birder(new Fisher(beforeWrapper));
        birder.move(); // 对客户端全透明

        // 对客户端半透明
        ((Birder) birder).fly();
    }
}
