package dev.icsw.resumes.storage;

import dev.icsw.resumes.exception.StorageException;
import dev.icsw.resumes.util.UtilResumes;
import org.junit.jupiter.api.Test;

import static dev.icsw.resumes.storage.AbstractArrayStorage.STORAGE_CAPACITY;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void saveBeyondCapacity() {
        try {
            for (int i = storage.size(); i < STORAGE_CAPACITY; i++) {
                // storage.save(new Resume("FakeName"));
                String StringValueOfIndex = String.valueOf(i);
                storage.save(UtilResumes.fillOut(StringValueOfIndex, "NAME-" + StringValueOfIndex));
            }
        } catch (StorageException e) {
            fail("Error: storage overflow within storage capacity");
        }
        // assertThrows(StorageException.class, () -> storage.save(new Resume("Name")));
        assertThrows(StorageException.class, () -> storage.save(UtilResumes.fillOut(String.valueOf(STORAGE_CAPACITY), "NAME-" + STORAGE_CAPACITY)));
    }
}
