package com.example.xieqe.test001.HashCodeTest;

import java.util.Map;

/**
 * Created by xqe on 2018/2/22.
 */

public class MapEntry<K,V> implements Map.Entry<K,V> {

    private K key;
    private V value;

    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V v) {
        V result = value;
        value = v;
        return result;
    }

    public int hashCode() {
        // 符号 ^ 表示按位抑或
        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }

    public boolean equals(Object o) {
        if (!(o instanceof MapEntry)) {
            return false;
        }
        MapEntry me = (MapEntry) o;
        return (key == null ? me.getKey() == null : me.getKey().equals(key)) &&
                (value == null ? me.getValue() == null : me.getValue().equals(value));
    }
}
