package com.hynial.preinter.dstructalgorithm.skiplist;

public class SkipNode<T> {
    int key;
    T value;
    SkipNode right, down;//左右上下四个方向的指针

    public SkipNode(int key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Key:%d Value:%s", key, value == null ? "NULL" : value.toString());
    }
}