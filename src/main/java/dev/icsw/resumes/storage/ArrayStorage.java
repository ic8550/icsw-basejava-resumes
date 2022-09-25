package dev.icsw.resumes.storage;

import dev.icsw.resumes.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    /**
     * @return a "location" (an index, a position in the array) of the Resume with a given uuid
     */
    @Override
    protected Integer getItemLocation(String itemId) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(itemId)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteElement(int index) {
        storage[index] = storage[size - 1];
    }
}
