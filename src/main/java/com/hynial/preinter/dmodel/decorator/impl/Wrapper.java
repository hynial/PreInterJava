package com.hynial.preinter.dmodel.decorator.impl;

import com.hynial.preinter.dmodel.decorator.ifc.TheGreatestSage;

public class Wrapper implements TheGreatestSage {
    TheGreatestSage theGreatestSage;

    public Wrapper(TheGreatestSage theGreatestSage) {
        this.theGreatestSage = theGreatestSage;
    }

    @Override
    public void move() {
        this.theGreatestSage.move();
    }
}
