package com.banking.servlet;

import com.banking.dao.AccountDAO;
import com.banking.model.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            res.sendRedirect("index.jsp");
            return;
        }
        Account account = (Account) session.getAttribute("account");
        String action = req.getParameter("action");

        try {
            AccountDAO dao = new AccountDAO();
            if ("updateProfile".equals(action)) {
                String name = req.getParameter("holderName");
                String phone = req.getParameter("phone");
                boolean success = dao.updateProfile(account.getAccountNumber(), name, phone);
                if (success) {
                    account.setHolderName(name);
                    account.setPhone(phone);
                    session.setAttribute("account", account);
                    req.setAttribute("success", "Profile updated successfully.");
                } else {
                    req.setAttribute("error", "Profile update failed.");
                }
            } else if ("changePassword".equals(action)) {
                String oldPass = req.getParameter("oldPassword");
                String newPass = req.getParameter("newPassword");
                String confirmPass = req.getParameter("confirmPassword");
                if (!newPass.equals(confirmPass)) {
                    req.setAttribute("error", "New passwords do not match.");
                } else {
                    boolean success = dao.changePassword(account.getAccountNumber(), oldPass, newPass);
                    req.setAttribute(success ? "success" : "error",
                            success ? "Password changed successfully." : "Old password is incorrect.");
                }
            }
        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
        }
        req.getRequestDispatcher("profile.jsp").forward(req, res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            res.sendRedirect("index.jsp");
            return;
        }
        req.getRequestDispatcher("profile.jsp").forward(req, res);
    }
}
