package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeWithException(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(resume.getSections().entrySet(), dos, entry -> {
                SectionType key = entry.getKey();
                Section value = entry.getValue();
                dos.writeUTF(key.name());
                switch (key) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(value.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(((ListSection) value).getItems(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(((OrganizationSection) value).getOrganizations(), dos, org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            String url = org.getHomePage().getUrl();
                            dos.writeUTF(url != null ? url : "null");
                            writeWithException(org.getPositions(), dos, position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                dos.writeUTF(description != null ? description : "null");
                            });
                        });
                        break;
                    default:
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, () -> {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(sectionType, new ListSection(readSectionWithException(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.setSection(sectionType, new OrganizationSection(readSectionWithException(dis, () -> {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            if (url.equals("null")) {
                                url = null;
                            }
                            Link homePage = new Link(name, url);
                            List<Organization.Position> positions = readSectionWithException(dis, () -> {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("null")) {
                                    description = null;
                                }
                                return new Organization.Position(startDate, endDate, title, description);
                            });
                            return new Organization(homePage, positions);
                        })));
                        break;
                    default:
                        break;
                }
            });
            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, DataWriter<T> action) throws IOException{
        Objects.requireNonNull(action);
        dos.writeInt(collection.size());
        for (T item : collection) {
            action.accept(item);
        }
    }

    private <T> List<T> readSectionWithException(DataInputStream dis, SectionReader<T> sectionReader) throws IOException {
        Objects.requireNonNull(sectionReader);
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(sectionReader.get());
        }
        return list;
    }

    private void readWithException(DataInputStream dis, DataReader dataReader) throws IOException {
        Objects.requireNonNull(dataReader);
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            dataReader.read();
        }
    }
}

@FunctionalInterface
interface DataWriter<T> {
    void accept(T t) throws IOException;
}

@FunctionalInterface
interface SectionReader<T> {
    T get() throws IOException;
}

@FunctionalInterface
interface DataReader {
    void read() throws IOException;
}