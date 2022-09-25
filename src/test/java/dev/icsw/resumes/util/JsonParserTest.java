package dev.icsw.resumes.util;

import dev.icsw.resumes.model.AbstractSection;
import dev.icsw.resumes.model.Resume;
import dev.icsw.resumes.model.TextSection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonParserTest {
    private final Resume RESUME_7 = UtilResumes.fillWithNumber(7);

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME_7);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_7, resume);
    }

    @Test
    public void write() throws Exception {
        AbstractSection originalSection = new TextSection("Objective1");
        String json = JsonParser.write(originalSection, AbstractSection.class);
        System.out.println(json);
        AbstractSection convertedToThenRestoredFromJson = JsonParser.read(json, AbstractSection.class);
        assertEquals(originalSection, convertedToThenRestoredFromJson);
    }
}