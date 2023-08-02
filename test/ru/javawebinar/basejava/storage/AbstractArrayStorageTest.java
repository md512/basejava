package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String DUMMY = "dummy";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume testResume = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
        Assert.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1);
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID_1));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] actual = {resume1, resume2, resume3};
        Assert.assertArrayEquals(actual, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        storage.save(testResume);
        assertGet(testResume);
        assertSize(4);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
    }

    @Test
    public void get() throws Exception {
        assertGet(resume1);
        assertGet(resume2);
        assertGet(resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(DUMMY);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume(DUMMY));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void notExistAfterDeletion() throws Exception {
        storage.delete(UUID_1);
        storage.get(UUID_1);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        for (int i = 0; i < 10000; i++) {
            try {
                storage.save(new Resume());
            } catch (StorageException e) {
                Assert.fail("Storage overflow ahead of time");
            }
        }
        storage.save(new Resume());
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}