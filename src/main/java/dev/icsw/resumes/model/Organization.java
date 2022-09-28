package dev.icsw.resumes.model;

import dev.icsw.resumes.util.UtilDates;
import dev.icsw.resumes.util.XmlLocalDateAdapter;

// import jakarta.xml.bind.annotation.XmlAccessType;
// import jakarta.xml.bind.annotation.XmlAccessorType;
// import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Organization EMPTY = new Organization("", "", Activity.EMPTY);
    private Link homePage;
    private List<Activity> activities = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, Activity... activities) {
        this(new Link(name, url), Arrays.asList(activities));
    }

    public Organization(Link homePage, List<Activity> activities) {
        this.homePage = homePage;
        this.activities = (activities == null) ? new ArrayList<>() : activities;
    }

    public Organization(String name, String url, List<Activity> activities) {
        this(new Link(name, url), activities);
    }


    public Link getHomePage() {
        return homePage;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Organization that = (Organization) obj;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(activities, that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, activities);
    }

    @Override
    public String toString() {
        return "organization(" + homePage + ", " + activities + ')';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Activity implements Serializable {

        private static final long serialVersionUID = 1L;
        public static final Activity EMPTY = new Activity();
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Activity() {
        }

        public Activity(int startYear, Month startMonth, String title, String description) {
            this(UtilDates.of(startYear, startMonth, 1), UtilDates.NOW, title, description);
        }

        public Activity(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            int lastDayOfMonth;
            switch (endMonth.getValue()) {
                case 2:
                    lastDayOfMonth = 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    lastDayOfMonth = 30;
                    break;
                default:
                    lastDayOfMonth = 31;
            }

            this.startDate = UtilDates.of(startYear, startMonth, 1);
            this.endDate = UtilDates.of(endYear, endMonth, lastDayOfMonth);
            this.title = title;
            this.description = (description == null) ? "" : description;

        }

        public Activity(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "activity's startDate cannot be null");
            Objects.requireNonNull(endDate, "activity's endDate cannot be null");
            Objects.requireNonNull(title, "activity's title cannot be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = (description == null) ? "" : description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Activity activity = (Activity) obj;
            return (
                    Objects.equals(startDate, activity.startDate)
                            && Objects.equals(endDate, activity.endDate)
                            && Objects.equals(title, activity.title)
                            && Objects.equals(description, activity.description)
            );
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return "Activity(" + startDate.toString() + ',' + endDate.toString() + ',' + title + ',' + description + ')';
        }
    }
}
