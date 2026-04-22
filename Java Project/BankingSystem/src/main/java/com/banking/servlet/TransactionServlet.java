package com.banking.servlet;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class TransactionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            res.sendRedirect("index.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        String accNo = account.getAccountNumber();
        String type = req.getParameter("type");
        double amount = 0;
        try {
            amount = Double.parseDouble(req.getParameter("amount"));
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid amount.");
            req.getRequestDispatcher("transaction.jsp").forward(req, res);
            return;
        }

        if (amount <= 0) {
            req.setAttribute("error", "Amount must be greater than 0.");
            req.getRequestDispatcher("transaction.jsp").forward(req, res);
            return;
        }

        try {
            AccountDAO dao = new AccountDAO();
            boolean success = false;

            if ("deposit".equals(type)) {
                success = dao.deposit(accNo, amount);
                req.setAttribute("msg", success ? "Deposit of ₹" + amount + " successful!" : "Deposit failed.");
            } else if ("withdraw".equals(type)) {
                success = dao.withdraw(accNo, amount);
                req.setAttribute("msg", success ? "Withdrawal of ₹" + amount + " successful!" : "Insufficient balance.");
            } else if ("transfer".equals(type)) {
                String toAccNo = req.getParameter("toAccount");
                if (toAccNo == null || toAccNo.trim().isEmpty()) {
                    req.setAttribute("error", "Please enter recipient account number.");
                    req.getRequestDispatcher("transaction.jsp").forward(req, res);
                    return;
                }
                if (toAccNo.equals(accNo)) {
                    req.setAttribute("error", "Cannot transfer to own account.");
                    req.getRequestDispatcher("transaction.jsp").forward(req, res);
                    return;
                }
                success = dao.transfer(accNo, toAccNo, amount);
                req.setAttribute("msg", success ? "Transfer of ₹" + amount + " to " + toAccNo + " successful!" : "Transfer failed. Check balance or recipient account.");
            }

            // Refresh account in session
            Account updated = dao.getAccount(accNo);
            session.setAttribute("account", updated);

        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
        }
        req.getRequestDispatcher("transaction.jsp").forward(req, res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            res.sendRedirect("index.jsp");
            return;
        }
        req.getRequestDispatcher("transaction.jsp").forward(req, res);
    }
}
