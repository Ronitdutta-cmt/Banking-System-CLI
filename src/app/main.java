package app;
import service.BankService;
import service.impl.BankServiceImpl;

import java.util.* ;
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in) ;
        BankService bankService = new BankServiceImpl() ; // obj of bankservice , but assigned obj of implementation .

        boolean running = true ;
        System.out.println("Welcome to Console Bank ");
        while (running) {
            System.out.println("""
                        1) Open Account
                        2) Deposit
                        3) Withdraw
                        4) Transfer
                        5) Account Statement
                        6) List Accounts
                        7) Search Accounts by Customer Name
                        0) Exit
                    """);
            System.out.print("CHOOSE: ");
            String choice = sc.nextLine().trim();
            System.out.println("CHOICE: " + choice);

        // passing scanner object into it.
            switch (choice) {
                case "1" -> openAccount(sc, bankService);
                case "2" -> deposit(sc );
                case "3" -> withdraw(sc);
                case "4" -> transfer(sc, bankService);
                case "5" -> statement(sc ,bankService);
                case "6" -> listAccounts(sc , bankService);
                case "7" -> searchAccounts(sc , bankService) ;
                case "0" -> running = false;
            }

        }

        }

        public static void openAccount(Scanner sc  , BankService bankService){
            System.out.println("Customer name: ");
            String name = sc.nextLine().trim();

            System.out.println("Customer email: ");
            String email = sc.nextLine().trim();

            System.out.println("Account Type (SAVINGS/CURRENT): ");
            String type = sc.nextLine().trim();

            System.out.println("Initial deposit (optional, blank for 0): ");
            String amountStr = sc.nextLine().trim();

            if (amountStr.isBlank()) amountStr = "0";
            Double initial = Double.valueOf(amountStr); //type conversion into double .

            String accountNumber = BankService.openAccount(name, email , type)  ; // err : non static
            if(initial > 0){
                bankService.deposit(accountNumber , initial ,  "initial deposit") ;  // err : not made yet .
            }
            System.out.println("Account Opend" + accountNumber );
        }

        public static void deposit(Scanner sc){
            System.out.println("Account number: ");
            String accountNumber = sc.nextLine().trim();
            System.out.println("Amount: ");
            Double amount = Double.valueOf(sc.nextLine().trim());

            BankService.deposit(accountNumber, amount, "Deposit");
            System.out.println("Deposited");
        }

        public static void withdraw(Scanner sc){
            System.out.println("Account number: ");
            String accountNumber = sc.nextLine().trim();
            System.out.println("Amount: ");
            Double amount = Double.valueOf(sc.nextLine().trim());

            BankService.Withdraw(accountNumber, amount, "Withdrawl");
            System.out.println("Withdrawn");
            // till here same as deposit .


        }
        public static void transfer(Scanner sc , BankService bankService){

            System.out.println("From account: ");
            String from = sc.nextLine().trim();
            System.out.println("To account : ");
            String to = sc.nextLine().trim();
            System.out.println("Amount : ");


            Double amount = Double.valueOf(sc.nextLine().trim());

            BankService.transfer(from,to,  amount, "Transfer");
        }


        public static void statement(Scanner sc , BankService bankService){
            System.out.println("Account number: ");
            String account = scanner.nextLine().trim();

            bankService.getStatement(account).forEach(t -> {
                System.out.println(t.getTimestamp() + " | " + t.getType() + " | " + t.getAmount() + " | " + t.getNote());
            });

        }


        public static void listAccounts(Scanner sc , BankService bankService ){
            bankService.listAccounts().forEach(a->{
                System.out.println(a.getAccountNumber()  + "|" + a.getAccountType() +"|" + a.getBalance() );
            });

        }
        public static void searchAccounts(Scanner sc , BankService bankService){
            System.out.println("Customer name contains: ");
            String q = sc.nextLine().trim();
            bankService.searchAccountsByCustomerName(q).forEach( account ->
                    System.out.println(account.getAccountNumber() + " | " + account.getAccountType() + " | " + account.getBalance())
            );
        }

}
