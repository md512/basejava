package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PathStorage extends AbstractPathStorage{

    private SerializationStrategy serializer;

    protected PathStorage(String dir, SerializationStrategy serializer) {
        super(dir);
        this.serializer = serializer;
    }

    public void setSerializationStrategy(SerializationStrategy serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        serializer.doWrite(resume, os);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
