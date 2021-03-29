package service.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.*;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ClientServiceMySQLTest {

    private static ClientService clientService;

    @BeforeClass
    public static void setupClass() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientService = new ClientServiceMySQL(new ClientRepositoryMySQL(connection));

    }

    @Before
    public void clean(){
       removeAll();
    }

    @Test
    public void createClient() {
        Client client = new ClientBuilder()
                .setNumericalCode(clientService.generateNumericalCode())
                .setCardNumber(clientService.generateCardNumber())
                .setAddress("Frunzisului12")
                .setName("Georgiana Badiu")
                .build();

        System.out.println(clientService.createClient(client).getResult());

        Assert.assertEquals(1, clientService.viewAll().size());
    }

    @Test
    public void updateClient() throws EntityNotFoundException {
        createClient();
        Client client = clientService.viewAll().get(0);
        client.setAddress("Otherwise");
        clientService.updateClient(client);
        assertEquals("Otherwise", clientService.viewById(1L).getAddress());
    }

    @Test
    public void viewAll() {
        createClient();
        assertEquals(1,clientService.viewAll().size());

    }

    @Test
    public void viewById() throws EntityNotFoundException {
        createClient();
        Client client = clientService.viewAll().get(0);
        Client clientById = clientService.viewById(client.getId());

        assertEquals(client.getName(), clientById.getName());
    }

    @Test
    public void viewByName() throws EntityNotFoundException {
        createClient();
        Client client = clientService.viewAll().get(0);
        Client clientByName = clientService.viewByName(client.getName()).getResult();

        assertEquals(client.getName(), clientByName.getName());
    }

    @Test
    public void removeAll() {
        clientService.removeAll();
        assertEquals(0, clientService.viewAll().size());
    }

    @Test
    public void removeById() {
        createClient();
        clientService.removeById(1L);
        assertEquals(0, clientService.viewAll().size());
    }

    @Test
    public void generateCardNumber() {
        assertEquals("SX 123 123".length(), clientService.generateCardNumber().length());

    }

    @Test
    public void generateNumericalCode() {
        assertEquals("1234 1231 1234".length(), clientService.generateNumericalCode().length());
    }
}