package com.zhe.grain.utils.design.iterator;

import java.util.Iterator;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface AnyClassAggregate<T> {

    Iterator<T> iterator();
}
