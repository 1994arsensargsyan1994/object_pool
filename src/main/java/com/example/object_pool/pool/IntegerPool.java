package com.example.object_pool.pool;

import com.example.object_pool.pool.config.ObjectPoolConfig;

import java.util.Random;

public class IntegerPool extends AbstractObjectPool<Integer> {

    private static final Random RANDOM = new Random();

    public IntegerPool(final ObjectPoolConfig poolConfig) {
        super(poolConfig);
    }

    @Override
    public Integer createObject() {
        return RANDOM.nextInt();
    }
}