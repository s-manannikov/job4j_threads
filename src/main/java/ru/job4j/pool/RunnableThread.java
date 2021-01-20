package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

public class RunnableThread extends Thread {
    private final SimpleBlockingQueue<Runnable> tasks;

    public RunnableThread(SimpleBlockingQueue<Runnable> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                tasks.poll().run();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        Thread.currentThread().interrupt();
    }
}