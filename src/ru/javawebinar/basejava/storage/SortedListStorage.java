package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortedListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = new Resume[size()];
        return storage.toArray(result);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        int index = (Integer) searchKey;
        return index >= 0;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return Collections.binarySearch(storage, new Resume(uuid));
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        storage.add(Math.abs((Integer) searchKey) - 1, resume);
    }
}
