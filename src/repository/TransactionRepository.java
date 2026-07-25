package repository;

import database.DBConnection;
import domain.Transanction;
import domain.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public void add(Transanction transaction) {

        String sql =
        "INSERT INTO transactions(id, type, account_number, amount, transaction_time, note)VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, transaction.getId());
            ps.setString(2, transaction.getType().name());
            ps.setString(3, transaction.getAccountNumber());
            ps.setDouble(4, transaction.getAmount());

            ps.setTimestamp(
                    5,
                    Timestamp.valueOf(transaction.getTimestamp())
            );

            ps.setString(6, transaction.getNote());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transanction> findByAccount(String account) {

        List<Transanction> transactions = new ArrayList<>();

        String sql =
            "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_time";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, account);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                transactions.add(
                        new Transanction(
                                rs.getString("id"),
                                Type.valueOf(rs.getString("type")),
                                rs.getString("account_number"),
                                rs.getDouble("amount"),
                                rs.getTimestamp("transaction_time").toLocalDateTime(),
                                rs.getString("note")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}