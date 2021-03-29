package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.client.ClientRepositoryMySQL;
import repository.client.ClientRepositoryMySQLTest;

import java.awt.image.PackedColorModel;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AccountRepositoryMySQLTest {

    private static AccountRepositoryMySQL accountRepository;

    private ClientRepositoryMySQLTest clientRepositoryTest;
    private static ClientRepositoryMySQL clientRepository;
    @BeforeClass
    public static void setupClass(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);

    }

    @After
    public void clear(){
        removeAll();
    }


    @Test
    public void findAll() {
        save();
        Assert.assertEquals(1, accountRepository.findAll().size());
    }

    @Test
    public void findById() throws EntityNotFoundException {
        save();
        Account account = accountRepository.findAll().get(0);
        Account accountById = accountRepository.findById(account.getId());

        Assert.assertEquals(account.getNumber(), accountById.getNumber());

    }

    @Test
    public void findByNumber() throws EntityNotFoundException {
        save();
        Account account = accountRepository.findAll().get(0);
        Account accountByNumber = accountRepository.findByNumber(account.getNumber());
        Assert.assertEquals(account.getNumber(), accountByNumber.getNumber());

    }

    @Test
    public void save() {
        removeAll();
        clientRepository.removeAll();

        Client client = new ClientBuilder()
                .setName("IonAgarbiceanu")
                .setAddress("Cluj,Turzii")
                .setCardNumber("4356423464453")
                .setNumericalCode("1995984882954")
                .build();
        clientRepository.save(client);


        Account account = new AccountBuilder()
                .setBalance(12000f)
                .setCreationDate(LocalDate.now())
                .setNumber("12345654321")
                .setType("debit")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();

        accountRepository.save(account);

        Assert.assertEquals(1, accountRepository.findAll().size());


    }

    @Test
    public void removeAll() {
        accountRepository.removeAll();
        Assert.assertEquals(0, accountRepository.findAll().size());
    }

    @Test
    public void removeById() {
        save();
        accountRepository.removeById(1L);
        Assert.assertEquals(0, accountRepository.findAll().size());
    }

    @Test
    public void update() {
        save();

        Account account = accountRepository.findAll().get(0);
        account.setBalance(69420f);

        Assert.assertTrue(accountRepository.update(account));

    }


}