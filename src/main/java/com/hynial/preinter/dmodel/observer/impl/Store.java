package com.hynial.preinter.dmodel.observer.impl;

import com.hynial.preinter.dmodel.observer.entity.Product;
import com.hynial.preinter.dmodel.observer.ifc.ProductObservable;
import com.hynial.preinter.dmodel.observer.ifc.ProductObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store implements ProductObservable {
    private List<ProductObserver> observers = new ArrayList<>();
    private Map<String, Product> products = new HashMap<>();

    @Override
    public void addObserver(ProductObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(ProductObserver observer) {
        this.observers.remove(observer);
    }

    public void addNewProduct(String name, double price) {
        Product p = new Product(name, price);
        products.put(p.getName(), p);
        // 通知观察者:
        observers.forEach(o -> o.onPublished(p));
    }

    public void setProductPrice(String name, double price) {
        Product p = products.get(name);
        p.setPrice(price);
        // 通知观察者: // 并发同时处理?
        observers.forEach(o -> o.onPriceChanged(p));
    }
}
