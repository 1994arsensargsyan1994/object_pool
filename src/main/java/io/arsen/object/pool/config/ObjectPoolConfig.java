package io.arsen.object.pool.config;

public interface ObjectPoolConfig {

    int minSize();

    int maxSize();

    static ObjectPoolConfig of(final int minSize, final int maxSize) {
        if (minSize < 0) {
            throw new IllegalArgumentException("Negative or zero passed as an argument for parameter 'minSize'.");
        }
        if (maxSize < 1) {
            throw new IllegalArgumentException("Negative or zero passed as an argument for parameter 'maxSize'.");
        }
        if (minSize > maxSize) {
            throw new IllegalArgumentException("Min size cannot be greater than max size.");
        }
        return new ImmutableObjectPoolConfig(minSize, maxSize);
    }
}