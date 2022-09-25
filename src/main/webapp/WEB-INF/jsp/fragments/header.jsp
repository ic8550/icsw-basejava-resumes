<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="header">
  <a class="no-underline-anchor" href="resumes?theme=${theme}">
    <div class="arrow-dot">
      <img src="img/left_arrow.svg" alt="">
    </div>
  </a>
  <a class="text-anchor" href="resumes?theme=${theme == null ? 'light' : theme}">
    <span class="resumes-control-title">Управление резюме</span>
  </a>
</div>
