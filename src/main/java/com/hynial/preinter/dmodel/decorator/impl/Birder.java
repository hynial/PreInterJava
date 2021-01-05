package com.hynial.preinter.dmodel.decorator.impl;

import com.hynial.preinter.dmodel.decorator.ifc.TheGreatestSage;

public class Birder extends Wrapper {
    public Birder(TheGreatestSage theGreatestSage) {
        super(theGreatestSage);
    }

    @Override
    public void move() {
        super.move();
        System.out.println("Birder move.");
    }

    public void fly() {
        System.out.println("Birder fly.");
    }
}
