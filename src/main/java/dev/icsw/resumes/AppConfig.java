package dev.icsw.resumes;

import dev.icsw.resumes.storage.SqlStorage;
import dev.icsw.resumes.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final String APP_PROPS_FILE = "/app.properties";
    private static final AppConfig APP_CONFIG_INSTANCE = new AppConfig();
    private final File storageDir;
    private final Storage storage;

    public static AppConfig getConfigInstance() {
        return APP_CONFIG_INSTANCE;
    }

    private AppConfig() {
        try (InputStream inputStream = AppConfig.class.getResourceAsStream(APP_PROPS_FILE)) {
            Properties appProps = new Properties();
            appProps.load(inputStream);
            storageDir = new File(appProps.getProperty("storage.dir"));
            // storage = new SqlStorage(appProps.getProperty("db.url"), appProps.getProperty("db.user"), appProps.getProperty("db.password"));
            storage = new SqlStorage(System.getenv("DB_URL"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + APP_PROPS_FILE);
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
