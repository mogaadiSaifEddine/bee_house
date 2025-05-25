<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>${messages['farm.title']}</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.2.3/css/bootstrap.min.css"
    />
  </head>
  <body>
    <div class="container mt-4">
      <h1>${messages['farm.title']}</h1>

      <div class="mb-3">
        <a
          href="${pageContext.request.contextPath}/farms/add"
          class="btn btn-primary"
        >
          ${messages['farm.add']}
        </a>
      </div>

      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>${messages['farm.name']}</th>
              <th>${messages['farm.description']}</th>
              <th>${messages['farm.actions']}</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${farms}" var="farm">
              <tr>
                <td>${farm.id}</td>
                <td>${farm.name}</td>
                <td>${farm.description}</td>
                <td>
                  <a
                    href="${pageContext.request.contextPath}/farms/edit?id=${farm.id}"
                    class="btn btn-sm btn-warning"
                  >
                    ${messages['farm.edit']}
                  </a>
                  <a
                    href="${pageContext.request.contextPath}/farms/delete?id=${farm.id}"
                    class="btn btn-sm btn-danger"
                    onclick="return confirm('${messages['farm.delete.confirm']}')"
                  >
                    ${messages['farm.delete']}
                  </a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/jquery/3.6.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.2.3/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
