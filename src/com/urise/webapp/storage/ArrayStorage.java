package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (checkResume(resume.uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(resume.uuid) ) {
                    storage[i] = resume;
                }
            }
        } else {
            System.out.println("Error: resume " + resume + " not in the storage.");
        }
    }

    public void save(Resume resume) {
        if (!checkResume(resume.uuid)) {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Error: storage is full.");
            }
        } else {
            System.out.println("Error: resume " + resume + " already in the storage.");
        }
    }

    public Resume get(String uuid) {
        if (checkResume(uuid)) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].uuid)) {
                    return storage[i];
                }
            }
        } else {
            System.out.println("Error: resume " + uuid + " not in the storage.");
        }
        return null;
    }

    public void delete(String uuid) {
        if (checkResume(uuid)) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].uuid)) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                }
            }
        } else {
            System.out.println("Error: resume " + uuid + " not in the storage.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private boolean checkResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return true;
            }
        }
        return false;
    }
}