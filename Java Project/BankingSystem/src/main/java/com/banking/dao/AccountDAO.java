package com.banking.dao;

import com.banking.model.Account;
import com.banking.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountDAO {

    private String generateAccountNumber() throws SQLException {
        String accNo;
        do {
            int num = 1000000 + new Random().nextInt(9000000);
            accNo = "ACC" + num;
        } while (accountExists(accNo));
        return accNo;
    }

    private boolean accountExists(String accNo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM accounts WHERE account_number = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accNo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }


    public boolean registerAccount(Account account) throws SQLException {
        String accNo = generateAccountNumber();
        account.setAccountNumber(accNo);
        String sql = "INSERT INTO accounts (account_number, holder_name, email, phone, password, balance, account_type) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getHolderName());
            ps.setString(3, account.getEmail());
            ps.setString(4, account.getPhone());
            ps.setString(5, account.getPassword());
            ps.setDouble(6, account.getBalance());
            ps.setString(7, account.getAccountType());
            return ps.executeUpdate() > 0;
        }
    }

    // Login
    public Account login(String accNo, String password) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accNo);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapAccount(rs);
            }
        }
        return null;
    }

    public Account getAccount(String accNo) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapAccount(rs);
        }
        return null;
    }

    public boolean deposit(String accNo, double amount) throws SQLException {
        Connection con = DBConnection.getConnection();
        con.setAutoCommit(false);
        try {
            String updateSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement ps = con.prepareStatement(updateSql);
            ps.setDouble(1, amount);
            ps.setString(2, accNo);
            ps.executeUpdate();

            double newBalance = getBalance(con, accNo);
            insertTransaction(con, accNo, "DEPOSIT", amount, newBalance, "Deposit");

            con.commit();
            return true;
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }

    public boolean withdraw(String accNo, double amount) throws SQLException {
        Connection con = DBConnection.getConnection();
        con.setAutoCommit(false);
        try {
            double currentBalance = getBalance(con, accNo);
            if (currentBalance < amount) return false;

            String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement ps = con.prepareStatement(updateSql);
            ps.setDouble(1, amount);
            ps.setString(2, accNo);
            ps.executeUpdate();

            double newBalance = getBalance(con, accNo);
            insertTransaction(con, accNo, "WITHDRAWAL", amount, newBalance, "Withdrawal");

            con.commit();
            return true;
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }

    public boolean transfer(String fromAccNo, String toAccNo, double amount) throws SQLException {
        Connection con = DBConnection.getConnection();
        con.setAutoCommit(false);
        try {
            double fromBalance = getBalance(con, fromAccNo);
            if (fromBalance < amount) return false;

            Account toAcc = getAccount(toAccNo);
            if (toAcc == null) return false;

            // Debit sender
            String debitSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement ps1 = con.prepareStatement(debitSql);
            ps1.setDouble(1, amount);
            ps1.setString(2, fromAccNo);
            ps1.executeUpdate();

            // Credit receiver
            String creditSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement ps2 = con.prepareStatement(creditSql);
            ps2.setDouble(1, amount);
            ps2.setString(2, toAccNo);
            ps2.executeUpdate();

            insertTransaction(con, fromAccNo, "TRANSFER_OUT", amount, getBalance(con, fromAccNo), "Transfer to " + toAccNo);
            insertTransaction(con, toAccNo, "TRANSFER_IN", amount, getBalance(con, toAccNo), "Transfer from " + fromAccNo);

            con.commit();
            return true;
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }

    public List<Transaction> getTransactions(String accNo) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC LIMIT 20";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accNo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setAccountNumber(rs.getString("account_number"));
                t.setTransactionType(rs.getString("transaction_type"));
                t.setAmount(rs.getDouble("amount"));
                t.setBalanceAfter(rs.getDouble("balance_after"));
                t.setDescription(rs.getString("description"));
                t.setTransactionDate(rs.getTimestamp("transaction_date").toString());
                list.add(t);
            }
        }
        return list;
    }

    public boolean updateProfile(String accNo, String holderName, String phone) throws SQLException {
        String sql = "UPDATE accounts SET holder_name = ?, phone = ? WHERE account_number = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, holderName);
            ps.setString(2, phone);
            ps.setString(3, accNo);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean changePassword(String accNo, String oldPass, String newPass) throws SQLException {
        String sql = "UPDATE accounts SET password = ? WHERE account_number = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPass);
            ps.setString(2, accNo);
            ps.setString(3, oldPass);
            return ps.executeUpdate() > 0;
        }
    }

    private double getBalance(Connection con, String accNo) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?");
        ps.setString(1, accNo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getDouble("balance");
        return 0;
    }

    private void insertTransaction(Connection con, String accNo, String type, double amount, double balanceAfter, String desc) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount, balance_after, description) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, accNo);
        ps.setString(2, type);
        ps.setDouble(3, amount);
        ps.setDouble(4, balanceAfter);
        ps.setString(5, desc);
        ps.executeUpdate();
    }

    private Account mapAccount(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setId(rs.getInt("id"));
        a.setAccountNumber(rs.getString("account_number"));
        a.setHolderName(rs.getString("holder_name"));
        a.setEmail(rs.getString("email"));
        a.setPhone(rs.getString("phone"));
        a.setPassword(rs.getString("password"));
        a.setBalance(rs.getDouble("balance"));
        a.setAccountType(rs.getString("account_type"));
        a.setCreatedDate(rs.getTimestamp("created_date").toString());
        return a;
    }
}