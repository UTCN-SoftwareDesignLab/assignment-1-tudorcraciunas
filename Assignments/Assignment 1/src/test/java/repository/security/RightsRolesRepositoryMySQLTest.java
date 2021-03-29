package repository.security;

import database.DBConnectionFactory;
import model.Right;
import model.Role;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static database.Constants.Rights;
import static database.Constants.Rights.CREATE_USER;

import java.sql.Connection;
import java.util.List;


public class RightsRolesRepositoryMySQLTest {

    private static RightsRolesRepository rightsRolesRepository;


    @BeforeClass
    public static void setupClass(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);

    }

/*    @Before
    public void cleanUp(){
        rightsRolesRepository.remove
    }
    */
    @Test
    public void addRole() {
        Assert.assertTrue(rightsRolesRepository.addRole("employee"));
        Assert.assertTrue(rightsRolesRepository.addRole("administrator"));
    }

    @Test
    public void addRight() {

        rightsRolesRepository.addRight("test");
        Right right = rightsRolesRepository.findRightByTitle("test");
        Assert.assertEquals("test", right.getRight());
    }

    @Test
    public void findRoleByTitle() {
        Role role = rightsRolesRepository.findRoleByTitle("employee");
        Assert.assertEquals("employee", role.getRole());

    }

    @Test
    public void findRoleById() {
        Role role = rightsRolesRepository.findRoleById(1L);
        Assert.assertNotNull(role.getRole());
    }

    @Test
    public void findRightByTitle() {
        Right right = rightsRolesRepository.findRightByTitle("create_user");
        Assert.assertEquals("create_user", right.getRight());
    }


    @Test
    public void addRoleRight() {
    }
}