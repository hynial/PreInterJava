package com.hynial.preinter.dmodel.observer.ifc;

public interface ProductObservable {
    void addObserver(ProductObserver observer);
    void removeObserver(ProductObserver observer);
}
