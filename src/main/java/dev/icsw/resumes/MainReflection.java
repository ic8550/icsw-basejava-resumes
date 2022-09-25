package dev.icsw.resumes;

import dev.icsw.resumes.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws SecurityException, ReflectiveOperationException {
        Resume resume = new Resume("111", "Name-1");
        Class<Resume> resumeClass = Resume.class;
        Field uuidField = resumeClass.getDeclaredFields()[0];
        uuidField.setAccessible(true);
        System.out.println("Field name: " + uuidField.getName());
        System.out.println("Field value: " + uuidField.get(resume));
        uuidField.set(resume, "new_uuid");
        System.out.println("New field value: " + resume);
        // TODO : invoke resume.toString via reflection
        uuidField.set(resume, "yet_another_new_uuid");
        Method toStringMethod = resumeClass.getDeclaredMethod("toString");
        String resumeUuid = (String) toStringMethod.invoke(resume);
        System.out.println("Yet another new field value: " + resumeUuid);
    }
}
