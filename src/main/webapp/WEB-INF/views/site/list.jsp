<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="site.list.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <div class="container">
        <h1>
            <c:if test="${not empty farm}">
                <fmt:message key="site.list.title.farm" var="title"/>
                <fmt:formatMessage key="${title}" value="${farm.name}"/>
            </c:if>
            <c:if test="${empty farm}">
                <fmt:message key="site.list.title.all"/>
            </c:if>
        </h1>

        <div class="actions">
            <c:if test="${not empty farm}">
                <a href="${pageContext.request.contextPath}/sites/add?farmId=${farm.id}" class="btn btn-primary">
                    <fmt:message key="site.add.button"/>
                </a>
                <a href="${pageContext.request.contextPath}/farms/view?id=${farm.id}" class="btn btn-secondary">
                    <fmt:message key="common.back.to.farm"/>
                </a>
            </c:if>
        </div>

        <div class="site-list">
            <c:if test="${empty sites}">
                <p class="no-data"><fmt:message key="site.list.empty"/></p>
            </c:if>
            
            <c:if test="${not empty sites}">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th><fmt:message key="site.name"/></th>
                            <th><fmt:message key="site.description"/></th>
                            <th><fmt:message key="site.location"/></th>
                            <th><fmt:message key="site.hive.count"/></th>
                            <th><fmt:message key="common.actions"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sites}" var="site">
                            <tr>
                                <td>${site.name}</td>
                                <td>${site.description}</td>
                                <td>
                                    <fmt:formatNumber value="${site.latitude}" pattern="0.000000"/>,
                                    <fmt:formatNumber value="${site.longitude}" pattern="0.000000"/>
                                    (${site.altitude}m)
                                </td>
                                <td>${site.hives.size()}</td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/hives?siteId=${site.id}" 
                                       class="btn btn-info" title="<fmt:message key="site.view.hives"/>">
                                        <i class="fas fa-bee"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/sites/edit?id=${site.id}" 
                                       class="btn btn-warning" title="<fmt:message key="common.edit"/>">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/sites/delete?id=${site.id}" 
                                       class="btn btn-danger" 
                                       onclick="return confirm('<fmt:message key="site.delete.confirm"/>')"
                                       title="<fmt:message key="common.delete"/>">
                                        <i class="fas fa-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html> 