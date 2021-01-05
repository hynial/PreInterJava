package com.hynial.preinter.dmodel.adapter;

import com.hynial.preinter.dmodel.adapter.ifc.Target;

public class Adapter extends Adaptee implements Target {

    @Override
    public void request() {
        this.specificRequest();
    }
}
