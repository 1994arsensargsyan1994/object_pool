//package com.example.object_pool;
//
//import io.arsen.object.pool.config.ObjectPoolConfig;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.IntStream;
//
//public class IntegerPoolTest {
//
//
//    IntegerPool integerPool;
//
//    @Before
//    public void init() {
//        integerPool = new IntegerPool(
//                ObjectPoolConfig.of(POOL_MIN_SIZE, POOL_MAX_SIZE)
//        );
//    }
//
//    @Test
//    public void testMultiThreading() throws InterruptedException {
//
//        final AtomicReference<Integer> atomicReference = new AtomicReference<>();
//
//        final Thread t1 = new Thread(() -> {
//            System.out.println("t1 starting");
//            atomicReference.set(integerPool.get());
//            System.out.println("t1 finishing");
//        });
//        t1.start();
//
//        Thread.sleep(5000);
//
//        final Thread t2 = new Thread(() -> {
//            System.out.println("t2 starting");
//            final Integer integer = integerPool.get();
//            System.out.println("t2 finishing");
//
//        });
//        t2.start();
//
//        Thread.sleep(5000);
//
//        System.out.println("releasing object to the pool");
//        integerPool.release(atomicReference.get());
//
//        Thread.sleep(5000);
//
//    }
//
//    @Test
//    public void test() {
//
//
//        Integer integer = integerPool.get();
//
//
//        integerPool.release(integer);
//
//        System.out.println(integerPool);
//
//    }
//
//    @Test
//    public void estGrowPool() throws InterruptedException {
//        final IntegerPool integerPool = new IntegerPool(
//                ObjectPoolConfig.of(5, 10)
//        );
//
//        final AtomicReference<Integer> atomicReference = new AtomicReference<>();
//        final AtomicReference<Integer> atomicReference2 = new AtomicReference<>();
//
//
//        Thread thread = new Thread(() -> {
//            IntStream.range(0, 9).forEach((val) -> integerPool.get());
//
//            atomicReference.set(integerPool.get());
//            atomicReference2.set(integerPool.get());
//
//
//        });
//        thread.start();
//
//
//        try {
//            Thread.sleep(10 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        integerPool.release(atomicReference.get());
//
//
//        System.out.println(integerPool);
//    }
//
//
//    @Test
//    public void testValidator() {
//        Integer integer = integerPool.get();
//        integerPool.release(56878);
//    }
//}
