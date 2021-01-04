package com.hynial.preinter.dmodel.dynproxy.annotationinject.impl;

import com.hynial.preinter.dmodel.dynproxy.annotationinject.annotation.Fluid;
import com.hynial.preinter.dmodel.dynproxy.annotationinject.ifc.AnimalInterface;

public class DogImpl implements AnimalInterface {
    @Fluid(value = "Lunar")
    private String name;
    private String Property;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void say() {
        System.out.println("Dog: wang");
    }

    @Override
    public void getProperty() {
        System.out.println("Properties:" + this.name + " - " + this.Property);
    }

    @Fluid(Property = "Fluid Shore")
    public void setProperty(String Property) {
        this.Property = Property;
    }
}
