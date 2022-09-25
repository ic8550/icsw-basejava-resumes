package dev.icsw.resumes.storage;

import dev.icsw.resumes.AppConfig;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(AppConfig.getConfigInstance().getStorage());
    }
}
