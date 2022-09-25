package dev.icsw.resumes;

import dev.icsw.resumes.exception.StorageException;
import dev.icsw.resumes.model.Resume;
import dev.icsw.resumes.storage.SortedArrayStorage;

import java.util.List;

/**
 * Test for your ListStorage implementation
 */
public class MainTestSortedArrayStorage {
    static final SortedArrayStorage STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r3 = new Resume("uuid-3", "Name-3");
        Resume r33 = new Resume("uuid-3", "Name-3");
        Resume r2 = new Resume("uuid-2", "Name-2");
        Resume r22 = new Resume("uuid-2", "Name-22");
        Resume r1 = new Resume("uuid-1", "Name-1");
        Resume r4 = new Resume("uuid-4", "Name-4");
        Resume r5 = new Resume("uuid-5", "Name-N");
        Resume r6 = new Resume("uuid-6", "Name-N");
        Resume r7 = new Resume("uuid-7", "Name-N");

        printAll();
        printSize();

        System.out.println("Clear:");
        STORAGE.clear();
        printAll();
        printSize();

        System.out.println("Get resume with uuid=null:");
        try {
            Resume resume = STORAGE.get(null);
            System.out.println(resume);
        } catch (StorageException e) {
            System.out.println("Error in get(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Get resume with uuid=\"\":");
        try {
            Resume resume = STORAGE.get("");
            System.out.println(resume);
        } catch (StorageException e) {
            System.out.println("Error in get(): " + e.getMessage());
        }

        printAll();
        printSize();

        System.out.println("Get resume with uuid=\"dummy\":");
        try {
            Resume resume = STORAGE.get("dummy");
            System.out.println(resume);
        } catch (StorageException e) {
            System.out.println("Error in get(): " + e.getMessage());
        }

        printAll();
        printSize();

        System.out.println("Delete resume with uuid=null:");
        try {
            STORAGE.delete(null);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Delete resume with uuid=\"\":");
        try {
            STORAGE.delete("");
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Delete resume with uuid=\"dummy\":");
        try {
            STORAGE.delete("dummy");
        } catch (StorageException e) {
            System.out.println("Error delete(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save null:");
        try {
            STORAGE.save(null);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save r3:");
        try {
            STORAGE.save(r3);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save r3 again:");
        try {
            STORAGE.save(r33);
        } catch (StorageException e) {
            System.out.println("Error save(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save r2:");
        try {
            STORAGE.save(r2);
        } catch (StorageException e) {
            System.out.println("Error save(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save r1:");
        try {
            STORAGE.save(r1);
        } catch (StorageException e) {
            System.out.println("Error save(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save r4:");
        try {
            STORAGE.save(r4);
        } catch (StorageException e) {
            System.out.println("Error save(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save r7:");
        try {
            STORAGE.save(r7);
        } catch (StorageException e) {
            System.out.println("Error save(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save r6:");
        try {
            STORAGE.save(r6);
        } catch (StorageException e) {
            System.out.println("Error save(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Save r5:");
        try {
            STORAGE.save(r5);
        } catch (StorageException e) {
            System.out.println("Error save(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Get resume with uuid=null:");
        try {
            Resume resume = STORAGE.get(null);
            System.out.println(resume);
        } catch (StorageException e) {
            System.out.println("Error in get(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Get resume with uuid=\"\":");
        try {
            Resume resume = STORAGE.get("");
            System.out.println(resume);
        } catch (StorageException e) {
            System.out.println("Error in get(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Get resume with uuid=\"dummy\":");
        try {
            Resume resume = STORAGE.get("dummy");
            System.out.println(resume);
        } catch (StorageException e) {
            System.out.println("Error in get(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Get r1:");
        try {
            Resume resume = STORAGE.get(r1.getUuid());
            System.out.println(resume);
        } catch (StorageException e) {
            System.out.println("Error in get(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Update null:");
        try {
            STORAGE.update(null);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Update r2:");
        try {
            STORAGE.update(r22);
        } catch (StorageException e) {
            System.out.println("Error in update(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Delete resume with uuid=null:");
        try {
            STORAGE.delete(null);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Delete resume with uuid=\"\":");
        try {
            STORAGE.delete("");
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Delete resume with uuid=\"dummy\":");
        try {
            STORAGE.delete("dummy");
        } catch (StorageException e) {
            System.out.println("Error in delete(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Delete r3:");
        try {
            STORAGE.delete(r3.getUuid());
        } catch (StorageException e) {
            System.out.println("Error in delete(): " + e.getMessage());
        }
        printAll();
        printSize();

        System.out.println("Clear:");
        STORAGE.clear();
        printAll();
        printSize();
    }

    static void printAll() {
        List<Resume> all = STORAGE.getAllSorted();
        System.out.println("\nStorage state:");
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
    static void printSize() {
        System.out.println("Storage size: " + STORAGE.size() + "\n\n");
    }
}
