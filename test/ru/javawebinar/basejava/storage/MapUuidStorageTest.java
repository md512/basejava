package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import ru.javawebinar.basejava.model.Resume;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.basejava.TestData.*;


public class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    public void getAllSorted() throws Exception {
        List<Resume> resultList = Arrays.asList(storage.getAllSorted().toArray(new Resume[0]));
        Assert.assertEquals(3, resultList.size());
        Assert.assertTrue(resultList.contains(R1));
        Assert.assertTrue(resultList.contains(R2));
        Assert.assertTrue(resultList.contains(R3));
    }
}