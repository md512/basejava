package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortedListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();
    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        int index = searchKey;
        return index >= 0;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        return Collections.binarySearch(storage, new Resume(uuid, "name"), RESUME_COMPARATOR);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        storage.add(Math.abs(searchKey) - 1, resume);
    }

    @Override
    protected List<Resume> getStorageAsList() {
        return storage;
    }
}
