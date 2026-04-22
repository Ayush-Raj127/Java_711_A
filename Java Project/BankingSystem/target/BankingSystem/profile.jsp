<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.banking.model.Account" %>
<%
    Account acc = (Account) session.getAttribute("account");
    if (acc == null) { response.sendRedirect("index.jsp"); return; }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile - Banking System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <h2>🏦 Banking System</h2>
    <div>
        <a href="dashboard.jsp">Dashboard</a>
        <a href="transaction">Transactions</a>
        <a href="statement">Statement</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </div>
</div>

<div class="container">

    <% if (request.getAttribute("success") != null) { %>
    <div class="alert alert-success"><%= request.getAttribute("success") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <!-- Account Info -->
    <div class="card">
        <h3>Account Information</h3>
        <table style="width:auto;">
            <tr><td style="color:#777;padding:6px 15px 6px 0;">Account Number</td><td><strong><%= acc.getAccountNumber() %></strong></td></tr>
            <tr><td style="color:#777;padding:6px 15px 6px 0;">Account Type</td><td><%= acc.getAccountType() %></td></tr>
            <tr><td style="color:#777;padding:6px 15px 6px 0;">Email</td><td><%= acc.getEmail() %></td></tr>
            <tr><td style="color:#777;padding:6px 15px 6px 0;">Balance</td><td><strong>₹<%= String.format("%.2f", acc.getBalance()) %></strong></td></tr>
            <tr><td style="color:#777;padding:6px 15px 6px 0;">Member Since</td><td><%= acc.getCreatedDate() != null ? acc.getCreatedDate().substring(0,10) : "N/A" %></td></tr>
        </table>
    </div>

    <!-- Update Profile -->
    <div class="card">
        <h3>Update Profile</h3>
        <form action="profile" method="post" style="max-width:420px;">
            <input type="hidden" name="action" value="updateProfile">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="holderName" value="<%= acc.getHolderName() %>" required>
            </div>
            <div class="form-group">
                <label>Phone Number</label>
                <input type="text" name="phone" value="<%= acc.getPhone() != null ? acc.getPhone() : "" %>">
            </div>
            <button type="submit" class="btn btn-success" style="width:auto;">Update Profile</button>
        </form>
    </div>

    <!-- Change Password -->
    <div class="card">
        <h3>Change Password</h3>
        <form action="profile" method="post" style="max-width:420px;">
            <input type="hidden" name="action" value="changePassword">
            <div class="form-group">
                <label>Current Password</label>
                <input type="password" name="oldPassword" required>
            </div>
            <div class="form-group">
                <label>New Password</label>
                <input type="password" name="newPassword" required>
            </div>
            <div class="form-group">
                <label>Confirm New Password</label>
                <input type="password" name="confirmPassword" required>
            </div>
            <button type="submit" class="btn btn-danger" style="width:auto;">Change Password</button>
        </form>
    </div>

</div>
</body>
</html>
