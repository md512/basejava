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
        List<Resume> resultList = Arrays.asList(storage.getAll());
        Assert.assertEquals(3, resultList.size());
        Assert.assertTrue(resultList.contains(RESUME_1));
        Assert.assertTrue(resultList.contains(RESUME_2));
        Assert.assertTrue(resultList.contains(RESUME_3));
    }
}