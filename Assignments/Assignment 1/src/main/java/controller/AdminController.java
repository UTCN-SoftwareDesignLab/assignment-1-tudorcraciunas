package controller;

import model.Activity;
import model.DTO.UserDTO;
import model.User;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.activity.ActivityService;
import service.admin.AdminService;
import service.client.ClientService;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class AdminController {

    private  final LoginView loginView;
    private final AdminView adminView;

    private final AdminService adminService;
    private final AuthenticationService authenticationService;

    private final ActivityService activityService;



    public AdminController(AuthenticationService authenticationService, AdminService adminService, ActivityService activityService, AdminView adminView, LoginView loginView) {
        this.authenticationService = authenticationService;
        this.adminService = adminService;
        this.activityService = activityService;
        this.adminView = adminView;
        this.loginView = loginView;

        setButtons();
    }

    public void setButtons(){
        adminView.setAddButtonListener(new AdminController.AddUserButtonListener());
        adminView.setDeleteButtonListener(new AdminController.DeleteUserButtonListener());
        adminView.setUpdateButtonListener(new AdminController.UpdateButtonListener());
        adminView.setViewButtonListener(new AdminController.ViewButtonListener());
        adminView.setLogoutButtonListener(new AdminController.LogoutButtonListener());
        adminView.setGenerateButtonListener(new AdminController.GenerateReportButtonListener());
    }

    private class AddUserButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            UserDTO userDTO = adminView.getUserDTO();

            Notification<Boolean> addUser = authenticationService.register(userDTO.getUsername(), userDTO.getPassword());

            if(!addUser.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), "User successfully created!");
            }
            else{
                JOptionPane.showMessageDialog(adminView.getContentPane(), addUser.getFormattedErrors());
            }
        }
    }

    private class DeleteUserButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            UserDTO userDTO = adminView.getUserDTO();

            Notification<User> userNotification = adminService.viewByUsername(userDTO.getUsername());
            if (!userNotification.hasErrors()){
                User user = adminService.viewByUsername(userDTO.getUsername()).getResult();

                adminService.deleteEmployee(user.getId());

                JOptionPane.showMessageDialog(adminView.getContentPane(), "User deleted!");
            }
            else{
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Errors occurred!" + userNotification.getFormattedErrors());
            }

        }
    }

    private class UpdateButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            UserDTO userDTO = adminView.getUserDTO();

            Notification<User> userFound = adminService.viewByUsername(userDTO.getUsername());

            if(!userFound.hasErrors())
            {
                User user = userFound.getResult();
                user.setPassword(userDTO.getPassword());
                Notification<User> userNotification =  adminService.updateEmployee(user);

                if(!userNotification.hasErrors()){
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "User updated!");
                }
                else{
                    JOptionPane.showMessageDialog(adminView.getContentPane(), userNotification.getFormattedErrors());
                }
            }


        }
    }

    private class ViewButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> userList = adminService.viewAll();

            if(!userList.isEmpty()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), userList.toString());
            }
            else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Users not viewable at the moment maybe because of an error.");
            }

        }
    }

    private class GenerateReportButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            UserDTO userDTO = adminView.getUserDTO();

            Notification<User> userNotification = adminService.viewByUsername(userDTO.getUsername());

            if(!userNotification.hasErrors()){

                User user = userNotification.getResult();
                //I mostly did this because i wanted to simplify the process such that i could test everything to work and then couldnt fint the time to implement something better
                List<Activity> activityList = activityService.getActivitiesBetweenDates(LocalDate.of(2000, 10, 10), LocalDate.of(2100, 10, 10), user.getId());

                JOptionPane.showMessageDialog(adminView.getContentPane(), activityList.toString());
            }
            else{
                JOptionPane.showMessageDialog(adminView.getContentPane(), "big errors and  " + userNotification.getFormattedErrors());
            }




        }
    }

    private class LogoutButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            adminView.setNotVisible();
            loginView.setVisible();
        }
    }



}
