package io.arsen.object.pool;

import io.arsen.object.pool.config.ObjectPoolConfig;
import io.arsen.object.pool.objectfactory.ObjectFactory;
import io.arsen.object.pool.validation.ObjectValidator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

abstract class AbstractObjectPool<T> implements ObjectPool<T> {

    private final ObjectPoolConfig poolConfig;

    private final ObjectFactory<T> objectFactory;

    private final ObjectValidator<T> objectValidator;

    private final Queue<T> inUseObjects = new LinkedList<>();

    private final Queue<T> pooledObjects = new LinkedList<>();

    AbstractObjectPool(final ObjectPoolConfig poolConfig, final ObjectFactory<T> objectFactory, final ObjectValidator<T> objectValidator) {
        if (poolConfig == null) {
            throw new IllegalArgumentException("Null was passed was passed as an argument for parameter 'poolConfig'.");
        }
        if (objectFactory == null) {
            throw new IllegalArgumentException("Null was passed was passed as an argument for parameter 'objectFactory'.");
        }
        if (objectValidator == null) {
            throw new IllegalArgumentException("Null was passed was passed as an argument for parameter 'objectValidator'.");
        }
        this.poolConfig = poolConfig;
        this.objectFactory = objectFactory;
        this.objectValidator = objectValidator;

        this.initializePool();
    }

    @Override
    public synchronized T get() {
        if (CollectionUtils.isNotEmpty(this.pooledObjects)) {
            final T pooledObject = this.pooledObjects.poll();
            this.inUseObjects.add(pooledObject);
            return pooledObject;
        }
        if (this.inUseObjects.size() < this.poolConfig.maxSize()) {
            final T newObject = this.objectFactory.createObject();
            this.inUseObjects.add(newObject);
            return newObject;
        }
        try {
            this.wait();
        } catch (final InterruptedException ex) {
            throw new IllegalStateException("Something went wrong during wait process", ex);
        }
        final T pooledObject = pooledObjects.poll();
        this.inUseObjects.add(pooledObject);
        return pooledObject;
    }

    @Override
    public synchronized void release(final T object) {
        if (this.objectValidator.isNotValid(object, this.inUseObjects)) {
            throw new IllegalArgumentException("Releasing invalid object to the pool.");
        }
        this.pooledObjects.add(object);
        this.inUseObjects.remove(object);

        if (this.pooledObjects.size() == 1) {
            this.notify();
        }
    }

    private void initializePool() {
        IntStream.range(0, this.poolConfig.minSize())
                .forEach(value -> this.pooledObjects.add(this.objectFactory.createObject()));
    }
}