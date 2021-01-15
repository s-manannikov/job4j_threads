package ru.job4j;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        return content(i -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(i -> i > 0x80);
    }

    private String content(Predicate<Integer> predicate) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                if (predicate.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}