package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;

@ThreadSafe
public class UserStorage implements Storage {
    @GuardedBy("this")
    private final HashMap<Integer, User> map = new HashMap<>();

    public synchronized boolean add(User user) {
        return map.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return map.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return map.remove(user.getId()) != null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User source = map.get(fromId);
        User dest = map.get(toId);
        if (source != null && dest != null && source.getAmount() >= amount) {
            source.setAmount(source.getAmount() - amount);
            dest.setAmount(dest.getAmount() + amount);
        } else {
            System.out.println("Not enough amount or user not found!");
        }
    }
}