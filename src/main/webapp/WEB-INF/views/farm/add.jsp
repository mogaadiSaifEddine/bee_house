<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%><%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${messages['farm.add']}</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.2.3/css/bootstrap.min.css"
    />
  </head>
  <body>
    <div class="container mt-4">
      <h1>${messages['farm.add']}</h1>

      <form
        action="${pageContext.request.contextPath}/farms/add"
        method="post"
        class="mt-4"
      >
        <div class="mb-3">
          <label for="name" class="form-label">${messages['farm.name']}</label>
          <input
            type="text"
            class="form-control"
            id="name"
            name="name"
            required
          />
        </div>

        <div class="mb-3">
          <label for="description" class="form-label"
            >${messages['farm.description']}</label
          >
          <textarea
            class="form-control"
            id="description"
            name="description"
            rows="3"
          ></textarea>
        </div>

        <div class="mb-3">
          <button type="submit" class="btn btn-primary">
            ${messages['farm.save']}
          </button>
          <a
            href="${pageContext.request.contextPath}/farms"
            class="btn btn-secondary"
          >
            ${messages['farm.cancel']}
          </a>
        </div>
      </form>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.6.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.2.3/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
