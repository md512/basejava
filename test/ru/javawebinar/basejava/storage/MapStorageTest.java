package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import ru.javawebinar.basejava.model.Resume;
import java.util.Arrays;
import java.util.List;


public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() throws Exception {
        List<Resume> actual = Arrays.asList(storage.getAll());
        Assert.assertEquals(3, actual.size());
        Assert.assertTrue(actual.contains(RESUME_1));
        Assert.assertTrue(actual.contains(RESUME_2));
        Assert.assertTrue(actual.contains(RESUME_3));
    }
}