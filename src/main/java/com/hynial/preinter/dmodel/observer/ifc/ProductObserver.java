package com.hynial.preinter.dmodel.observer.ifc;

import com.hynial.preinter.dmodel.observer.entity.Product;

public interface ProductObserver {

    void onPublished(Product product);

    void onPriceChanged(Product product);
}

