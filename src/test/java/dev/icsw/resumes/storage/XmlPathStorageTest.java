package dev.icsw.resumes.storage;

import dev.icsw.resumes.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        // super(new PathStorage(UtilFS.getDirectoryName("../test-xml-storage"), new XmlStreamSerializer()));
        super(new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}
