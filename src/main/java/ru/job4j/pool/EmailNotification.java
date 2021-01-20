package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> send(getSubj(user), getBody(user), user.getEmail()));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
        System.out.println("sending message...");
    }

    private String getSubj(User user) {
        return "Notification " + user.getUsername()
                + " to email " + user.getEmail();
    }

    private String getBody(User user) {
        return "Add a new event to " + user.getUsername();
    }

    public static void main(String[] args) {
        EmailNotification notification = new EmailNotification();
        User user = new User("Name", "some@email.com");
        notification.emailTo(user);
        notification.close();
    }
}