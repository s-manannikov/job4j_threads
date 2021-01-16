package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage implements Storage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> list = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return list.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized User get(int id) {
        return list.get(id) == null ? new User(0, 0) : list.get(id);
    }

    public synchronized boolean update(User user) {
        return list.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return list.remove(user.getId()) != null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User source = get(fromId);
        User dest = get(toId);
        if (source.getAmount() >= amount) {
            source.setAmount(source.getAmount() - amount);
            dest.setAmount(dest.getAmount() + amount);
        } else {
            System.out.println("Not enough amount or user not found!");
        }
    }
}