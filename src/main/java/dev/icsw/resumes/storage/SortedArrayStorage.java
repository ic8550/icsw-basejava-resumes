package dev.icsw.resumes.storage;

import dev.icsw.resumes.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    /**
     * @return a "location", (an index, a position in the array) of the Resume with a given uuid
     */
    @Override
    protected Integer getItemLocation(String itemId) {
        if (itemId == null || itemId.equals("")) {
            return -1;
        }
        Resume resume = new Resume(itemId, "Name");
        return Arrays.binarySearch(storage, 0, size, resume, RESUME_COMPARATOR);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        int insertionPoint = -index - 1;
        System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        storage[insertionPoint] = resume;
    }

    @Override
    protected void deleteElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}
