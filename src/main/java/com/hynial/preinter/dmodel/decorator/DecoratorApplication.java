package com.hynial.preinter.dmodel.decorator;

import com.hynial.preinter.dmodel.decorator.ifc.TheGreatestSage;
import com.hynial.preinter.dmodel.decorator.impl.Birder;
import com.hynial.preinter.dmodel.decorator.impl.Fisher;
import com.hynial.preinter.dmodel.decorator.impl.Monkey;

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
        birder.move();
        ((Birder) birder).fly();
    }
}
