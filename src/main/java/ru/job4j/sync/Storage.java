package ru.job4j.sync;

public interface Storage {

    boolean add(User user);

    User get(int id);

    boolean update(User user);

    boolean delete(User user);
}