package dev.icsw.resumes.storage;

import dev.icsw.resumes.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        // super(new PathStorage(UtilFS.getDirectoryName("../test-json-storage"), new JsonStreamSerializer()));
        super(new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}
