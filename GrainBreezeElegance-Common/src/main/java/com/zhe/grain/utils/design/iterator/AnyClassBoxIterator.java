package com.zhe.grain.utils.design.iterator;

import java.util.Iterator;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class AnyClassBoxIterator<T> implements Iterator<T> {
    private AnyClassBox<T> anyClassBox;
    private Integer index;

    public AnyClassBoxIterator(AnyClassBox<T> anyClassBox) {
        this.anyClassBox = anyClassBox;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < anyClassBox.getLength();
    }

    @Override
    public T next() {
        T data = anyClassBox.getData(index);
        index++;
        return data;
    }
}
