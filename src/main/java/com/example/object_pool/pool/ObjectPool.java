package com.example.object_pool.pool;


import com.example.object_pool.pool.objectfactory.ObjectFactory;
import com.example.object_pool.pool.validation.Validatable;

public interface ObjectPool<T> extends ObjectFactory<T>, Validatable<T> {

    T get() throws InterruptedException;

    void release(T object);

}