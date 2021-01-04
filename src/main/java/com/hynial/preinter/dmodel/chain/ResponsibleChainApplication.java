package com.hynial.preinter.dmodel.chain;

import com.hynial.preinter.dmodel.chain.impl.ConcreteHandler;
import com.hynial.preinter.dmodel.chain.sup.Handler;

/**
 * 责任链模式是一种对象的行为模式。在责任链模式里，很多对象由每一个对象对其下家的引用而连接起来形成一条链。
 * 请求在这个链上传递，直到链上的某一个对象决定处理此请求。
 * 发出这个请求的客户端并不知道链上的哪一个对象最终处理这个请求，这使得系统可以在不影响客户端的情况下动态地重新组织和分配责任。
 */
public class ResponsibleChainApplication {
    public static void main(String[] args) {
        // 组装责任链
        Handler handler1 = new ConcreteHandler();
        Handler handler2 = new ConcreteHandler();
        handler1.setSuccessor(handler2);
        //提交请求
        handler1.handleRequest();
    }
}
