package dev.icsw.resumes.storage;

import dev.icsw.resumes.exception.StorageException;
import dev.icsw.resumes.model.Resume;
import dev.icsw.resumes.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private final StreamSerializer streamSerializer;

    protected PathStorage(String storageDir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(storageDir, "Error initializing storage: storage directory cannot be null");
        directory = Paths.get(storageDir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException("Error initializing storage: storage directory " + storageDir + " is not a directory or is nor writable");
        }
        this.streamSerializer = streamSerializer;
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error getting resume: error reading from file: ", getFileName(path), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Error saving resume: error trying to create file: " + path, getFileName(path), e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error updating resume: error writing to file: ", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Error deleting resume: error trying to delete file/directory: ", getFileName(path), e);
        }
    }

    @Override
    protected Path getItemLocation(String fileName) {
        return directory.resolve(fileName);
    }

    @Override
    protected boolean isItemLocated(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> doGetAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Error getting list of resumes: error getting directory's files list: ", e);
        }
    }
}
