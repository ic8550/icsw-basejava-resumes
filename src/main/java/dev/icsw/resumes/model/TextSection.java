package dev.icsw.resumes.model;


import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private String text;
    public static final TextSection EMPTY = new TextSection("");

    public TextSection() {
    }

    public TextSection(String text) {
        Objects.requireNonNull("TextSection's text content cannot be 'null'");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TextSection that = (TextSection) obj;
        return text.equals(that.text);

    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
