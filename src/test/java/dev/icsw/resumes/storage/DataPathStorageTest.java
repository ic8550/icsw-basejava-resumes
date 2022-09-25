package dev.icsw.resumes.storage;

import dev.icsw.resumes.storage.serializer.DataStreamSerializer;
import dev.icsw.resumes.util.UtilFS;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(UtilFS.getDirectoryName("../test-data-storage"), new DataStreamSerializer()));
    }
}
