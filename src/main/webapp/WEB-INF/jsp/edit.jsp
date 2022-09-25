<%@ page import="dev.icsw.resumes.util.UtilDates" %>
<%@ page import="dev.icsw.resumes.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/edit-resume-styles.css">

    <%-- <jsp:useBean id="resume" type="dev.icsw.resumes.model.Resume" scope="request"/> --%>
    <%
      Resume resume = (Resume) pageContext.getAttribute("resume", PageContext.REQUEST_SCOPE);
      if (resume == null) {
        throw new ServletException("Bean resume not found within scope");
      }
    %>

    <title>Резюме ${resume.fullName}</title>
  </head>
  <body>
    <jsp:include page="fragments/header.jsp"/>
    <form method="post" action="resumes?theme=${theme}" enctype="application/x-www-form-urlencoded">
      <input type="hidden" name="uuid" value="${resume.uuid}">
      <input type="hidden" name="theme" value="${theme}">
      <div class="scrollable-panel">
        <div class="form-wrapper">
          <div class="section">ФИО</div>
          <input class="field" type="text" name="fullName" size=55 placeholder="ФИО" value="${resume.fullName}" required>

          <div class="section">Контакты</div>

          <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <input class="field" type="text" name="${contactType.name()}" size=30 placeholder="${contactType.translation}"
                   value="${resume.getContact(contactType)}">
          </c:forEach>

          <div class="spacer"></div>

          <div class="section">Секции</div>

          <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
           <%-- <jsp:useBean id="section" type="dev.icsw.resumes.model.AbstractSection"/> --%>
            <%
              AbstractSection section = (AbstractSection) pageContext.getAttribute("section");
              if (section == null) {
                throw new ServletException("bean section not found within scope");
              }
            %>
            <div class="field-label">${sectionType.translation}</div>
            <c:choose>
              <c:when test="${sectionType=='OBJECTIVE' || sectionType=='PERSONAL'}">
                <textarea class="field" name='${sectionType}'><%=section%></textarea>
              </c:when>
              <c:when test="${sectionType=='QUALIFICATIONS' || sectionType=='ACHIEVEMENTS'}">
                <textarea class="field" name='${sectionType}'><%=String.join("\n", ((ListSection) section).getContent())%></textarea>
              </c:when>
              <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>" varStatus="counter">
                  <c:choose>
                    <c:when test="${counter.index == 0}">
                    </c:when>
                    <c:otherwise>
                      <div class="spacer"></div>
                    </c:otherwise>
                  </c:choose>

                  <input class="field" type="text" placeholder="Название" name='${sectionType}' size=100 value="${organization.homePage.name}">
                  <input class="field" type="text" placeholder="Ссылка" name='${sectionType}url' size=100 value="${organization.homePage.url}">

                  <c:forEach var="activity" items="${organization.activities}">
                    <%-- <jsp:useBean id="activity" type="dev.icsw.resumes.model.Organization.Activity"/> --%>
                    <%
                      Organization.Activity activity = (Organization.Activity) pageContext.getAttribute("activity");
                      if (activity == null) {
                        throw new ServletException("bean activity not found within scope");
                      }
                    %>

                    <div class="date-section">
                      <input class="field date" name="${sectionType}${counter.index}startDate"
                             placeholder="Начало, ММ/ГГГГ"
                             size=10
                             value="<%=UtilDates.format(activity.getStartDate())%>">
                      <input class="field date date-margin" name="${sectionType}${counter.index}endDate"
                             placeholder="Окончание, ММ/ГГГГ"
                             size=10
                             value="<%=UtilDates.format(activity.getEndDate())%>">
                    </div>

                    <input class="field" type="text" placeholder="Заголовок"
                           name='${sectionType}${counter.index}title' size=75
                           value="${activity.title}">
                    <textarea class="field" placeholder="Описание" name="${sectionType}${counter.index}description">${activity.description}</textarea>

                  </c:forEach>
                </c:forEach>
              </c:when>
            </c:choose>
          </c:forEach>

          <div class="spacer"></div>

          <div class="button-section">
            <button class="red-cancel-button" type="button" onclick="window.history.back()">Отменить</button>
            <button class="green-submit-button" type="submit">Сохранить</button>
          </div>

        </div>
      </div>
    </form>
    <jsp:include page="fragments/footer.jsp"/>
  </body>
</html>

