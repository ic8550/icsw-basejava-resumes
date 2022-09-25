<%@ page import="dev.icsw.resumes.model.ContactType" %>
<%@ page import="dev.icsw.resumes.AppConfig" %>
<%@ page import="dev.icsw.resumes.model.Resume" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/resume-list-styles.css">
    <title>Список всех резюме</title>
  </head>
  <body>
    <div class="themes">
      <div class="theme-title">Тема</div>
      <div class="theme-selector">
        <form action="" method="GET">
          <select name="theme" onchange="this.form.submit()">
            <option value="light" ${theme == null || theme == 'light' ? 'selected' : ''}>Светлая</option>
            <option value="dark" ${theme == 'dark' ? 'selected' : ''}>Темная</option>
            <option value="purple" ${theme == 'purple' ? 'selected' : ''}>Фиолетовая</option>
          </select>
        </form>
      </div>
    </div>
    <jsp:include page="fragments/header.jsp"/>
    <div class="scrollable-panel">
      <div class="table-wrapper">
        <div class="add-resume">
          <a class="no-underline-anchor" href="resumes?action=add&theme=${theme}">
            <img src="img/${theme}/add-person.svg" alt="">
          </a>
          <a class="text-anchor" href="resumes?action=add&theme=${theme}">
            <p class="add-resume-title">Добавить резюме</p>
          </a>
        </div>
        <div class="resumes-list">
          <table>
            <tr class="t-header">
              <th class="name-column">Имя</th>
              <th class="info-column">Контакты</th>
              <th class="img-column">Редактировать</th>
              <th class="img-column">Удалить</th>
            </tr>
            <c:forEach var="resumeObj" items="${resumeList}">

              <%-- <jsp:useBean id="resumeObj" type="dev.icsw.resumes.model.Resume" scope="page"/> --%>
              <%
                Resume resumeObj = (Resume) pageContext.getAttribute("resumeObj");
                if (resumeObj == null) {
                  throw new ServletException("bean resumeObj not found within scope");
                }
              %>

              <tr class="t-body">
                <td class="name-column">
                  <a class="contact-link"
                     href="resumes?uuid=${resumeObj.uuid}&action=view&theme=${theme}">${resumeObj.fullName}</a>
                </td>
                <td class="info-column">
                  <%=ContactType.EMAIL.toLink(resumeObj.getContact(ContactType.EMAIL))%>
                </td>
                <td class="img-column">
                  <a class="no-underline-anchor" href="resumes?uuid=${resumeObj.uuid}&action=edit&theme=${theme}">
                    <img src="img/${theme}/edit.svg" alt="">
                  </a>
                </td>
                <td class="img-column">
                  <a class="no-underline-anchor" href="resumes?uuid=${resumeObj.uuid}&action=delete&theme=${theme}">
                    <img src="img/${theme}/remove.svg" alt="">
                  </a>
                </td>
              </tr>

            </c:forEach>
          </table>
        </div>
      </div>
    </div>
    <jsp:include page="fragments/footer.jsp"/>
  </body>
</html>
