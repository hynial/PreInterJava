package com.hynial.preinter.observer.ifc;

import com.hynial.preinter.observer.entity.Product;

public interface ProductObserver {

    void onPublished(Product product);

    void onPriceChanged(Product product);
}

