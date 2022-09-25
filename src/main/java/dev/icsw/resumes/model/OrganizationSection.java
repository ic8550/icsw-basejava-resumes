package dev.icsw.resumes.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrganizationSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private  List<Organization> organizations;

    public OrganizationSection() {
        this.organizations = new ArrayList<Organization>();
    }

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        OrganizationSection that = (OrganizationSection) obj;

        return organizations.equals(that.organizations);

    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}
