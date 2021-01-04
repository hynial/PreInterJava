package com.hynial.preinter.observer.ifc;

public interface ProductObservable {
    void addObserver(ProductObserver observer);
    void removeObserver(ProductObserver observer);
}
