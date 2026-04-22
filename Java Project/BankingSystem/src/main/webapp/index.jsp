<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("account") != null) {
        response.sendRedirect("dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Banking System - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="auth-wrapper">
    <div class="auth-box">
        <h2>🏦 Banking System</h2>
        <p class="subtitle">Login to your account</p>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="login" method="post">
            <div class="form-group">
                <label>Account Number</label>
                <input type="text" name="accountNumber" placeholder="e.g. ACC1234567" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Enter password" required>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>

        <p class="text-center mt-10">
            Don't have an account? <a class="link" href="register.jsp">Register here</a>
        </p>
    </div>
</div>
</body>
</html>
