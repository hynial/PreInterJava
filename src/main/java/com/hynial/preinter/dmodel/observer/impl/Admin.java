package com.hynial.preinter.dmodel.observer.impl;

import com.hynial.preinter.dmodel.observer.entity.Product;
import com.hynial.preinter.dmodel.observer.ifc.ProductObserver;

public class Admin implements ProductObserver {
    @Override
    public void onPublished(Product product) {
        System.out.println("Admin receive publish:" + product.getName());
    }

    @Override
    public void onPriceChanged(Product product) {
        System.out.println("Admin receive price change:" + product.getName() + ", price:" + product.getPrice());
    }
}
