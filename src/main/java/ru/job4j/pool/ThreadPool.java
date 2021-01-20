package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<RunnableThread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;
    private final static int SIZE = Runtime.getRuntime().availableProcessors();

    public ThreadPool(int tasksMaxNumber) {
        tasks = new SimpleBlockingQueue<>(tasksMaxNumber);
        for (int i = 0; i < SIZE; i++) {
            threads.add(new RunnableThread(tasks));
            threads.get(i).start();
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(RunnableThread::stopThread);
    }
}