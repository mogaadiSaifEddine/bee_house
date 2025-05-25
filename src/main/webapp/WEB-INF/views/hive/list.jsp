<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="hive.list.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <div class="container">
        <h1>
            <c:if test="${not empty site}">
                <fmt:message key="hive.list.title.site" var="title"/>
                <fmt:formatMessage key="${title}" value="${site.name}"/>
            </c:if>
            <c:if test="${empty site}">
                <fmt:message key="hive.list.title.all"/>
            </c:if>
        </h1>

        <div class="actions">
            <c:if test="${not empty site}">
                <a href="${pageContext.request.contextPath}/hives/add?siteId=${site.id}" class="btn btn-primary">
                    <fmt:message key="hive.add.button"/>
                </a>
                <a href="${pageContext.request.contextPath}/sites/view?id=${site.id}" class="btn btn-secondary">
                    <fmt:message key="common.back.to.site"/>
                </a>
            </c:if>
        </div>

        <div class="hive-list">
            <c:if test="${empty hives}">
                <p class="no-data"><fmt:message key="hive.list.empty"/></p>
            </c:if>
            
            <c:if test="${not empty hives}">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th><fmt:message key="hive.name"/></th>
                            <th><fmt:message key="hive.type"/></th>
                            <th><fmt:message key="hive.status"/></th>
                            <th><fmt:message key="hive.health.status"/></th>
                            <th><fmt:message key="hive.productivity.status"/></th>
                            <th><fmt:message key="hive.colony.size"/></th>
                            <th><fmt:message key="hive.honey.quantity"/></th>
                            <th><fmt:message key="common.actions"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${hives}" var="hive">
                            <tr>
                                <td>${hive.name}</td>
                                <td>${hive.type}</td>
                                <td>${hive.status}</td>
                                <td>${hive.healthStatus}</td>
                                <td>${hive.productivityStatus}</td>
                                <td>${hive.colonySize}</td>
                                <td>${hive.honeyQuantity} kg</td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/hives/view?id=${hive.id}" 
                                       class="btn btn-info" title="<fmt:message key="hive.view.details"/>">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/hives/edit?id=${hive.id}" 
                                       class="btn btn-warning" title="<fmt:message key="common.edit"/>">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/hives/delete?id=${hive.id}" 
                                       class="btn btn-danger" 
                                       onclick="return confirm('<fmt:message key="hive.delete.confirm"/>')"
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