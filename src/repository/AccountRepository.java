package repository;

import domain.Account;

import java.util.*;

public class AccountRepository {
    private final Map<String , Account> accountsByNumber = new HashMap<>() ;

    public void save(Account account){
        accountsByNumber.put(account.getAccountNumber() , account) ; // (acc no , obj )
    }

    public List<Account> findAll() {
        return new ArrayList<>(accountsByNumber.values()) ;
    }

    // return account krega pr , wraped in optional (coz it can be null) .
    public Optional<Account> findByNumber(String accountNumber) {
        return  Optinal.ofNullable(accountsByNumber.get(accountNumber)) ; //didn't use normal "of" -> coz this will handle null too .
    }
}
