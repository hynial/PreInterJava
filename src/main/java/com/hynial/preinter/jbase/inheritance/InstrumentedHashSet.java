package com.hynial.preinter.jbase.inheritance;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount;

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount(){
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedHashSet<String> instrumentedHashSet = new InstrumentedHashSet<>();
        instrumentedHashSet.addAll(Arrays.asList("a", "b", "c"));

        System.out.println(instrumentedHashSet.getAddCount());
    }
}
