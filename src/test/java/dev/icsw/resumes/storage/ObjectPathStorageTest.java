package dev.icsw.resumes.storage;

import dev.icsw.resumes.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        // super(new PathStorage(UtilFS.getDirectoryName("../test-path-storage"), new ObjectStreamSerializer()));
        super(new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}
