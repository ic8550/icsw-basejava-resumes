package dev.icsw.resumes.web;

import dev.icsw.resumes.AppConfig;
import dev.icsw.resumes.model.*;
import dev.icsw.resumes.storage.Storage;
import dev.icsw.resumes.util.UtilDates;
import dev.icsw.resumes.util.UtilHtml;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResumeServlet extends HttpServlet {

    private enum THEME {
        dark, light, purple
    }

    private final Storage storage = AppConfig.getConfigInstance().getStorage();
    private final Set<String> themes = new HashSet<>(); // https://stackoverflow.com/a/4936895/548473

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        for (THEME t : THEME.values()) {
            themes.add(t.name());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        final boolean isUuidEmpty = (uuid == null || uuid.length() == 0);
        Resume r;

        if (isUuidEmpty) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (UtilHtml.isEmpty(value)) {
                r.getContacts().remove(contactType);
            } else {
                r.addContact(contactType, value);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String requestParameter = request.getParameter(sectionType.name());
            String[] requestParameterValues = request.getParameterValues(sectionType.name());
            if (UtilHtml.isEmpty(requestParameter) && requestParameterValues.length < 2) {
                r.getSections().remove(sectionType);
            } else {
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(sectionType, new TextSection(requestParameter));
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        r.addSection(sectionType, new ListSection(requestParameter.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(sectionType.name() + "url");
                        for (int i = 0; i < requestParameterValues.length; i++) {
                            String name = requestParameterValues[i];
                            if (!UtilHtml.isEmpty(name)) {
                                List<Organization.Activity> activities = new ArrayList<>();
                                String pfx = sectionType.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!UtilHtml.isEmpty(titles[j])) {
                                        activities.add(
                                                new Organization.Activity(
                                                        UtilDates.parse(startDates[j]),
                                                        UtilDates.parse(endDates[j]),
                                                        titles[j],
                                                        descriptions[j]
                                                )
                                        );
                                    }
                                }
                                organizations.add(new Organization(new Link(name, urls[i]), activities));
                            }
                        }
                        r.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                }
            }
        }
        if (isUuidEmpty) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resumes?theme=" + getTheme(request));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        request.setAttribute("theme", getTheme(request));

        if (action == null) {
            request.setAttribute("resumeList", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resumes");
                return;
            case "add":
                r = Resume.EMPTY;
                break;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    AbstractSection section = r.getSection(sectionType);
                    switch (sectionType) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENTS:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            // Turn the existing organization section into the one that is nearly
                            // the same as the original, except for:
                            // 1) it has an empty organization as a first organization of the organizations list;
                            // 2) it has an empty activity as a first activity of the activities list
                            // for each organization of the organizations list.
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> orgListWithFirstOrgEmpty = new ArrayList<>();
                            orgListWithFirstOrgEmpty.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Organization.Activity> activityListWithFirstActivityEmpty = new ArrayList<>();
                                    activityListWithFirstActivityEmpty.add(Organization.Activity.EMPTY);
                                    activityListWithFirstActivityEmpty.addAll(org.getActivities());
                                    orgListWithFirstOrgEmpty.add(new Organization(org.getHomePage(), activityListWithFirstActivityEmpty));
                                }
                            }
                            section = new OrganizationSection(orgListWithFirstOrgEmpty);
                            break;
                    }
                    r.addSection(sectionType, section);
                    break;
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request
                .getRequestDispatcher((action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"))
                .forward(request, response);
    }

    private String getTheme(HttpServletRequest request) {
        String theme = request.getParameter("theme");
        return themes.contains(theme) ? theme : THEME.light.name();
    }
}
