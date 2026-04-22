<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.banking.model.Account" %>
<%
    Account acc = (Account) session.getAttribute("account");
    if (acc == null) { response.sendRedirect("index.jsp"); return; }
    String activeTab = request.getParameter("tab");
    if (activeTab == null) activeTab = "deposit";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transactions - Banking System</title>
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

    <!-- Balance Info -->
    <div class="card" style="padding:15px 25px;">
        <span style="font-size:13px;color:#666;">Current Balance &nbsp;|&nbsp; <strong style="color:#1a237e; font-size:18px;">₹<%= String.format("%.2f", acc.getBalance()) %></strong></span>
    </div>

    <% if (request.getAttribute("msg") != null) { %>
    <div class="alert alert-success"><%= request.getAttribute("msg") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <!-- Tab Bar -->
    <div class="tab-bar">
        <a href="transaction?tab=deposit"  class="<%= "deposit".equals(activeTab)  ? "active" : "" %>">💰 Deposit</a>
        <a href="transaction?tab=withdraw" class="<%= "withdraw".equals(activeTab) ? "active" : "" %>">💸 Withdraw</a>
        <a href="transaction?tab=transfer" class="<%= "transfer".equals(activeTab) ? "active" : "" %>">🔄 Transfer</a>
    </div>

    <!-- Deposit Tab -->
    <% if ("deposit".equals(activeTab)) { %>
    <div class="card">
        <h3>Deposit Money</h3>
        <form action="transaction" method="post" style="max-width:400px;">
            <input type="hidden" name="type" value="deposit">
            <div class="form-group">
                <label>Amount (₹)</label>
                <input type="number" name="amount" placeholder="Enter amount" min="1" step="0.01" required>
            </div>
            <button type="submit" class="btn btn-success" style="width:auto;">Deposit</button>
        </form>
    </div>

    <!-- Withdraw Tab -->
    <% } else if ("withdraw".equals(activeTab)) { %>
    <div class="card">
        <h3>Withdraw Money</h3>
        <form action="transaction" method="post" style="max-width:400px;">
            <input type="hidden" name="type" value="withdraw">
            <div class="form-group">
                <label>Amount (₹)</label>
                <input type="number" name="amount" placeholder="Enter amount" min="1" step="0.01" required>
            </div>
            <button type="submit" class="btn btn-danger" style="width:auto;">Withdraw</button>
        </form>
    </div>

    <!-- Transfer Tab -->
    <% } else if ("transfer".equals(activeTab)) { %>
    <div class="card">
        <h3>Transfer Money</h3>
        <form action="transaction" method="post" style="max-width:400px;">
            <input type="hidden" name="type" value="transfer">
            <div class="form-group">
                <label>Recipient Account Number</label>
                <input type="text" name="toAccount" placeholder="e.g. ACC1234567" required>
            </div>
            <div class="form-group">
                <label>Amount (₹)</label>
                <input type="number" name="amount" placeholder="Enter amount" min="1" step="0.01" required>
            </div>
            <button type="submit" class="btn btn-secondary" style="width:auto; background:#1565c0;">Transfer</button>
        </form>
    </div>
    <% } %>

</div>
</body>
</html>
