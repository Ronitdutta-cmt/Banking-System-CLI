package service;

import domain.Account;
import domain.Customer;
import domain.Transanction;
import java.util.List;

public interface BankService {

    String openAccount(String name, String email, String accountType);

    List<Account> listAccounts();

    void deposit(String accountNumber, Double amount, String note);

    void Withdraw(String accountNumber, Double amount, String note);

    void transfer(String fromAcc, String toAcc, Double amount, String note);

    List<Transanction> getStatement(String account);

    List<Account> searchAccountsByCustomerName(String query);

    Customer getCustomerById(String id);
}