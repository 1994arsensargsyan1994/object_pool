package com.example.object_pool.pool;

import com.example.object_pool.pool.config.ObjectPoolConfig;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public abstract class AbstractObjectPool<T> implements ObjectPool<T> {

    private int poolSize;

    private final ObjectPoolConfig poolConfig;

    private final Queue<T> pooledObjects = new LinkedList<>();
    private final Queue<T> inUSeObjects = new LinkedList<>();

    protected AbstractObjectPool(final ObjectPoolConfig poolConfig) {
        if (poolConfig == null) {
            throw new IllegalArgumentException("Null was passed was passed as an argument for parameter 'poolConfig'.");
        }
        this.poolConfig = poolConfig;

        IntStream.range(0, this.poolConfig.minSize()).forEach(value -> this.pooledObjects.add(createPooledObject()));
    }

    @Override
    public synchronized T get() {
        if (!this.pooledObjects.isEmpty()) {
            return this.pooledObjects.poll();
        }
        if (poolSize < this.poolConfig.maxSize()) {
            return createPooledObject();
        }
        try {
            this.wait();
        } catch (final InterruptedException ex) {
            throw new IllegalStateException("Something went wrong during wait process", ex);
        }
        return pooledObjects.poll();
    }

    @Override
    public synchronized void release(final T object) {



        pooledObjects.add(object);
        if (this.pooledObjects.size() == 1) {
            this.notify();
        }
    }

    private T createPooledObject() {
        final T newObject = createObject();
        this.poolSize = this.poolSize + 1;
        return newObject;
    }

    public abstract T createObject();
}
