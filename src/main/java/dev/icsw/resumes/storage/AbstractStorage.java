package dev.icsw.resumes.storage;

import dev.icsw.resumes.exception.ItemAlreadyPresentException;
import dev.icsw.resumes.exception.ItemNotPresentException;
import dev.icsw.resumes.exception.StorageException;
import dev.icsw.resumes.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public abstract class AbstractStorage<Location> implements Storage {

    protected abstract Resume doGet(Location itemLocation);

    protected abstract void doSave(Resume resume, Location itemLocation);

    protected abstract void doUpdate(Resume resume, Location itemLocation);

    protected abstract void doDelete(Location itemLocation);

    protected abstract Location getItemLocation(String itemId);

    protected abstract boolean isItemLocated(Location itemLocation);

    protected abstract List<Resume> doGetAll();

    public Resume get(String itemId) {
        Location itemLocation = getExistentItemLocation(itemId);
        return doGet(itemLocation);
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = doGetAll();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    public void save(Resume resume) {
        Objects.requireNonNull(resume, "Error saving resume: resume object is null");
        Location itemLocation = getNonexistentItemLocation(resume.getUuid());
        doSave(resume, itemLocation);
    }

    public void update(Resume resume) {
        Objects.requireNonNull(resume, "Error updating resume: resume object is null");
        Location itemLocation = getExistentItemLocation(resume.getUuid());
        doUpdate(resume, itemLocation);
    }

    public void delete(String itemId) {
        Objects.requireNonNull(itemId, "Error deleting resume: resume's uuid is null");
        if (itemId.isEmpty()) {
            throw new StorageException("Error deleting resume: resume's uuid is empty (uuid=\"\")", "");
        }
        Location itemLocation = getExistentItemLocation(itemId);
        doDelete(itemLocation);
    }

    private Location getExistentItemLocation(String itemId) {
        Location itemLocation = getItemLocation(itemId);
        if (!isItemLocated(itemLocation)) {
            throw new ItemNotPresentException(itemId);
        }
        return itemLocation;
    }

    private Location getNonexistentItemLocation(String itemId) {
        Location itemLocation = getItemLocation(itemId);
        if (isItemLocated(itemLocation)) {
            throw new ItemAlreadyPresentException(itemId);
        }
        return itemLocation;
    }
}