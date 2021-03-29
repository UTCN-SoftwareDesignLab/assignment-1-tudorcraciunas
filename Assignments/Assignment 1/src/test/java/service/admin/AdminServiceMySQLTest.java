package service.admin;

import database.DBConnectionFactory;
import model.Account;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

public class AdminServiceMySQLTest {

    private static AdminService adminService;
    private static RightsRolesRepository rightsRolesRepository;
    private static  UserRepository userRepository;
    @BeforeClass
    public static void setupClass() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        adminService = new AdminServiceMySQL(userRepository);
    }

    @Before
    public void clean(){
        deleteAll();
    }
    @Test
    public void createEmployee() {

        User user = new UserBuilder()
                .setUsername("test.user@mail.com")
                .setPassword("Test!Pass123")
                .setRole(rightsRolesRepository.findRoleByTitle("employee"))
                .build();
        User user1 = new UserBuilder()
                .setUsername("test1.altceva@mail.com")
                .setPassword("TestaltaParola!123")
                .setRole(rightsRolesRepository.findRoleByTitle("employee"))
                .build();

        adminService.createEmployee(user);
        adminService.createEmployee(user1);

        assertEquals(2, adminService.viewAll().size());


    }

    @Test
    public void readEmployee() {
        createEmployee();
        User user = adminService.viewAll().get(0);
        User user1 = adminService.readEmployee(user.getId()).getResult();

        assertEquals(user.getUsername(), user1.getUsername());
    }

    @Test
    public void updateEmployee() {
        createEmployee();
        User user = adminService.viewAll().get(0);

        user.setPassword("ParolaNoua!12");

        adminService.updateEmployee(user);

        assertEquals("ParolaNoua!12", adminService.viewAll().get(0).getPassword());
    }

    @Test
    public void deleteEmployee() {
        createEmployee();
        User user =  adminService.viewAll().get(0);
        adminService.deleteEmployee(user.getId());

        assertEquals(1, adminService.viewAll().size());
    }

    @Test
    public void deleteAll(){
        adminService.deleteAll();
        assertEquals(0, adminService.viewAll().size());
    }

    @Test
    public void viewAll(){
        createEmployee();
        assertEquals(2, adminService.viewAll().size());
    }
}