package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T el;
    private int from;
    private int to;

    public ParallelSearch(T[] array, T el, int from, int to) {
        this.array = array;
        this.el = el;
        this.from = 0;
        this.to = array.length - 1;
    }

    public ParallelSearch(T[] array, T el) {
        this.array = array;
        this.el = el;
    }

    @Override
    protected Integer compute() {
        if (from - to <= 10) {
            return indexOf();
        }
        int mid = (from - to) / 2;
        ParallelSearch<T> ps = new ParallelSearch<>(array, el, from, mid);
        ParallelSearch<T> ps2 = new ParallelSearch<>(array, el, mid + 1, to);
        ps.fork();
        ps2.fork();
        return Math.max(ps.join(), ps2.join());
    }

    private int indexOf() {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(el)) {
                rsl = i;
            }
        }
        return rsl;
    }

    public int find() {
        ForkJoinPool fjp = new ForkJoinPool();
        return fjp.invoke(new ParallelSearch<>(array, el));
    }
}
