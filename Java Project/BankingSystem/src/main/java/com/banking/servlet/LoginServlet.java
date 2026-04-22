package com.banking.servlet;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String accNo = req.getParameter("accountNumber");
        String password = req.getParameter("password");

        try {
            AccountDAO dao = new AccountDAO();
            Account account = dao.login(accNo, password);
            if (account != null) {
                HttpSession session = req.getSession();
                session.setAttribute("account", account);
                res.sendRedirect("dashboard.jsp");
            } else {
                req.setAttribute("error", "Invalid account number or password.");
                req.getRequestDispatcher("index.jsp").forward(req, res);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("index.jsp").forward(req, res);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.sendRedirect("index.jsp");
    }
}