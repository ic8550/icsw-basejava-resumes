package dev.icsw.resumes.exception;

public class ItemAlreadyPresentException extends StorageException {
    public ItemAlreadyPresentException(String uuid) {
        super("Resume with uuid=\\\"\" + uuid + \"\\\" already present in storage", uuid);
    }
}
