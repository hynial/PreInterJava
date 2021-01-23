package com.hynial.preinter.dmodel.prototype;

import java.io.Serializable;

public class Area implements Cloneable, Serializable {
    public Area(String unit) {
        this.unit = unit;
    }

    public Area() {
    }

    // 钞票单位
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    protected Area clone() throws CloneNotSupportedException {
        Area cloneArea;
        cloneArea = (Area) super.clone();
        return cloneArea;
    }
}