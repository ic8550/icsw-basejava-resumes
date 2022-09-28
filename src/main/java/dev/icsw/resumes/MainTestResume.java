package dev.icsw.resumes;

import dev.icsw.resumes.model.*;
import dev.icsw.resumes.storage.SqlStorage;
import dev.icsw.resumes.storage.Storage;
import dev.icsw.resumes.util.UtilDates;
import dev.icsw.resumes.util.UtilResumes;
import dev.icsw.resumes.AppConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MainTestResume {
    public static Resume resume;
    private static File storageDir;
    private static Storage storage;
    private static final String APP_PROPS_FILE = "/app.properties";

    public static void main(String[] args) {
        // resume = UtilResumes.fillOut(UUID.randomUUID().toString(), "Григорий Кислин");
        resume = UtilResumes.fillWithNumber(9);

        printResume(resume);

        //////////////////////////////////////
        // getConfig();
    }

    public static void getConfig() {
        // This code is for Heroku deployment
        try (InputStream inputStream = AppConfig.class.getResourceAsStream(APP_PROPS_FILE)) {
            Properties appProps = new Properties();
            System.out.println(inputStream);
            appProps.load(inputStream);

            System.out.println(appProps.getProperty("storage.dir"));
            System.out.println(appProps.getProperty("db.url"));
            System.out.println(appProps.getProperty("db.user"));
            System.out.println(appProps.getProperty("db.password"));

            storageDir = new File(appProps.getProperty("storage.dir"));
            storage = new SqlStorage(appProps.getProperty("db.url"), appProps.getProperty("db.user"), appProps.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + APP_PROPS_FILE);
        }
    }

    public static void printResume(Resume resume) {
        System.out.println();
        printFullName(resume.toString());
        System.out.println();
        printContacts(resume.getContacts());
        System.out.println();
        printSections(resume.getSections());
    }

    public static void printFullName(String fullName) {
        System.out.println(fullName);
    }

    public static void printContacts(Map<ContactType, String> contacts) {
        System.out.println("CONTACTS:");
        contacts.forEach((key, value) -> System.out.println("    " + key + ": " + value));
    }

    public static void printSections(Map<SectionType, AbstractSection> sections) {
        sections.forEach((key, value) -> printSection(key));
    }

    public static void printSection(SectionType sectionType) {
        switch (sectionType) {
            case OBJECTIVE: {
                System.out.println("OBJECTIVE:");
                printTextSection(resume.getSections().get(SectionType.OBJECTIVE), "    ");
                break;
            }
            case PERSONAL: {
                System.out.println("\nPERSONAL:");
                printTextSection(resume.getSections().get(SectionType.PERSONAL), "    ");
                break;
            }
            case ACHIEVEMENTS: {
                System.out.println("\nACHIEVEMENTS:");
                printListSection((ListSection) resume.getSections().get(SectionType.ACHIEVEMENTS), "    ");
                break;
            }
            case QUALIFICATIONS: {
                System.out.println("\nQUALIFICATIONS:");
                printListSection((ListSection) resume.getSections().get(SectionType.QUALIFICATIONS), "    ");
                break;
            }
            case EXPERIENCE: {
                System.out.println("\nEXPERIENCE:");
                printOrganizationSection((OrganizationSection) resume.getSections().get(SectionType.EXPERIENCE), "    ");
                break;
            }
            case EDUCATION: {
                System.out.println("\nEDUCATION:");
                printOrganizationSection((OrganizationSection) resume.getSections().get(SectionType.EDUCATION), "    ");
                break;
            }
        }
    }

    public static void printTextSection(AbstractSection textSection, String indent) {
        System.out.print(indent);
        System.out.println(((TextSection) textSection).getText());
    }

    public static void printListSection(ListSection listSection, String indent) {
        List<String> content = listSection.getContent();
        for (String s : content) {
            System.out.print(indent);
            System.out.println(s);
        }
    }

    public static void printOrganizationSection(OrganizationSection organizationSection, String indent) {
        List<Organization> organizations = organizationSection.getOrganizations();
        for (Organization organization : organizations) {
            printOrganization(organization, indent);
        }
    }

    public static void printOrganization(Organization organization, String indent) {
        String url = organization.getHomePage().getUrl();
        String name = organization.getHomePage().getName();
        System.out.print(indent + name);
        if (url != null) {
            System.out.println(" (" + url + ")");
        } else {
            System.out.println();
        }
        List<Organization.Activity> activities = organization.getActivities();
        for (Organization.Activity activity : activities) {
            printActivity(activity, indent);
        }
    }

    public static void printActivity(Organization.Activity activity, String indent) {
        String startDateFormatted = activity.getStartDate().format(DateTimeFormatter.ofPattern("MM/yyyy"));
        LocalDate endDate = activity.getEndDate();
        String endDateFormatted = (endDate == UtilDates.NOW)
                                  ? "по настоящее время"
                                  : endDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
        System.out.println(indent + indent + startDateFormatted + " - " + endDateFormatted);
        System.out.println(indent + indent + indent + activity.getTitle());
        String description = activity.getDescription();
        if (description != null) {
            System.out.println(indent + indent + indent + indent + description);
        }
    }
}

