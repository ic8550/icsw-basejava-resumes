package dev.icsw.resumes.storage.serializer;

import dev.icsw.resumes.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outStream) throws IOException {
        try (DataOutputStream dataOutStream = new DataOutputStream(outStream)) {
            dataOutStream.writeUTF(resume.getUuid());
            dataOutStream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dataOutStream, contacts.entrySet(), entry -> {
                dataOutStream.writeUTF(entry.getKey().name());
                dataOutStream.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeCollection(dataOutStream, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                dataOutStream.writeUTF(sectionType.name());
                AbstractSection sectionValue = entry.getValue();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutStream.writeUTF(((TextSection) sectionValue).getText());
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        writeCollection(dataOutStream, ((ListSection) sectionValue).getContent(), dataOutStream::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dataOutStream, ((OrganizationSection) sectionValue).getOrganizations(), org -> {
                            dataOutStream.writeUTF(org.getHomePage().getName());
                            dataOutStream.writeUTF(org.getHomePage().getUrl());
                            writeCollection(dataOutStream, org.getActivities(), activity -> {
                                writeLocalDate(dataOutStream, activity.getStartDate());
                                writeLocalDate(dataOutStream, activity.getEndDate());
                                dataOutStream.writeUTF(activity.getTitle());
                                dataOutStream.writeUTF(activity.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    private <T> void writeCollection(DataOutputStream dataOutStream, Collection<T> collection, ListItemWriter<T> itemWriter) throws IOException {
        dataOutStream.writeInt(collection.size());
        for (T item : collection) {
            itemWriter.write(item);
        }
    }

    private interface ListItemWriter<T> {
        void write(T item) throws IOException;
    }

    private void writeLocalDate(DataOutputStream dataOutStream, LocalDate localDate) throws IOException {
        dataOutStream.writeInt(localDate.getYear());
        dataOutStream.writeInt(localDate.getMonth().getValue());
        dataOutStream.writeInt(localDate.getDayOfMonth());
    }

    @Override
    public Resume doRead(InputStream inStream) throws IOException {
        try (DataInputStream dataInStream = new DataInputStream(inStream)) {
            String uuid = dataInStream.readUTF();
            String fullName = dataInStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readEnumMap(dataInStream, () -> {
                ContactType contactType = ContactType.valueOf(dataInStream.readUTF());
                String contactValue = dataInStream.readUTF();
                resume.addContact(contactType, contactValue);
            });
            readEnumMap(dataInStream, () -> {
                SectionType sectionType = SectionType.valueOf(dataInStream.readUTF());
                AbstractSection sectionValue = readSection(dataInStream, sectionType);
                resume.addSection(sectionType, sectionValue);
            });
            return resume;
        }
    }

    private void readEnumMap(DataInputStream dataInStream, MapEntryReader entryReader) throws IOException {
        int size = dataInStream.readInt();
        for (int i = 0; i < size; i++) {
            entryReader.read();
        }
    }

    private interface MapEntryReader {
        void read() throws IOException;
    }

    private AbstractSection readSection(DataInputStream dataInStream, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dataInStream.readUTF());
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                return new ListSection(readList(dataInStream, dataInStream::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(
                        readList(dataInStream, () -> new Organization(
                                new Link(dataInStream.readUTF(), dataInStream.readUTF()),
                                readList(dataInStream, () -> new Organization.Activity(
                                        readLocalDate(dataInStream),
                                        readLocalDate(dataInStream),
                                        dataInStream.readUTF(),
                                        dataInStream.readUTF()
                                ))
                        )));
            default: throw new RuntimeException("Invalid Resume Section");
        }
    }

    private <T> List<T> readList(DataInputStream dataInStream, ListItemReader<T> itemReader) throws IOException {
        int size = dataInStream.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(itemReader.read());
        }
        return list;
    }

    private interface ListItemReader<T> {
        T read() throws IOException;
    }

    private LocalDate readLocalDate(DataInputStream dataInStream) throws IOException {
        return LocalDate.of(dataInStream.readInt(), dataInStream.readInt(), dataInStream.readInt());
    }
}
