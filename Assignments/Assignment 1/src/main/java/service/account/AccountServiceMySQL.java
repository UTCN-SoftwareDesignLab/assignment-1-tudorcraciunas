package service.account;

import model.Account;
import model.validation.AccountValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;

import java.util.List;
import java.util.Random;

public class AccountServiceMySQL implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Notification<Account> createAccount(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        Notification<Account> accountNotification = new Notification<>();

        if(accountValidator.validate()){
            accountNotification.setResult(account);
            accountRepository.save(account);
        }
        else{
            for(String error : accountValidator.getErrors()) {
                accountNotification.addError(error);
            }
        }
        return accountNotification;
    }

    @Override
    public Notification<Account> updateAccount(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        Notification<Account> accountNotification = new Notification<>();

        if(accountValidator.validate()){
            accountNotification.setResult(account);
            accountRepository.update(account);
        }
        else{
            for(String error : accountValidator.getErrors()){
                accountNotification.addError(error);
            }
        }
        return accountNotification;
    }

    @Override
    public List<Account> viewAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account viewById(Long id) throws EntityNotFoundException {
        return accountRepository.findById(id);
    }

    @Override
    public Account viewByNumber(String number) throws EntityNotFoundException {
        return accountRepository.findByNumber(number);
    }


    @Override
    public void removeAll() {
        accountRepository.removeAll();
    }

    @Override
    public Notification<String> transferMoney(String sourceAccount, String destinationAccount, Float amount) throws EntityNotFoundException {
        Account source = accountRepository.findByNumber(sourceAccount);
        Account destination = accountRepository.findByNumber(destinationAccount);

        Float sourceBalance = source.getBalance();
        source.setBalance(sourceBalance - amount);
        accountRepository.update(source);
        Float destinationBalance = destination.getBalance();
        destination.setBalance(destinationBalance + amount);
        accountRepository.update(destination);

        Notification<String> transfer = new Notification<>();
        transfer.setResult("Transferred " + amount + "$ from " + sourceAccount+ " to " + destinationAccount);

        return transfer;
    }

    @Override
    public Notification<Boolean> processBill(Long accountId, float amount) {
        return null;
    }

    @Override
    public void removeById(Long id) {
        accountRepository.removeById(id);
    }

    @Override
    public String generateNumber(){
        Random r = new Random();
        StringBuilder n = new StringBuilder();
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(" ");
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(" ");
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        return n.toString();
    }

}
