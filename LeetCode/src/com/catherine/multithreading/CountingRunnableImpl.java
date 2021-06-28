package com.catherine.multithreading;

/**
 * @author : Catherine
 * @created : 28/06/2021
 */
public class CountingRunnableImpl implements Runnable {
    private ThreadEventHandler handler;

    public CountingRunnableImpl() {

    }

    public CountingRunnableImpl(ThreadEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        System.out.printf("Current thread is %s%n", Thread.currentThread().getName());
        if (handler != null) {
            handler.onFinished("Done");
        }
    }
}
