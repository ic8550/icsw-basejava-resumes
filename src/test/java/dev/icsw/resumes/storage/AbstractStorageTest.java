package dev.icsw.resumes.storage;

import dev.icsw.resumes.AppConfig;
import dev.icsw.resumes.exception.ItemAlreadyPresentException;
import dev.icsw.resumes.exception.ItemNotPresentException;
import dev.icsw.resumes.model.Resume;
import dev.icsw.resumes.util.UtilResumes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = AppConfig.getConfigInstance().getStorageDir();

    protected final Storage storage;

    private static final Resume RESUME_1 = UtilResumes.fillWithNumber(1);
    private static final Resume RESUME_2 = UtilResumes.fillWithNumber(2);
    private static final Resume RESUME_3 = UtilResumes.fillWithNumber(3);
    private static final Resume RESUME_4 = UtilResumes.fillWithNumber(4);
    private static final Resume RESUME_5 = UtilResumes.fillWithNumber(5);
    private static final Resume RESUME_6 = UtilResumes.fillWithNumber(6);

    // private static final Resume RESUME_1 = UtilResumes.fillWithNumberUuidName(1);
    // private static final Resume RESUME_2 = UtilResumes.fillWithNumberUuidName(2);
    // private static final Resume RESUME_3 = UtilResumes.fillWithNumberUuidName(3);
    // private static final Resume RESUME_4 = UtilResumes.fillWithNumberUuidName(4);
    // private static final Resume RESUME_5 = UtilResumes.fillWithNumberUuidName(5);
    // private static final Resume RESUME_6 = UtilResumes.fillWithNumberUuidName(6);

    // private static final Resume RESUME_1 = UtilResumes.fillWithNumberContacts(1);
    // private static final Resume RESUME_2 = UtilResumes.fillWithNumberContacts(2);
    // private static final Resume RESUME_3 = UtilResumes.fillWithNumberContacts(3);
    // private static final Resume RESUME_4 = UtilResumes.fillWithNumberContacts(4);
    // private static final Resume RESUME_5 = UtilResumes.fillWithNumberContacts(5);
    // private static final Resume RESUME_6 = UtilResumes.fillWithNumberContacts(6);

    // private static final Resume RESUME_1 = UtilResumes.fillWithAllButOrganization(1);
    // private static final Resume RESUME_2 = UtilResumes.fillWithAllButOrganization(2);
    // private static final Resume RESUME_3 = UtilResumes.fillWithAllButOrganization(3);
    // private static final Resume RESUME_4 = UtilResumes.fillWithAllButOrganization(4);
    // private static final Resume RESUME_5 = UtilResumes.fillWithAllButOrganization(5);
    // private static final Resume RESUME_6 = UtilResumes.fillWithAllButOrganization(6);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void populateStorageForTesting() {
        storage.clear();

        /*
         * We put resumes in the storage in an "unsorted" order (6, 5, 4, 3, 2, 1)
         * that is exactly the opposite to that of a sorted storage (1, 2, 3, 4, 5, 6).
         * Besides, in order to test sorting by 'uuid' when 'fullName' is the same,
         * we set the 'fullName' field of the resumes RESUME_4, RESUME_5, and RESUME_6
         * to the same value of "Name-N Surname-N".
         */
        RESUME_6.setFullName("Name-N Surname-N");
        storage.save(RESUME_6);

        RESUME_5.setFullName("Name-N Surname-N");
        storage.save(RESUME_5);

        RESUME_4.setFullName("Name-N Surname-N");
        storage.save(RESUME_4);

        storage.save(RESUME_3);

        storage.save(RESUME_2);

        storage.save(RESUME_1);
    }

    @Test
    public void size() {
        assertSize(6);
    }

    @Test
    public void get() {
        assertGet(RESUME_6); // try getting a random resume out of the six previously saved
        /* The commented-out code below is for debugging */
        // assertEquals(RESUME_6.getFullName(), storage.get(RESUME_6.getUuid()).getFullName());
        // assertEquals(RESUME_6.getUuid(), storage.get(RESUME_6.getUuid()).getUuid());
        // assertEquals(RESUME_6.getContacts(), storage.get(RESUME_6.getUuid()).getContacts());
        // assertEquals(RESUME_6.getSections(), storage.get(RESUME_6.getUuid()).getSections());
        // assertEquals(RESUME_6.getSections().get(SectionType.OBJECTIVE), storage.get(RESUME_6.getUuid()).getSections().get(SectionType.OBJECTIVE));
        // assertEquals(RESUME_6.getSections().get(SectionType.PERSONAL), storage.get(RESUME_6.getUuid()).getSections().get(SectionType.PERSONAL));
        // assertEquals(RESUME_6.getSections().get(SectionType.ACHIEVEMENTS), storage.get(RESUME_6.getUuid()).getSections().get(SectionType.ACHIEVEMENTS));
        // assertEquals(RESUME_6.getSections().get(SectionType.QUALIFICATIONS), storage.get(RESUME_6.getUuid()).getSections().get(SectionType.QUALIFICATIONS));
        // assertEquals(RESUME_6.getSections().get(SectionType.EXPERIENCE), storage.get(RESUME_6.getUuid()).getSections().get(SectionType.EXPERIENCE));
        // assertEquals(RESUME_6.getSections().get(SectionType.EDUCATION), storage.get(RESUME_6.getUuid()).getSections().get(SectionType.EDUCATION));
        // assertEquals(
        //         ((OrganizationSection) RESUME_6.getSections().get(EXPERIENCE)).getOrganizations()
        //                 .get(0),
        //         ((OrganizationSection) storage.get(RESUME_6.getUuid()).getSections().get(EXPERIENCE)).getOrganizations()
        //                 .get(0)
        // );
    }

    @Test
    public void getNonexistent() {
        assertThrows(ItemNotPresentException.class, () -> storage.get("foo"));
    }

    @Test
    public void getAllSorted() {
        Resume[] resumeArray = {RESUME_1, RESUME_2, RESUME_3, RESUME_4, RESUME_5, RESUME_6};
        List<Resume> expectedList = Arrays.asList(resumeArray);
        assertIterableEquals(expectedList, storage.getAllSorted());
        /* The commented-out code below is for debugging */
        // assertEquals(resumeArray[0], storage.getAllSorted().get(0));
        // assertEquals(resumeArray[0].getFullName(), storage.getAllSorted().get(0).getFullName());
        // assertEquals(resumeArray[0].getUuid(), storage.getAllSorted().get(0).getUuid());
        // assertEquals(resumeArray[0].getContacts(), storage.getAllSorted().get(0).getContacts());
        // assertEquals(resumeArray[0].getSections(), storage.getAllSorted().get(0).getSections());
    }

    @Test
    public void save() {
        clear();
        storage.save(RESUME_1);
        assertSize(1);
        assertGet(RESUME_1);
    }

    @Test
    public void saveExistent() {
        assertThrows(ItemAlreadyPresentException.class, () -> storage.save(RESUME_1));
    }

    @Test
    public void update() {
        Resume newResume = UtilResumes.fillWithNumber(1);
        newResume.setFullName("Newname-1 Newsurname-1");
        storage.update(newResume);
        assertEquals(newResume, storage.get(newResume.getUuid()));
    }

    @Test
    public void updateNonexistent() {
        assertThrows(ItemNotPresentException.class, () -> storage.update(UtilResumes.fillOut("foo")));
    }

    @Test
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(5);
        assertThrows(ItemNotPresentException.class, () -> storage.get(RESUME_1.getUuid()));
    }

    @Test
    public void deleteNonexistent() {
        assertThrows(ItemNotPresentException.class, () -> storage.delete("foo"));
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    protected void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}

