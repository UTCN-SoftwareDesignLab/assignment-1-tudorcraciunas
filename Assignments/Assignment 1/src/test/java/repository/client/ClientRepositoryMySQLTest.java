package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepositoryMySQL clientRepository;


    @BeforeClass
    public static void setupClass(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);

    }

    @After
    public void clear(){
        removeAll();
    }

    @Test
    public void findAll() {
        save();
        Assert.assertEquals(1, clientRepository.findAll().size());
    }

    @Test
    public void findById() throws EntityNotFoundException {
        save();
        Client client = clientRepository.findAll().get(0);
        Client clientById = clientRepository.findById(client.getId());
        Assert.assertEquals(client.getName(), clientById.getName());
    }

    @Test
    public void findByName() {
        save();
        Client client = clientRepository.findAll().get(0);
        Notification<Client> notification = clientRepository.findByName(client.getName());

        Assert.assertFalse(notification.hasErrors());
    }

    @Test
    public void findByCardNumber() {
        save();

        Client client = clientRepository.findAll().get(0);
        Client clientByCard = clientRepository.findByCardNumber(client.getCardNumber());

        assertEquals(client.getName(), clientByCard.getName());

    }

    @Test
    public void update() {
        save();

        Client client = clientRepository.findAll().get(0);

        assertTrue(clientRepository.update(client));
    }

    @Test
    public void save() {
        removeAll();

        Client client = new ClientBuilder()
                .setName("IonAgarbiceanu")
                .setAddress("Cluj,Turzii")
                .setCardNumber("4356423464453")
                .setNumericalCode("1995984882954")
                .build();
        clientRepository.save(client);

        Assert.assertEquals(1, clientRepository.findAll().size());

    }

    @Test
    public void removeAll() {

        clientRepository.removeAll();
        Assert.assertEquals(0, clientRepository.findAll().size());
    }

    @Test
    public void removeById() {
        save();
        clientRepository.removeById(1L);
        Assert.assertEquals(0, clientRepository.findAll().size());

    }
}