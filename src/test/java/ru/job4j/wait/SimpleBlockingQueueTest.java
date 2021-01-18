package ru.job4j.wait;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenOffer4ThenPoll2() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
            queue.offer(4);
        });
        Thread consumer = new Thread(() -> {
            buffer.add(queue.poll());
            buffer.add(queue.poll());
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(buffer, List.of(1, 2));
    }

    @Test
    public void whenOffer2ThenPoll2() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
        });
        Thread consumer = new Thread(() -> {
            buffer.add(queue.poll());
            buffer.add(queue.poll());
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(buffer, List.of(1, 2));
    }
}