package com.banking.servlet;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;
import com.banking.model.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class StatementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            res.sendRedirect("index.jsp");
            return;
        }
        Account account = (Account) session.getAttribute("account");
        try {
            AccountDAO dao = new AccountDAO();
            List<Transaction> transactions = dao.getTransactions(account.getAccountNumber());
            req.setAttribute("transactions", transactions);
        } catch (Exception e) {
            req.setAttribute("error", "Error loading transactions: " + e.getMessage());
        }
        req.getRequestDispatcher("statement.jsp").forward(req, res);
    }
}
