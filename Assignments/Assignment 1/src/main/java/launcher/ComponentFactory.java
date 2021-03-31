package launcher;

import controller.AdminController;
import controller.EmployeeControler;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.activity.ActivityService;
import service.activity.ActivityServiceMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;

    private final LoginController loginController;
    private final EmployeeControler employeeControler;
    private final AdminController adminController;

    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ActivityService activityService;
    private final AdminService adminService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final AccountRepositoryMySQL accountRepository;
    private final ClientRepositoryMySQL clientRepository;
    private final ActivityRepository activityRepository;

    private static ComponentFactory instance;
    private final EmployeeView employeeView;
    private final AdminView adminView;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.activityRepository = new ActivityRepositoryMySQL(connection);

        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.clientService = new ClientServiceMySQL(clientRepository);
        this.accountService = new AccountServiceMySQL(accountRepository);
        this.activityService = new ActivityServiceMySQL(activityRepository,userRepository);
        this.adminService = new AdminServiceMySQL(userRepository);

        this.loginView = new LoginView();
        this.employeeView = new EmployeeView();
        this.adminView = new AdminView();


        this.loginController = new LoginController(loginView, authenticationService, employeeView, adminView);
        this.employeeControler = new EmployeeControler(clientService, accountService, activityService, employeeView, loginView);
        this.adminController = new AdminController(authenticationService, adminService, activityService, adminView, loginView);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public static ComponentFactory getInstance() {
        return instance;
    }

    public ClientRepositoryMySQL getClientRepository() {
        return clientRepository;
    }

    public AccountRepositoryMySQL getAccountRepository() {
        return accountRepository;
    }

    public LoginController getLoginController() {
        return loginController;
    }
}
