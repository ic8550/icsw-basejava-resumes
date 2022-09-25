package dev.icsw.resumes.model;

public enum SectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENTS("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String translation;

    SectionType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
