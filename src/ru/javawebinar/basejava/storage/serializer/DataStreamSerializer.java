package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());

            writeWithException(sections.entrySet(), dos, entry -> {
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
                        List<String> items = ((ListSection) value).getItems();
                        dos.writeInt(items.size());
                        writeWithException(items, dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = ((OrganizationSection) value).getOrganizations();
                        dos.writeInt(organizations.size());
                        writeWithException(organizations, dos, org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            String url = org.getHomePage().getUrl();
                            dos.writeUTF(url != null ? url : "null");
                            dos.writeInt(org.getPositions().size());
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

            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(readList(dis)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType, new OrganizationSection(readOrganizations(dis)));
                        break;
                    default:
                        break;
                }
            }
            return resume;
        }
    }

    private List<String> readList(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        return list;
    }

    private List<Organization> readOrganizations(DataInputStream dis) throws IOException {
        int orgSize = dis.readInt();
        List<Organization> organizations = new ArrayList<>();
        for (int i = 0; i < orgSize; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            if (url.equals("null")) {
                url = null;
            }
            Link homePage = new Link(name, url);
            List<Organization.Position> positions = new ArrayList<>();
            int posSize = dis.readInt();
            for (int j = 0; j < posSize; j++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF());
                LocalDate endDate = LocalDate.parse(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                if (description.equals("null")) {
                    description = null;
                }
                positions.add(new Organization.Position(startDate, endDate, title, description));
            }
            organizations.add(new Organization(homePage, positions));
        }
        return organizations;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ExcConsumer<T> action) throws IOException{
        Objects.requireNonNull(action);
        for (T item : collection) {
            action.accept(item);
        }

    }
}

@FunctionalInterface
interface ExcConsumer<T> {
    void accept(T t) throws IOException;
}

