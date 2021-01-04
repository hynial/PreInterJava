package com.hynial.preinter.dmodel.observer;

import com.hynial.preinter.dmodel.observer.impl.Admin;
import com.hynial.preinter.dmodel.observer.impl.Customer;
import com.hynial.preinter.dmodel.observer.impl.Store;

public class ObserverApplication {
    public static void main(String[] args) {
        Customer customer = new Customer();
        Admin admin = new Admin();

        Store store = new Store();
        // 注册观察者:
        store.addObserver(customer);
        store.addObserver(admin);

        store.addNewProduct("bangbangtang", 1);

        store.setProductPrice("bangbangtang", 2);
    }
}
