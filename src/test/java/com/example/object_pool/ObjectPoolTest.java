//package com.example.object_pool;
//
//import com.example.object_pool.config.ObjectPollConfig;
//import com.example.object_pool.exception.BlockingPoolException;
//import com.example.object_pool.use.User;
//import com.example.object_pool.use.UserPool;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//
//import static org.junit.Assert.assertEquals;
//
//public class ObjectPoolTest {
//
//
//    UserPool pool;
//
//
//    @Before
//    public void init() throws BlockingPoolException {
//        Collection<User> elementsToPool = Arrays.asList(new User("Arse", 18),
//                new User("Arsen", 18),
//                new User("Valod", 58),
//                new User("Marat", 19));
//        pool = new UserPool(elementsToPool);
//    }
//
//
//    @Test(expected = BlockingPoolException.class)
//    public void testEmptyCollectionConstruction() throws BlockingPoolException {
//        Collection<User> elementsToPool = new ArrayList<>();
//        pool = new UserPool(elementsToPool);
//
//    }
//
//    @Test(expected = BlockingPoolException.class)
//    public void testWrongPoolSize() throws BlockingPoolException {
//        pool = new UserPool(-1);
//    }
//
//
//    @Test
//    public void testConfig() throws BlockingPoolException {
//
//        ObjectPollConfig config = new ObjectPollConfig.Builder()
//                .setPollSize(8)
//                .setWaitTime(1000)
//                .build();
//        pool = new UserPool(config);
//        assertEquals(8, pool.getSize());
//    }
//
//    @Test
//    public void testSize() {
//        assertEquals(4, pool.getSize());
//    }
//
//    @Test
//    public void putObject() throws BlockingPoolException {
//        pool = new UserPool(4);
//        assertEquals(4, pool.getSize());
//        User user = pool.get();
//        User user1 = pool.get();
//        User user2 = pool.get();
//        User user3 = pool.get();
//        assertEquals(0, pool.getSize());
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        pool.release(user);
//        assertEquals(1, pool.getSize());
//
//    }
//
//    @Test
//    public void testBlockingCondition() throws InterruptedException, BlockingPoolException {
//        ObjectPollConfig config = new ObjectPollConfig.Builder()
//                .setPollSize(1)
//                .setWaitTime(1000)
//                .build();
//        pool = new UserPool(config);
//        Thread thread1 = new Thread() {
//            public void run() {
//                User element1 = pool.get();
//
//                try {
//                    sleep(5000);
//                    assertEquals(0, pool.getSize());
//                    assertEquals("Arsen1", element1.getName());
//                    pool.release(element1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        Thread thread2 = new Thread() {
//            public void run() {
//                try {
//                    sleep(1000);
//                    User element1 = pool.get();
//                    assertEquals(0, pool.getSize());
//                    assertEquals("Arsen1", element1.getName());
//                    pool.release(element1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        thread1.start();
//        thread2.start();
//        thread1.join();
//        thread2.join();
//    }
//
//
//}
