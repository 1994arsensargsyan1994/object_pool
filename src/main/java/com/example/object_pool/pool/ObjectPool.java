package com.example.object_pool.pool;

public interface ObjectPool<T> {

    T get() throws InterruptedException;

    void release(T object);
}