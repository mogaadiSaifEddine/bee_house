<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title><fmt:message key="site.edit.title"/></title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/style.css"
    />
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
        <fmt:message key="site.edit.title" var="title" />
        <fmt:formatMessage key="${title}" value="${site.name}" />
      </h1>

      <form
        action="${pageContext.request.contextPath}/sites/edit"
        method="POST"
        class="form"
      >
        <input type="hidden" name="id" value="${site.id}" />

        <div class="form-group">
          <label for="name"><fmt:message key="site.name" /> *</label>
          <input
            type="text"
            id="name"
            name="name"
            value="${site.name}"
            required
            class="form-control"
          />
        </div>

        <div class="form-group">
          <label for="description"
            ><fmt:message key="site.description"
          /></label>
          <textarea
            id="description"
            name="description"
            class="form-control"
            rows="3"
          >
${site.description}</textarea
          >
        </div>

        <div class="form-group">
          <label><fmt:message key="site.location" /> *</label>
          <div id="map"></div>
          <p class="help-text"><fmt:message key="site.location.help" /></p>
        </div>

        <div class="form-group">
          <label for="latitude"><fmt:message key="site.latitude" /> *</label>
          <input
            type="number"
            id="latitude"
            name="latitude"
            value="${site.latitude}"
            step="0.000001"
            required
            class="form-control"
          />
        </div>

        <div class="form-group">
          <label for="longitude"><fmt:message key="site.longitude" /> *</label>
          <input
            type="number"
            id="longitude"
            name="longitude"
            value="${site.longitude}"
            step="0.000001"
            required
            class="form-control"
          />
        </div>

        <div class="form-group">
          <label for="altitude"
            ><fmt:message key="site.altitude" /> (m) *</label
          >
          <input
            type="number"
            id="altitude"
            name="altitude"
            value="${site.altitude}"
            step="0.1"
            required
            class="form-control"
          />
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            <fmt:message key="site.edit.submit" />
          </button>
          <a
            href="${pageContext.request.contextPath}/sites?farmId=${site.farm.id}"
            class="btn btn-secondary"
          >
            <fmt:message key="common.cancel" />
          </a>
        </div>
      </form>
    </div>

    <script>
      const GOOGLE_MAPS_API_KEY = "${googleMapsApiKey}";
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
  </body>
</html>
