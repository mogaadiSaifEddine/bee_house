<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${messages['app.title']}</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/style.css"
    />
  </head>
  <body>
    <div class="container">
      <h1>${messages['app.title']}</h1>
      <p>${messages['app.welcome']}</p>

      <div class="actions">
        <a
          href="${pageContext.request.contextPath}/farms"
          class="btn btn-primary"
        >
          ${messages['nav.farms']}
        </a>
        <a
          href="${pageContext.request.contextPath}/sites"
          class="btn btn-secondary"
        >
          ${messages['nav.sites']}
        </a>
        <a href="${pageContext.request.contextPath}/hives" class="btn btn-info">
          ${messages['nav.hives']}
        </a>
      </div>
    </div>
  </body>
</html>
