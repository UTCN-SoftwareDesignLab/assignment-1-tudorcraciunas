package repository.user;

import database.DBConnectionFactory;
import model.User;
import model.builder.UserBuilder;
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

public class UserRepositoryMySQLTest {

    private static UserRepositoryMySQL userRepositoryMySQL;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setupClass(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepositoryMySQL = new UserRepositoryMySQL(connection, rightsRolesRepository);

    }

    @After
    public void clear(){
        removeAll();
    }

    @Test
    public void findById() throws Exception{
        userRepositoryMySQL.findById(1L);
    }

    @Test
    public void findAll() {
        removeAll();
        Assert.assertEquals(0, userRepositoryMySQL.findAll().size());
    }

    @Test
    public void findByUsernameAndPassword() {
        save();

        User employee = userRepositoryMySQL.findAll().get(1);

        Notification<User> notification = userRepositoryMySQL.findByUsernameAndPassword(employee.getUsername(), employee.getPassword());
        assertFalse(notification.hasErrors());

    }

    @Test
    public void save() {
        removeAll();
        User admin = new UserBuilder().setUsername("admin99@mail.com")
        .setPassword("ParolA12!@")
        .setRole(rightsRolesRepository.findRoleByTitle("administrator"))
        .build();
        userRepositoryMySQL.save(admin);

        User employee = new UserBuilder().setUsername("employee99@mail.com")
                .setPassword("Password12!@")
                .setRole(rightsRolesRepository.findRoleByTitle("employee"))
                .build();
        userRepositoryMySQL.save(employee);

        Assert.assertEquals(2, userRepositoryMySQL.findAll().size());

    }

    @Test
    public void removeById() {
        save();
        userRepositoryMySQL.removeById(1L);
        Assert.assertEquals(1, userRepositoryMySQL.findAll().size());

    }

    @Test
    public void updatePassword() {
        save();
        User employee = userRepositoryMySQL.findAll().get(1);

        Assert.assertTrue(userRepositoryMySQL.updatePassword(employee));
    }

    @Test
    public void removeAll() {
        userRepositoryMySQL.removeAll();
        Assert.assertEquals(0, userRepositoryMySQL.findAll().size());
    }
}