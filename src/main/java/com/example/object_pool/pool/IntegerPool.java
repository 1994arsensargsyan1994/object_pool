package com.example.object_pool.pool;

import com.example.object_pool.pool.config.ObjectPoolConfig;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IntegerPool extends AbstractObjectPool<Integer> {

    private static final Random RANDOM = new Random();
    private static final Set<Integer> set = new HashSet<>();

    public IntegerPool(final ObjectPoolConfig poolConfig) {
        super(poolConfig);
    }

    @Override
    public Integer createObject() {


        Integer i = RANDOM.nextInt();

        set.add(i);
        return i;
    }

    @Override
    public boolean validate(Integer integer) {
        return set.contains(integer);
    }
}