<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.banking.model.*,java.util.*" %>
<%
    Account acc = (Account) session.getAttribute("account");
    if (acc == null) { response.sendRedirect("index.jsp"); return; }
    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Statement - Banking System</title>
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
    <div class="card">
        <h3>Account Statement</h3>
        <p style="font-size:13px;color:#666;margin-bottom:15px;">Account: <strong><%= acc.getAccountNumber() %></strong> &nbsp;|&nbsp; Last 20 transactions</p>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>

        <% if (transactions == null || transactions.isEmpty()) { %>
        <div class="alert alert-info">No transactions found for your account.</div>
        <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Description</th>
                    <th>Amount (₹)</th>
                    <th>Balance (₹)</th>
                </tr>
            </thead>
            <tbody>
            <% int i = 1; for (Transaction t : transactions) {
                String badgeClass = "badge-blue";
                if (t.getTransactionType().contains("DEPOSIT") || t.getTransactionType().contains("IN")) badgeClass = "badge-green";
                else if (t.getTransactionType().contains("WITHDRAW") || t.getTransactionType().contains("OUT")) badgeClass = "badge-red";
            %>
                <tr>
                    <td><%= i++ %></td>
                    <td><%= t.getTransactionDate().substring(0,16) %></td>
                    <td><span class="badge <%= badgeClass %>"><%= t.getTransactionType() %></span></td>
                    <td><%= t.getDescription() %></td>
                    <td><%= String.format("%.2f", t.getAmount()) %></td>
                    <td><strong><%= String.format("%.2f", t.getBalanceAfter()) %></strong></td>
                </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</div>
</body>
</html>
