package ru.job4j.cas;

import java.util.Objects;

public class Base {
    private final int id;
    private String name;
    private int version;

    public Base(int id, String name) {
        this.id = id;
        this.name = name;
        this.version = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base base = (Base) o;
        return id == base.id && version == base.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public String toString() {
        return "Base{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", version=" + version
                + '}';
    }
}
