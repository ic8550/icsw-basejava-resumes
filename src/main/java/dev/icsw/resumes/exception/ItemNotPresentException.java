package dev.icsw.resumes.exception;

public class ItemNotPresentException extends StorageException {
    public ItemNotPresentException(String uuid) {
        super("Resume with uuid=\"" + uuid + "\" not present in storage", uuid);
    }
}
