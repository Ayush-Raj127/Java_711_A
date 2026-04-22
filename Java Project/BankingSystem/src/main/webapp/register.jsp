<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Banking System - Register</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="auth-wrapper">
    <div class="auth-box" style="max-width:500px;">
        <h2>🏦 Create Account</h2>
        <p class="subtitle">Open a new bank account</p>

        <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="register" method="post">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="holderName" placeholder="Enter full name" required>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" placeholder="Enter email" required>
            </div>
            <div class="form-group">
                <label>Phone Number</label>
                <input type="text" name="phone" placeholder="Enter 10-digit phone">
            </div>
            <div class="form-group">
                <label>Account Type</label>
                <select name="accountType">
                    <option value="SAVINGS">Savings Account</option>
                    <option value="CURRENT">Current Account</option>
                </select>
            </div>
            <div class="form-group">
                <label>Initial Deposit (₹)</label>
                <input type="number" name="initialDeposit" value="0" min="0" step="0.01">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="Create password" required>
            </div>
            <button type="submit" class="btn btn-primary">Create Account</button>
        </form>

        <p class="text-center mt-10">
            Already have an account? <a class="link" href="index.jsp">Login here</a>
        </p>
    </div>
</div>
</body>
</html>
