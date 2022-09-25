package dev.icsw.resumes.storage;

import dev.icsw.resumes.model.Resume;

import java.util.List;

public interface Storage {

    int size();

    Resume get(String uuid);

    List<Resume> getAllSorted();

    void save(Resume r);

    void update(Resume r);

    void delete(String uuid);

    void clear();
}