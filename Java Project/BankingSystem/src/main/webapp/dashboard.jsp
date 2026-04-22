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
    <title>Dashboard - Banking System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<!-- Navbar -->
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
    <div class="card">
        <h3>Welcome, <%= acc.getHolderName() %> 👋</h3>
        <p style="color:#666; font-size:13px;">Account No: <strong><%= acc.getAccountNumber() %></strong> &nbsp;|&nbsp; Type: <strong><%= acc.getAccountType() %></strong></p>
    </div>

    <!-- Stats Grid -->
    <div class="dashboard-grid">
        <div class="stat-card">
            <div class="label">CURRENT BALANCE</div>
            <div class="value">₹<%= String.format("%.2f", acc.getBalance()) %></div>
        </div>
        <div class="stat-card" style="border-left-color:#2e7d32;">
            <div class="label">ACCOUNT TYPE</div>
            <div class="value" style="color:#2e7d32;"><%= acc.getAccountType() %></div>
        </div>
        <div class="stat-card" style="border-left-color:#e65100;">
            <div class="label">EMAIL</div>
            <div class="value" style="color:#e65100; font-size:14px;"><%= acc.getEmail() %></div>
        </div>
        <div class="stat-card" style="border-left-color:#6a1b9a;">
            <div class="label">MEMBER SINCE</div>
            <div class="value" style="color:#6a1b9a; font-size:15px;"><%= acc.getCreatedDate() != null ? acc.getCreatedDate().substring(0,10) : "N/A" %></div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="card">
        <h3>Quick Actions</h3>
        <div style="display:flex; gap:12px; flex-wrap:wrap; margin-top:10px;">
            <a href="transaction?tab=deposit" class="btn btn-success">💰 Deposit</a>
            <a href="transaction?tab=withdraw" class="btn btn-danger">💸 Withdraw</a>
            <a href="transaction?tab=transfer" class="btn btn-secondary">🔄 Transfer</a>
            <a href="statement" class="btn btn-primary" style="width:auto;">📄 View Statement</a>
        </div>
    </div>
</div>
</body>
</html>
