import java.util.Collection;

class Account{
    private String accountNumber;
    private double balance;

    Account(String accountNumber, double balance){
        this.accountNumber = accountNumber;
        if(balance < 0)
            throw new InsufficientFundsException(balance);
        else
        this.balance = balance;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) throws InvalidTransactionException{
        if(amount < 0)
            throw new InvalidTransactionException(amount);
        else
            balance += amount;
    }
    public void withdraw(double amount) throws InvalidTransactionException, InsufficientFundsException{
        if(amount < 0)
            throw new InvalidTransactionException(amount);
        else if(amount > balance)
            throw new InsufficientFundsException(balance, amount);
        else
            balance -= amount;
    }
//HOCAM BURAYA BAKAR MISINIZ BILMIYORUM DA BU AI VALLA ISIMIZI ELIMIZDEN ALACAK KENDI YAZIYOR BEN METHOD ADINI YAZMADAN BOLUMU YANLIS MI SECTIK ACABA (ben tab'a basıyorum yani ben de yazmış sayılırım ayrıca kontrol de ediyorum sizin istediğinizi yazmış mı diye xd)
    @Override 
    public String toString(){
        return "Account number: " + accountNumber + "\nBalance: " + balance;
    }
}
class Customer{
    private String name;
    private Collection<Account> accounts;

    Customer(String name){
        this.name = name;
    }
    public Account getAccount(String accountNumber) throws AccountNotFoundException{
        for(Account account : accounts)
            if(account.getAccountNumber().equals(accountNumber))
                return account;
        throw new AccountNotFoundException(accountNumber);
    }
    public void addAccount(Account account) throws AccountAlreadyExistException, AccountNotFoundException{
        try{
            getAccount(account.getAccountNumber());
            throw new AccountAlreadyExistException(account.getAccountNumber());
        }catch(AccountNotFoundException e){
            accounts.add(account);
        }finally{
            System.out.println("Added account: " + account.getAccountNumber() + " with " + account.getBalance());
        }
    }
    public void removeAccount(String accountNumber){
        Account account = getAccount(accountNumber);
        accounts.remove(account);
    }
    public void transfer(String fromAccount, String toAccount, double amount) throws InvalidTransactionException{
        try{
            getAccount(fromAccount).withdraw(amount);
            getAccount(toAccount).deposit(amount);
        }catch(InvalidTransactionException e){
            throw new InvalidTransactionException(e, "Cannot transfer funds from account" + fromAccount + " to account " + toAccount);
        }   
    }
    @Override
    public String toString(){
        String str = "Customer name: " + name + "\nAccounts:\n";
        for(Account account : accounts)
            str += account.toString() + "\n";
        return str;
    }
}
class InsufficientFundsException extends RuntimeException{
    InsufficientFundsException(double balance){
        super("Wrong balance: " + balance);
    }
    InsufficientFundsException(double balance, double amount){
        super("Required amount is: " + amount + " but only " + balance + " remaining");
    }
}
class AccountAlreadyExistException extends RuntimeException{
    AccountAlreadyExistException(String accountNumber){
        super("Account number " + accountNumber + " already exist");
    }
}
class AccountNotFoundException extends RuntimeException{
    AccountNotFoundException(String accountNumber){
        super("Account number " + accountNumber + " already exist");
    }
    
}
class InvalidTransactionException extends Exception{
    InvalidTransactionException(double amount){
        super("Invalid amount: " + amount);
    }
    InvalidTransactionException(Exception e, String message){
        super(message + ":\n\t" + e.getMessage());
    }
}
