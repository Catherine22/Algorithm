package com.catherine.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : Catherine
 * @created : 28/06/2021
 */
public class Main {
    public static void main(String[] args) {
        System.out.printf("Current thread is %s%n", Thread.currentThread().getName());

        CountingRunnableImpl countingRunnable = new CountingRunnableImpl();
        for (int i = 0; i < 3; i++) {
            Thread countingThread2 = new Thread(countingRunnable);
            countingThread2.start();
        }

        // Create a new thread is expensive and therefore, we use ThreadPool when we need multiple threads
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.printf("%d processor(s)%n", coreCount);

        // The pool size depends on the task you want to do.
        // E.g., your tasks are CPU intensive, then you could set the pool size = the numbers of processors
        // E.g., your tasks are I/O intensive, then set larger pool size.
        // Too many threads will increase memory assumption.
        ExecutorService service = Executors.newFixedThreadPool(coreCount);
        for (int i = 0; i < 3; i++) {
            service.execute(countingRunnable);
        }

        // Execute tasks sequentially
        CountingRunnableImpl sequentialTask = new CountingRunnableImpl(System.out::println);
        Thread thread1 = new Thread(sequentialTask);
        thread1.start();
        Thread thread2 = new Thread(sequentialTask);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
