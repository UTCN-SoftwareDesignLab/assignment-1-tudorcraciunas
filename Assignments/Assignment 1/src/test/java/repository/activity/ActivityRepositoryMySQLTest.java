package repository.activity;

import database.DBConnectionFactory;
import jdk.vm.ci.meta.Local;
import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.client.ClientServiceMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;

public class ActivityRepositoryMySQLTest {

    private static ActivityRepository activityRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepository userRepository;
    @BeforeClass
    public static void setupClass() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        activityRepository = new ActivityRepositoryMySQL(connection);
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    }

    @Before
    public void clean(){
        removeAll();
        userRepository.removeAll();
    }

    @Test
    public void findAll() {
        add();

        assertEquals(2, activityRepository.findAll().size());

    }

    @Test
    public void findByUserId() {
        add();
        Activity activity = activityRepository.findAll().get(0);
        Activity activity1 = activityRepository.findByUserId(activity.getIdUser());

        assertEquals(activity.getActivity(), activity1.getActivity());
    }

    @Test
    public void findById() {
        add();
        Activity activity = activityRepository.findAll().get(0);
        Activity activity1 = activityRepository.findByUserId(activity.getId());

        assertEquals(activity.getActivity(), activity1.getActivity());
    }

    @Test
    public void add() {
        User user = new UserBuilder()
                .setUsername("test.activity@mail.com")
                .setPassword("parolaTest!12")
                .setRole(rightsRolesRepository.findRoleByTitle("employee"))
                .build();
        userRepository.save(user);
        Activity activity = new ActivityBuilder()
                .setUserId(user.getId())
                .setDate(LocalDate.now())
                .setActivity("Testing user activities.")
                .build();
        Activity activity1 = new ActivityBuilder()
                .setUserId(user.getId())
                .setDate(LocalDate.now())
                .setActivity("Testing user activities.")
                .build();

        assertTrue(activityRepository.add(activity));
        assertTrue(activityRepository.add(activity1));
    }

    @Test
    public void findBetweenDates() {
        add();
        User user = userRepository.findAll().get(0);
        assertEquals(2, activityRepository.findBetweenDates(LocalDate.of(2021,3,12), LocalDate.of(2021,4,1), user.getId()).size());
    }

    @Test
    public void removeAll() {
        activityRepository.removeAll();
        assertEquals(0, activityRepository.findAll().size());
    }
}