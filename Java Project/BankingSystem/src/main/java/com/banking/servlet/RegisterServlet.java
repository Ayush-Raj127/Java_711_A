package com.banking.servlet;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String name = req.getParameter("holderName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String accType = req.getParameter("accountType");
        double initialDeposit = 0;
        try {
            initialDeposit = Double.parseDouble(req.getParameter("initialDeposit"));
        } catch (NumberFormatException e) {
            initialDeposit = 0;
        }

        Account account = new Account(null, name, email, phone, password, initialDeposit, accType);

        try {
            AccountDAO dao = new AccountDAO();
            boolean success = dao.registerAccount(account);
            if (success) {
                req.setAttribute("success", "Account created! Your Account Number: " + account.getAccountNumber());
                req.getRequestDispatcher("register.jsp").forward(req, res);
            } else {
                req.setAttribute("error", "Registration failed. Try again.");
                req.getRequestDispatcher("register.jsp").forward(req, res);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, res);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.sendRedirect("register.jsp");
    }
}
