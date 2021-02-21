package com.example.object_pool.pool;

import com.example.object_pool.pool.config.ObjectPoolConfig;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public abstract class AbstractObjectPool<T> implements ObjectPool<T> {

    private final ObjectPoolConfig poolConfig;

    private final Queue<T> pooledObjects = new LinkedList<>();
    private final Queue<T> inUSeObjects = new LinkedList<>();

    protected AbstractObjectPool(final ObjectPoolConfig poolConfig) {
        if (poolConfig == null) {
            throw new IllegalArgumentException("Null was passed was passed as an argument for parameter 'poolConfig'.");
        }
        this.poolConfig = poolConfig;

        IntStream.range(0, this.poolConfig.minSize()).forEach(value -> this.pooledObjects.add(createObject()));
    }

    @Override
    public synchronized T get() {
        if (!this.pooledObjects.isEmpty()) {
            T element = this.pooledObjects.poll();
            inUSeObjects.add(element);

            return element;
        }
        if (inUSeObjects.size() < this.poolConfig.maxSize()) {
            T element = createObject();
            inUSeObjects.add(element);
            return element;
        }
        try {
            this.wait();
        } catch (final InterruptedException ex) {
            throw new IllegalStateException("Something went wrong during wait process", ex);
        }
        T element = pooledObjects.poll();
        System.out.println(element);
        inUSeObjects.add(element);
        return element;
    }

    @Override
    public synchronized void release(final T object) {

        if (validate(object)) {
            inUSeObjects.remove(object);
            pooledObjects.add(object);
        }
        if (this.pooledObjects.size() == 0) {
            this.notify();
        }
    }

    public abstract T createObject();

    public abstract boolean validate(T o);
}
