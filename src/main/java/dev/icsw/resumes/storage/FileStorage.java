package dev.icsw.resumes.storage;

import dev.icsw.resumes.exception.StorageException;
import dev.icsw.resumes.model.Resume;
import dev.icsw.resumes.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File storageDir;
    private final StreamSerializer streamSerializer;

    protected FileStorage(File storageDir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(storageDir, "Error initializing storage: storage directory is null");
        if (!storageDir.isDirectory()) {
            throw new IllegalArgumentException("Error initializing storage: storage directory " + storageDir.getAbsolutePath() + " is not a directory");
        }
        if (!storageDir.canRead() || !storageDir.canWrite()) {
            throw new IllegalArgumentException("Error initializing storage: storage directory " + storageDir.getAbsolutePath() + " is not readable/writable");
        }
        this.storageDir = storageDir;
        this.streamSerializer = streamSerializer;
    }
    @Override
    protected Resume doGet(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Error reading from file ", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Error trying to create file " + file.getName(), file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Error writing to file " + file.getName(), file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Error trying to delete file ", file.getName());
        }
    }

    @Override
    protected File getItemLocation(String fileName) {
        return new File(storageDir, fileName);
    }

    @Override
    protected boolean isItemLocated(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] resumesArray = getFilesList();
        for (File resume : resumesArray) {
            resumes.add(doGet(resume));
        }
        return resumes;
    }

    @Override
    public int size() {
        return getFilesList().length;
    }

    @Override
    public void clear() {
        File[] files = getFilesList();
        for (File file : files) {
            doDelete(file);
        }
    }

    private File[] getFilesList() {
        File[] files = storageDir.listFiles();
        if (files == null) {
            throw new NullPointerException("Error getting directory's files list: listFiles() returned null");
        }
        return files;
    }
}
