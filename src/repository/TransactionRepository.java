package repository;

import domain.Transanction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository {
    private  final Map<String , List<Transanction>> txByAccount = new HashMap<>() ;  // (transactionId , list of transactions ) .

    public void add(Transanction transanction){
        // 1st para -> agr present h , ishke corresponding transaction will be returned > jaisi hi transaction return ho rha h,  we are adding a new txn to it.
        txByAccount.computeIfAbsent(transanction.getAccountNumber() , k-> new ArrayList<>() ) .add(transanction) ;

    }
}
