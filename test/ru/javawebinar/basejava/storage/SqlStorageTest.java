package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}