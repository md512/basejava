package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortedListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

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
    protected int getIndex(String uuid) {
        return Collections.binarySearch(storage, new Resume(uuid));
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteElement(int index) {
        storage.remove(index);
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void saveElement(int index, Resume resume) {
        storage.add(Math.abs(index) - 1, resume);
    }
}
