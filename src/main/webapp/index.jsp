<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bee House Management</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background: #f0f8ff;
      }
      .container {
        max-width: 800px;
        margin: 0 auto;
        background: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }
      h1 {
        color: #2c3e50;
        text-align: center;
        margin-bottom: 30px;
      }
      .welcome-text {
        text-align: center;
        color: #34495e;
        margin-bottom: 30px;
      }
      .menu {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 20px;
        margin-top: 30px;
      }
      .menu-item {
        background: #3498db;
        color: white;
        padding: 15px;
        text-align: center;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s;
      }
      .menu-item:hover {
        background: #2980b9;
      }
      .footer {
        text-align: center;
        margin-top: 40px;
        color: #7f8c8d;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h1>Bee House Management System</h1>

      <div class="welcome-text">
        <p>Welcome to your Bee House Management System!</p>
        <p>
          Manage your beehives, track your honey production, and monitor your
          bee colonies all in one place.
        </p>
      </div>

      <div class="menu">
        <a href="${pageContext.request.contextPath}/farms" class="menu-item">
          <h3>Farms</h3>
          <p>Manage your bee farms</p>
        </a>

        <a href="${pageContext.request.contextPath}/sites" class="menu-item">
          <h3>Sites</h3>
          <p>View and manage apiary sites</p>
        </a>

        <a href="${pageContext.request.contextPath}/hives" class="menu-item">
          <h3>Hives</h3>
          <p>Track your beehives</p>
        </a>

        <a href="${pageContext.request.contextPath}/visits" class="menu-item">
          <h3>Visits</h3>
          <p>Record hive inspections</p>
        </a>
      </div>

      <div class="footer">
        <p>© 2024 Bee House Management System</p>
        <p>Version 1.0</p>
      </div>
    </div>
  </body>
</html>
