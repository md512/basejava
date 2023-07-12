package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void addToStorage(Resume resume, int index) {
        int newIndex = Math.abs(index) - 1;
        for (int i = size; i > newIndex; i--) {
            storage[i] = storage[i - 1];
        }
        storage[newIndex] = resume;
    }

    @Override
    protected void removeFromStorage(int index) {
        for (int i = index; i < size; i++) {
            storage[i] = storage[i + 1];
        }
    }
}
