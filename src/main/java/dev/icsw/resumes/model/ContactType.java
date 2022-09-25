package dev.icsw.resumes.model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return getTranslation() + ": " + toLink("skype:" + value, value);
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return getTranslation() + ": " + toLink(value);
        }

        @Override
        public String toLink(String value) {
            return (value == null) ? "" : toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };

    private final String translation;

    ContactType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    protected String toHtml0(String value) {
        return translation + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, translation);
    }

    public static String toLink(String href, String anchorCaption) {
        return "<a class=\"contact-link\" href='" + href + "'>" + anchorCaption + "</a>";
    }
}

