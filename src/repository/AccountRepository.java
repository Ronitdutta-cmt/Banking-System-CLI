package repository;

import database.DBConnection;
import domain.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {

    public void save(Account account) {

        String sql =
                "INSERT INTO accounts(account_number, customer_id, balance, account_type) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getCustomerId());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getAccountType());

            ps.executeUpdate();

            System.out.println("Account inserted into database!"); // temporary : for debugging . 

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> findAll() {

        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT * FROM accounts";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                accounts.add(
                        new Account(
                                rs.getString("account_number"),
                                rs.getString("customer_id"),
                                rs.getDouble("balance"),
                                rs.getString("account_type")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public Optional<Account> findByNumber(String accountNumber) {

        String sql =
                "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Account account = new Account(
                        rs.getString("account_number"),
                        rs.getString("customer_id"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );

                return Optional.of(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Account> findByCustomerId(String customerId) {

        List<Account> accounts = new ArrayList<>();

        String sql =
                "SELECT * FROM accounts WHERE customer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                accounts.add(
                        new Account(
                                rs.getString("account_number"),
                                rs.getString("customer_id"),
                                rs.getDouble("balance"),
                                rs.getString("account_type")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }


    public void update(Account account) {

    String sql =
            "UPDATE accounts SET balance = ?, account_type = ? WHERE account_number = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDouble(1, account.getBalance());
        ps.setString(2, account.getAccountType());
        ps.setString(3, account.getAccountNumber());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}