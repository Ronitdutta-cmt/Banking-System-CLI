package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transanction;
import domain.Type;

import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.ValidationException;

import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;
import util.Validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class BankServiceImpl implements BankService  {

    public final AccountRepository accountRepository = new AccountRepository() ;
    public final TransactionRepository transactionRepository = new TransactionRepository() ;
    public final CustomerRepository customerRepository = new CustomerRepository() ;


    // validations using lambda functions .
    private final Validation<String> validateName = name -> {
            if(name == null || name.isBlank() ) throw new ValidationException("Name") ;
    };
    private final Validation<String> validateEmail = email -> {
        if(email == null || !email.contains("@") ) throw new ValidationException("Email") ;
    };
    private final Validation<String> validateType = type -> {
        if(type == null || !(type.equalsIgnoreCase("SAVINGS") || type.equalsIgnoreCase("CURRENT")) ) throw new ValidationException("Type must be saving or current ") ;
    };
    private final Validation<Double> validateAmountPositive = amount -> {
        if(amount == null ||  amount < 0   ) throw new ValidationException("Please enter a valid amount.. ") ;
    };


    @Override
    public String openAccount(String name, String email, String accountType) {
        validateName.validate(name);
        validateEmail.validate(email);
        validateType.validate(accountType);



        //customer id is from random string generator
        String customerId = UUID.randomUUID().toString() ;

        //create customer
        Customer c = new Customer(email,  customerId ,name) ;
        customerRepository.save(c) ;


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

        validateAmountPositive.validate(amount) ;

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(()-> new AccountNotFoundException("Account not found" + accountNumber)) ; //new thing .

        account.setBalance(Double.valueOf(account.getBalance() + amount)) ;  // balance updated .

        Transanction transanction = new Transanction(account.getAccountNumber() , amount , UUID.randomUUID().toString() , note , LocalDateTime.now(), Type.DEPOSIT ) ;
        transactionRepository.add(transaction)  ;
    }

    @Override
    public void Withdraw(String accountNumber, Double amount, String note ) {
        //logic same as deposit , but 1st check if amt is sufficient to withdraw .

        validateAmountPositive.validate(amount) ;

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(()-> new AccountNotFoundException("Account not found" + accountNumber)) ; //new thing .

        if(account.getBalance().compareTo(amount) < 0 )
            throw new InsufficientFundsException("Insufficient Balance") ;


        account.setBalance(Double.valueOf(account.getBalance() - amount)) ;  // balance updated .

        Transanction transanction = new Transanction(account.getAccountNumber() , amount , UUID.randomUUID().toString() , note , LocalDateTime.now(), Type.WITHDRAW ) ;
        transactionRepository.add(transaction)  ;


    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {

        validateAmountPositive.validate(amount) ;

        validateAmountPositive.validate(amount);
        if (fromAcc.equals(toAcc))
            throw new ValidationException("Cannot transfer to your own account");

        // fetching accounts
        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + fromAcc));
        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + toAcc));

        //check : from mei sufficient bal h ya nhi .
        if (from.getBalance().compareTo(amount) < 0)
            throw new InsufficientFundsException("Insufficient Balance");


        //suff bal present -> make transaction .
        from.setBalance(from.getBalance() - amount); // from acc : bal deduct
        to.setBalance(to.getBalance() + amount);     // to m : bal add .

        transactionRepository.add(new Transaction(from.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note,
                LocalDateTime.now(), Type.TRANSFER_OUT));

        transactionRepository.add(new Transaction(to.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note,
                LocalDateTime.now(), Type.TRANSFER_IN));
    }

    @Override
    public List<Transanction> getStatement(String account) {

      // See again.
        return  transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transanction::getTimestamp))
                .collect(Collectors.toList())  ;
    }

    @Override
    public List<Account> searchAccountsByCustomerName(String q)
    {
        String query = (q == null) ? "" : q.toLowerCase() ;
        List<Account> result = new ArrayList<>() ;

        for(Customer c : customerRepository.findAll()){
            if(c.getName().toLowerCase().contains(q))
                result.addAll(accountRepository.findByCustomerId(c.getId()) ) ;
        }
    result.sort(Comparator.comparing(Account::getAccountNumber)) ;
    return  result ;
    }


    // shortcut : select the logic > rc > refactor > new method .
    private String getAccountNumber() {
        //-------> acc no gen logic
        int size = accountRepository.findAll().size() + 1 ;
        return String.format("AC%06d",size ) ; // "AC" k badd 6 digits honge .
    }



}
