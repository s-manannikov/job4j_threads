package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) {
        while (queue.size() == size) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        T obj = queue.poll();
        if (obj == null) {
            throw new NoSuchElementException();
        }
        notifyAll();
        return obj;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}