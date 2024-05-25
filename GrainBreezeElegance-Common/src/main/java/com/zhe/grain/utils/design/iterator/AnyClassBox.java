package com.zhe.grain.utils.design.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 使用迭代器设计模式(Iterator) 适用多种不同的类型遍历需求
 */

public class AnyClassBox<T> implements AnyClassAggregate<T> {
    private List<T> anyClass;
    private Integer last;

    public AnyClassBox(Integer maxSize) {
        this.anyClass = new ArrayList<>(maxSize);
        this.last = 0;
    }

    public void appendData(T data) {
        anyClass.add(data);
        this.last++;
    }

    public void appendAll(List<T> datas) {
        anyClass.addAll(datas);
        this.last += datas.size();
    }

    public Integer getLength() {
        return last;
    }

    public T getData(Integer index) {
        return anyClass.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return new AnyClassBoxIterator<>(this);
    }
}
