package com.hynial.preinter.dmodel.decorator.impl;

import com.hynial.preinter.dmodel.decorator.ifc.TheGreatestSage;

public class Fisher extends Wrapper {
    public Fisher(TheGreatestSage theGreatestSage) {
        super(theGreatestSage);
    }

    @Override
    public void move() {
        super.move();
        System.out.println("Fisher move.");
    }
}
