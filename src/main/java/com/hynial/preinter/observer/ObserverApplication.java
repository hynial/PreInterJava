package com.hynial.preinter.observer;

import com.hynial.preinter.observer.impl.Admin;
import com.hynial.preinter.observer.impl.Customer;
import com.hynial.preinter.observer.impl.Store;

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
