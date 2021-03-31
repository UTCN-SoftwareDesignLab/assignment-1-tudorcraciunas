package controller;

import model.User;
import model.validation.Notification;
import service.client.ClientService;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LoginView loginView;
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final EmployeeView employeeView;
    private boolean isAdmin = false;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, EmployeeView employeeView, AdminView adminView) {
        this.loginView = loginView;
        this.adminView = adminView;
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");


                if(loginNotification.getResult().getRole().getRole().equals("administrator")){
                    adminView.setVisible();
                }
                else{

                    employeeView.setUserId(loginNotification.getResult().getId());
                    employeeView.setVisible();

                }
                loginView.setNotVisible();

            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }

}
