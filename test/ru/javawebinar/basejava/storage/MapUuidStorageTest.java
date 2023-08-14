package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import ru.javawebinar.basejava.model.Resume;
import java.util.Arrays;
import java.util.List;


public class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    public void getAllSorted() throws Exception {
        List<Resume> resultList = Arrays.asList(storage.getAllSorted().toArray(new Resume[0]));
        Assert.assertEquals(3, resultList.size());
        Assert.assertTrue(resultList.contains(RESUME_1));
        Assert.assertTrue(resultList.contains(RESUME_2));
        Assert.assertTrue(resultList.contains(RESUME_3));
    }
}