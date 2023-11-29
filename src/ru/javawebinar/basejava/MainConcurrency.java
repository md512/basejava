package ru.javawebinar.basejava;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
//    private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
//        CompletionService completionService = new ExecutorCompletionService(executorService);

//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
//            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
                return 5;
            });
//            System.out.println(future.isDone());
//            thread.start();
//            threads.add(thread);
        }

/*        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(mainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());
    }

    private void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
//        lock.lock();
//        try {
        atomicCounter.incrementAndGet();
//            counter++;
//        } finally {
//            lock.unlock();
//        }
//                wait();
//                readFile
//                ...
//        }
    }
}