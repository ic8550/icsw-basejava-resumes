package dev.icsw.resumes.storage;

import dev.icsw.resumes.storage.serializer.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        // super(new FileStorage(UtilFS.getDirectory("../test-file-storage"), new ObjectStreamSerializer()));
        super(new FileStorage(AbstractStorageTest.STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
