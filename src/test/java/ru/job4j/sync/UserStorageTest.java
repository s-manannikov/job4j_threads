package ru.job4j.sync;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenAddUsers() {
        Storage store = new UserStorage();
        User user1 = new User(1, 123);
        User user2 = new User(1, 567);
        assertTrue(store.add(user1));
        assertFalse(store.add(user2));
    }

    @Test
    public void whenUpdateUser() {
        UserStorage store = new UserStorage();
        User user1 = new User(1, 123);
        User user2 = new User(1, 567);
        store.add(user1);
        assertTrue(store.update(user2));
    }

    @Test
    public void whenRemoveUser() {
        UserStorage store = new UserStorage();
        User user1 = new User(1, 123);
        assertFalse(store.delete(user1));
        store.add(user1);
        assertTrue(store.delete(user1));
    }

    @Test
    public void whenTransferAmount() {
        UserStorage store = new UserStorage();
        User user1 = new User(1, 123);
        User user2 = new User(2, 567);
        store.add(user1);
        store.add(user2);
        store.transfer(1, 2, 100);
        assertEquals(user1.getAmount(), 23);
        assertEquals(user2.getAmount(), 667);
    }
}