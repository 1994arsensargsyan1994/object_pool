package com.example.object_pool;

import com.example.object_pool.pool.IntegerPool;
import com.example.object_pool.pool.config.ObjectPoolConfig;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class IntegerPoolTest {

    private static final int POOL_MIN_SIZE = 0;
    private static final int POOL_MAX_SIZE = 1;

    @Test
    public void name() throws InterruptedException {
        final IntegerPool integerPool = new IntegerPool(
                ObjectPoolConfig.of(POOL_MIN_SIZE, POOL_MAX_SIZE)
        );
        final AtomicReference<Integer> atomicReference = new AtomicReference<>();

        final Thread t1 = new Thread(() -> {
            System.out.println("t1 starting");
            atomicReference.set(integerPool.get());
            System.out.println("t1 finishing");
        });
        t1.start();

        Thread.sleep(5000);

        final Thread t2 = new Thread(() -> {
            System.out.println("t2 starting");
            final Integer integer = integerPool.get();
            System.out.println("t2 finishing");

        });
        t2.start();

        Thread.sleep(5000);

        System.out.println("releasing object to the pool");
        integerPool.release(atomicReference.get());

        Thread.sleep(5000);

    }
}
