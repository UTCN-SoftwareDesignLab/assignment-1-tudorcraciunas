package service.account;

import database.DBConnectionFactory;
import launcher.ComponentFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.client.ClientRepositoryMySQLTest;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.client.ClientServiceMySQLTest;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.*;

public class AccountServiceMySQLTest {

    private static AccountRepositoryMySQL accountRepository;
    private static AccountServiceMySQL accountService;
    private ClientRepositoryMySQLTest clientRepositoryTest;
    private static ClientRepositoryMySQL clientRepository;
    private static ClientServiceMySQLTest clientServiceMySQLTest;
    private static ClientServiceMySQL clientService;

    @BeforeClass
    public static void setupClass(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
        accountService = new AccountServiceMySQL(accountRepository);
        clientServiceMySQLTest = new ClientServiceMySQLTest();
        clientService = new ClientServiceMySQL(clientRepository);


    }

    public Account buildAccount(){
        Client client = new ClientBuilder()
                .setNumericalCode(clientService.generateNumericalCode())
                .setCardNumber(clientService.generateCardNumber())
                .setAddress("Frunzisului12")
                .setName("Georgiana Badiu")
                .build();
        clientService.createClient(client);

        Account account = new AccountBuilder()
                .setType("debit")
                .setNumber(accountService.generateNumber())
                .setCreationDate(LocalDate.now())
                .setBalance(5431f)
                .setClientId(client.getId())
                .build();

        return account;
    }

    @Before
    public void clean(){
        accountService.removeAll();
    }

    @Test
    public void createAccount() {
        System.out.println(accountService.createAccount(buildAccount()).getFormattedErrors());
        System.out.println(accountService.createAccount(buildAccount()).getFormattedErrors());

        assertEquals(2, accountService.viewAll().size());
    }

    @Test
    public void updateAccount() {
        createAccount();
        Account account = accountService.viewAll().get(0);
        account.setBalance(1000f);
        accountService.updateAccount(account);
        Float f = 1000f;
        assertEquals(f,accountService.viewAll().get(0).getBalance());
    }

    @Test
    public void viewAll() {
        createAccount();
        assertEquals(2, accountService.viewAll().size());
    }

    @Test
    public void viewById() throws EntityNotFoundException {
        createAccount();
        Account account = accountService.viewAll().get(0);
        Account account1 = accountService.viewById(account.getId());
        assertEquals(account.getNumber(), account1.getNumber() );
    }

    @Test
    public void viewByNumber() throws EntityNotFoundException {
        createAccount();
        Account account = accountService.viewAll().get(0);
        Account account1 = accountService.viewByNumber(account.getNumber());
        assertEquals(account.getNumber(), account1.getNumber() );
    }

    @Test
    public void removeAll() {
        createAccount();
        accountService.removeAll();
        assertEquals(0, accountService.viewAll().size());
    }

    @Test
    public void transferMoney() throws EntityNotFoundException {
        createAccount();
        Account account1 = accountService.viewAll().get(0);
        Account account2 = accountService.viewAll().get(1);

        assertFalse(accountService.transferMoney(account1.getNumber(), account2.getNumber(), 100f).hasErrors());


    }

    @Test
    public void processBill() {

    }

    @Test
    public void removeById() {
        createAccount();

        accountService.removeById(1L);

        assertEquals(1, accountService.viewAll().size());
    }
}