package dev.icsw.resumes;

import dev.icsw.resumes.storage.SqlStorage;
import dev.icsw.resumes.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    // private static final File APP_PROPS_FILE = new File("C:\\Users\\Igor\\Documents\\Topics\\Programming\\Projects\\grigory-kislin\\basejavaresumes\\src\\main\\resources\\app.properties");

    // This code is for localhost deployment
    // private static final File APP_PROPS_FILE = new File(AppConfig.getAppConfigBaseDir(), "app.properties");

    // This code is for Heroku deployment
    private static final String APP_PROPS_FILE = "/app.properties";

    private static final AppConfig APP_CONFIG_INSTANCE = new AppConfig();

    private final File storageDir;
    private final Storage storage;

    public static AppConfig getConfigInstance() {
        return APP_CONFIG_INSTANCE;
    }

    private AppConfig() {

        // This code is for localhost deployment
        // try (InputStream is = new FileInputStream(APP_PROPS_FILE)) {
        //     Properties appProps = new Properties();
        //     appProps.load(is);
        //     storageDir = new File(appProps.getProperty("storage.dir"));
        //     storage = new SqlStorage(appProps.getProperty("db.url"), appProps.getProperty("db.user"), appProps.getProperty("db.password"));
        // } catch (IOException e) {
        //     throw new IllegalStateException("Invalid config file " + APP_PROPS_FILE.getAbsolutePath());
        // }

        // This code is for Heroku deployment
        try (InputStream inputStream = AppConfig.class.getResourceAsStream(APP_PROPS_FILE)) {
            Properties appProps = new Properties();
            appProps.load(inputStream);
            storageDir = new File(appProps.getProperty("storage.dir"));
            storage = new SqlStorage(appProps.getProperty("db.url"), appProps.getProperty("db.user"), appProps.getProperty("db.password"));
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

    // This code is for localhost deployment
    // private static File getAppConfigBaseDir() {
    //     String appProp = System.getProperty("appConfigFileBaseDir");
    //     File baseDir = new File(appProp == null ? "/" : appProp);
    //     if (!baseDir.isDirectory()) {
    //         throw new IllegalStateException(baseDir + " is not directory");
    //     }
    //     return baseDir;
    // }
}
