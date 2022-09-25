package dev.icsw.resumes.storage;

import dev.icsw.resumes.exception.StorageException;
import dev.icsw.resumes.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    /**
     * final int STORAGE_CAPACITY - storage characteristic, the maximum capacity of the storage
     */
    protected static final int STORAGE_CAPACITY = 6;

    /**
     * Array based storage for Resumes
     */
    protected Resume[] storage = new Resume[STORAGE_CAPACITY];

    /**
     * Size of nonempty (nonnull) part of storage[] -- Number of contiguous
     * nonnull Resumes in the storage[] array, starting from the beginning
     * of storage[].
     */
    protected int size = 0;

    /**
     * @return int number of contiguous nonnull Resumes of the storage[] array,
     * starting from the beginning of the storage[].
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return a Resume object with a given uuid or null if there is no such Resume in storage[].
     */
    @Override
    public Resume doGet(Integer index) {
        return storage[index];
    }

    /**
     * @return an array, containing all nonnull/nonempty Resumes in storage[]
     */
    @Override
    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    /**
     * Adds a Resume with a given uuid to the storage[],
     * provided such Resume is not there already.
     */
    @Override
    public void doSave(Resume resume, Integer index) {
        if (size >= STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume, index);
            size++;
        }
    }

    /**
     * Updates a Resume with a given uuid after checking for its presence in storage[].
     */
    @Override
    public void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    /**
     * Removes a Resume with a given uuid while making sure that
     * the remaining nonnull Resumes are still contiguous.
     */
    @Override
    public void doDelete(Integer index) {
        deleteElement(index);
        storage[size - 1] = null;
        size--;
    }

    /**
     * Removes all Resumes from storage[] by replacing all nonnull Resumes in storage[]
     * with the null value.
     */
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isItemLocated(Integer index) {
        return index >= 0;
    }

    /**
     * @return a "location" (a position in the array) of the Resume with a given uuid
     */
    @Override
    protected abstract Integer getItemLocation(String itemId);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void deleteElement(int index);
}
