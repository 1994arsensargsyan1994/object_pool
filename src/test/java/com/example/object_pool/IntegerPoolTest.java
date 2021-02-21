package com.example.object_pool;

import com.example.object_pool.pool.IntegerPool;
import com.example.object_pool.pool.config.ObjectPoolConfig;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class IntegerPoolTest {

    private static final int POOL_MIN_SIZE = 0;
    private static final int POOL_MAX_SIZE = 1;

    @Test
    public void testMultiThreading() throws InterruptedException {
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

    @Test
    public  void  test(){

        final IntegerPool integerPool = new IntegerPool(
                ObjectPoolConfig.of(POOL_MIN_SIZE, POOL_MAX_SIZE)
        );

        Integer integer = integerPool.get();


        integerPool.release(integer);

        System.out.println(integerPool);

    }

    @Test
    public  void estGrowPool() throws InterruptedException {
        final IntegerPool integerPool = new IntegerPool(
                ObjectPoolConfig.of(5, 10)
        );

       final AtomicReference<Integer> atomicReference  = new AtomicReference<>();


        Thread thread = new Thread(() ->  {
            IntStream.range(0,10).forEach((val) -> integerPool.get());

           atomicReference.set(integerPool.get());


        });
        thread.start();


        try {
            Thread.sleep(10 *1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        integerPool.release(atomicReference.get());






        System.out.println(integerPool);
    }
}
