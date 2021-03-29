package service.account;

import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {

    Notification<Account> createAccount(Account account);
    Notification<Account> updateAccount(Account account);

    List<Account> viewAll();
    Account viewById(Long id) throws EntityNotFoundException;

    Account viewByNumber(String number) throws EntityNotFoundException;

    void removeAll();

    Notification<String> transferMoney(String sourceAccount, String destinationAccount, Float amount) throws EntityNotFoundException;

    Notification<Boolean> processBill(Long accountId, float amount);

    void removeById(Long id);

    String generateNumber();
}
