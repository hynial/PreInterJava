package com.hynial.preinter.dmodel.adapter;

import com.hynial.preinter.dmodel.adapter.ifc.Target;


/**
 * 适配器模式，即定义一个包装类，用于包装不兼容接口的对象
 * 把一个类的接口变换成客户端所期待的另一种接口，从而使原本接口不匹配而无法一起工作的两个类能够在一起工作。
 */
public class AdapterApplication {
    public static void main(String[] args) {
        Target target = new Adapter();
        target.request();
    }
}
