package ru.job4j.cas;

import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingCache {
    protected final ConcurrentHashMap<Integer, Base> map = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return map.putIfAbsent(model.getId(), model) != null;
    }

    public boolean update(Base newModel) {
        if (map.get(newModel.getId()) == null) {
            return false;
        }
        Base currentModel = map.get(newModel.getId());
        int currentVersion = currentModel.getVersion();
        map.computeIfPresent(newModel.getId(), (key, value) -> {
            value = newModel;
            value.setVersion(value.getVersion() + 1);
            if (value.getVersion() == currentVersion) {
                throw new OptimisticException("The model has been already changed!");
            }
            return value;
        });
        return true;
    }

    public boolean delete(Base model) {
        return map.remove(model.getId()) != null;
    }
}