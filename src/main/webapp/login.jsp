<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Login - Bee House Management</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background: #f0f8ff;
      }
      .login-container {
        max-width: 350px;
        margin: 80px auto;
        background: #fff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }
      h2 {
        text-align: center;
        color: #2c3e50;
      }
      .form-group {
        margin-bottom: 15px;
      }
      label {
        display: block;
        margin-bottom: 5px;
      }
      input[type="text"],
      input[type="password"] {
        width: 100%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
      }
      .btn {
        width: 100%;
        padding: 10px;
        background: #3498db;
        color: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }
      .btn:hover {
        background: #2980b9;
      }
      .error {
        color: #e74c3c;
        text-align: center;
        margin-bottom: 10px;
      }
      .success {
        color: #2ecc71;
        text-align: center;
        margin-bottom: 10px;
      }
      .register-link {
        text-align: center;
        margin-top: 10px;
      }
    </style>
  </head>
  <body>
    <div class="login-container">
      <h2>Login</h2>
      <form action="login" method="post">
        <input type="hidden" name="action" value="login" />
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" id="username" name="username" required />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" required />
        </div>
        <button type="submit" class="btn">Login</button>
      </form>
      <c:if test="${not empty error}">
        <div class="error">${error}</div>
      </c:if>
      <c:if test="${not empty success}">
        <div class="success">${success}</div>
      </c:if>
      <div class="register-link">
        Don't have an account? <a href="register.jsp">Register here</a>
      </div>
    </div>
  </body>
</html>
