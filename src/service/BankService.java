package service;

import domain.Account;
import domain.Transanction;

import java.util.List;
import java.util.Map;

public interface BankService {




    String openAccount(String name, String email, String accountType) ;
    List<Account> listAccounts() ;

    void deposit(String accountNumber, Double amount, String deposit) ;
    void Withdraw(String accountNumber, Double amount, String withdrawl) ;
    void transfer(String from, String to, Double amount, String transfer);

    List<Transanction> getStatement(String account) ;


    List<Account> searchAccountsByCustomerName(String q);

}
