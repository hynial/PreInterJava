package com.hynial.preinter.dmodel.decorator.impl;

import com.hynial.preinter.dmodel.decorator.ifc.TheGreatestSage;

public class Monkey implements TheGreatestSage {
    @Override
    public void move() {
        System.out.println("Monkey move.");
    }
}
