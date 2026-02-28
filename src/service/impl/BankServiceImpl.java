package service.impl;

import domain.Account;
import domain.Transanction;
import domain.Type;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService  {

    public final AccountRepository accountRepository = new AccountRepository() ;
    public final TransactionRepository transactionRepository = new TransactionRepository() ;


    @Override
    public String openAccount(String name, String email, String accountType) {

        //customer id is from random string generator
        String customeId = UUID.randomUUID().toString() ;

        String accountNumber = getAccountNumber();


        Account account = new Account(accountNumber ,accountType , (double)0 , customeId) ;
        accountRepository.save(account); ;

        return  accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return  accountRepository.findAll().stream().collect(Collectors.toList()) ;
        //stream() -> used to process the collections without using for , if , direct filter.
    }


    // errs in this method ( check later with chatGPT ) .
    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(()-> new RuntimeException("Account not found" + accountNumber)) ; //new thing .

        account.setBalance(Double.valueOf(account.getBalance() + amount)) ;  // balance updated .

        Transanction transanction = new Transanction(account.getAccountNumber() , amount , UUID.randomUUID().toString() , note , LocalDateTime.now(), Type.DEPOSIT ) ;
        transactionRepository.add(transaction)  ;
    }


    // shortcut : select the logic > rc > refactor > new method .
    private String getAccountNumber() {
        //-------> acc no gen logic
        int size = accountRepository.findAll().size() + 1 ;
        return String.format("AC%06d",size ) ; // "AC" k badd 6 digits honge .
    }


}
