package com.hynial.preinter.dstructalgorithm.algorithm.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * Least Recently Used
 * 缓存淘汰策略 - 最近最少使用
 * Leet-code 146 LRU 缓存机制
 */
public class LRUCache {
    private static class DataLinkNode{
        int key;
        int value;
        DataLinkNode prev;
        DataLinkNode post;

        public DataLinkNode(){

        }

        public DataLinkNode(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.head = new DataLinkNode();
        this.tail = new DataLinkNode();

        this.head.post = this.tail;
        this.tail.prev = this.head;
    }

    private Map<Integer, DataLinkNode> map = new HashMap<>();
    private int size = 0;
    private int capacity = 3;

    private DataLinkNode head, tail;

    public void put(int key, int value){
        if(map.containsKey(key)){
            DataLinkNode dataLinkNode = map.get(key);
            dataLinkNode.value = value;
            moveToHead(dataLinkNode);
        }else{
            DataLinkNode dataLinkNode = new DataLinkNode(key, value);
            map.put(key, dataLinkNode);
            addToHead(dataLinkNode);
            this.size += 1;
            if(this.size > this.capacity){
                DataLinkNode dataLinkNode1 = removeTail();
                map.remove(dataLinkNode1.key);
                this.size--;
            }

        }
    }

    public int get(int key) {
        DataLinkNode node = map.get(key);
        if (node == null) {
            return -1;
        }

        moveToHead(node);
        return node.value;
    }

    private void moveToHead(DataLinkNode dataLinkNode){
        // 先移除节点
        dataLinkNode.prev.post = dataLinkNode.post;
        dataLinkNode.post.prev = dataLinkNode.prev;
        // 再添加到头部
        dataLinkNode.post = this.head.post;
        dataLinkNode.prev = this.head;
        this.head.post.prev = dataLinkNode;
        this.head.post = dataLinkNode;
    }

    private void removeNode(DataLinkNode dataLinkNode){
        dataLinkNode.prev.post = dataLinkNode.post;
        dataLinkNode.post.prev = dataLinkNode.prev;
    }

    private void addToHead(DataLinkNode dataLinkNode){
        dataLinkNode.prev = this.head;
        dataLinkNode.post = this.head.post;
        this.head.post.prev = dataLinkNode;
        this.head.post = dataLinkNode;
    }

    private DataLinkNode removeTail(){
        DataLinkNode dataLinkNode = this.tail.prev;
        this.removeNode(this.tail.prev);

        return dataLinkNode;
    }

    public static class Main{
        public static void main(String[] args) {
            LRUCache lRUCache = new LRUCache(2);
            lRUCache.put(1, 1); // 缓存是 {1=1}
            lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
            System.out.println(lRUCache.get(1));    // 返回 1
            lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
            System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
            lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
            System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
            System.out.println(lRUCache.get(3));    // 返回 3
            System.out.println(lRUCache.get(4));    // 返回 4
        }
    }
}
