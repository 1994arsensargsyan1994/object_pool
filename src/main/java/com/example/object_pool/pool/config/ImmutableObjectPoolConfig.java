package com.example.object_pool.pool.config;

final class ImmutableObjectPoolConfig implements ObjectPoolConfig {

    private final int minSize;

    private final int maxSize;

    ImmutableObjectPoolConfig(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public int minSize() {
        return minSize;
    }

    @Override
    public int maxSize() {
        return maxSize;
    }
}