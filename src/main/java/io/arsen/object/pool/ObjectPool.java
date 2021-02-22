package io.arsen.object.pool;

public interface ObjectPool<T> {

    T get();

    void release(T object);
}