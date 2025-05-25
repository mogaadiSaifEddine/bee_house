<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="site.add.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <style>
        #map {
            height: 400px;
            width: 100%;
            margin-bottom: 20px;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            <c:if test="${not empty farm}">
                <fmt:message key="site.add.title.farm" var="title"/>
                <fmt:formatMessage key="${title}" value="${farm.name}"/>
            </c:if>
            <c:if test="${empty farm}">
                <fmt:message key="site.add.title"/>
            </c:if>
        </h1>

        <form action="${pageContext.request.contextPath}/sites/add" method="POST" class="form">
            <c:if test="${not empty farm}">
                <input type="hidden" name="farmId" value="${farm.id}">
            </c:if>

            <div class="form-group">
                <label for="name"><fmt:message key="site.name"/> *</label>
                <input type="text" id="name" name="name" required class="form-control">
            </div>

            <div class="form-group">
                <label for="description"><fmt:message key="site.description"/></label>
                <textarea id="description" name="description" class="form-control" rows="3"></textarea>
            </div>

            <div class="form-group">
                <label><fmt:message key="site.location"/> *</label>
                <div id="map"></div>
                <p class="help-text"><fmt:message key="site.location.help"/></p>
            </div>

            <div class="form-group">
                <label for="latitude"><fmt:message key="site.latitude"/> *</label>
                <input type="number" id="latitude" name="latitude" step="0.000001" required class="form-control">
            </div>

            <div class="form-group">
                <label for="longitude"><fmt:message key="site.longitude"/> *</label>
                <input type="number" id="longitude" name="longitude" step="0.000001" required class="form-control">
            </div>

            <div class="form-group">
                <label for="altitude"><fmt:message key="site.altitude"/> (m) *</label>
                <input type="number" id="altitude" name="altitude" step="0.1" required class="form-control">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <fmt:message key="site.add.submit"/>
                </button>
                <a href="${pageContext.request.contextPath}/sites?farmId=${farm.id}" class="btn btn-secondary">
                    <fmt:message key="common.cancel"/>
                </a>
            </div>
        </form>
    </div>

    <script>
        const GOOGLE_MAPS_API_KEY = '${googleMapsApiKey}';
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html> 