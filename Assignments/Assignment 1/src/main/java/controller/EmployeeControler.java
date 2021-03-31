package controller;


import model.Account;
import model.Activity;
import model.Client;
import model.DTO.AccountDTO;
import model.DTO.ClientDTO;
import model.DTO.TransferDTO;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import service.user.AuthenticationService;
import view.EmployeeView;
import view.LoginView;
import view.TransferView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class EmployeeControler {

    private  final LoginView loginView;
    private final EmployeeView employeeView;

    private final ClientService clientService;
    private final AccountService accountService;
    private final ActivityService activityService;


    public EmployeeControler(ClientService clientService, AccountService accountService, ActivityService activityService, EmployeeView employeeView, LoginView loginView) {

        this.clientService = clientService;
        this.accountService = accountService;
        this.activityService = activityService;
        this.employeeView = employeeView;
        this.loginView = loginView;

        setButtons();


    }

    public void setButtons(){
        employeeView.setAddClientButtonListener(new EmployeeControler.AddClientButtonListener());
        employeeView.setUpdateClientButtonListener(new EmployeeControler.UpdateClientButtonListener());
        employeeView.setViewClientButtonListener(new EmployeeControler.ViewClientButtonListener());
        employeeView.setCreateAccountButtonListener(new EmployeeControler.CreateAccountButtonListener());
        employeeView.setDeleteAccountButtonListener(new EmployeeControler.DeleteAccountButtonListener());
        employeeView.setUpdateAccountButtonListener(new EmployeeControler.UpdateAccountButtonListener());
        employeeView.setViewAccountButtonListener(new EmployeeControler.ViewAccountButtonListener());
        employeeView.setTransferButtonListener(new EmployeeControler.TransferButtonListener());
        employeeView.setLogOutButtonListener(new EmployeeControler.LogOutButtonListener());
    }

    public void setVisible(){
        employeeView.setVisible();
    }

    private class AddClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ClientDTO clientDTO = employeeView.getClientDTO();
            Notification<Client> addClientNotification = clientService.createClient(clientDTO.toClient());

            if(!addClientNotification.hasErrors()){
                Activity activity = new ActivityBuilder()
                        .setId(-1L)
                        .setUserId(employeeView.getUserId())
                        .setActivity("New client added: " + clientDTO.getName())
                        .setDate(LocalDate.now())
                        .build();
                activityService.addActivity(activity);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client successfully created!");

            }
            else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(), addClientNotification.getFormattedErrors());
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ClientDTO clientDTO = employeeView.getClientDTO();
            Notification<Client> clientNotification = clientService.viewByName(clientDTO.getName());
            if(!clientNotification.hasErrors()){

                Client client = clientNotification.getResult();
                clientDTO.setId(client.getId());
                Notification<Client> updateClientNotification = clientService.updateClient(clientDTO.toClient());

                if(!updateClientNotification.hasErrors()){
                    Activity activity = new ActivityBuilder()
                            .setId(-1L)
                            .setUserId(employeeView.getUserId())
                            .setActivity("Client " + clientDTO.getName() + " updated")
                            .setDate(LocalDate.now())
                            .build();
                    activityService.addActivity(activity);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client successfully updated!");
                }
                else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), updateClientNotification.getFormattedErrors());
                }
            }

        }
    }

    private class ViewClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> viewClients = clientService.viewAll();

            if(!viewClients.isEmpty()){
                Activity activity = new ActivityBuilder()
                        .setId(-1L)
                        .setUserId(employeeView.getUserId())
                        .setActivity("View all clients")
                        .setDate(LocalDate.now())
                        .build();
                activityService.addActivity(activity);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), viewClients.toString());

            }
            else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Clients not viewable at the moment maybe because of an error.");
            }
        }
    }

    private class CreateAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            AccountDTO accountDTO = employeeView.getAccountDTO();
            Client client = clientService.viewByName(accountDTO.getOwner()).getResult();
            accountDTO.setIdClient(client.getId());
            Notification<Account> createAccount = accountService.createAccount(accountDTO.toAccount());

            if(!createAccount.hasErrors()){
                Activity activity = new ActivityBuilder()
                        .setId(-1L)
                        .setUserId(employeeView.getUserId())
                        .setActivity("Created account " + accountDTO.getNumber())
                        .setDate(LocalDate.now())
                        .build();
                activityService.addActivity(activity);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully created!");
            }
            else{
                JOptionPane.showMessageDialog(employeeView.getContentPane(), createAccount.getFormattedErrors());
            }
        }
    }

    private class DeleteAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                 AccountDTO accountDTO = employeeView.getAccountDTO();

                accountService.removeById(accountService.viewByNumber(accountDTO.getNumber()).getId());

                Activity activity = new ActivityBuilder()
                        .setId(-1L)
                        .setUserId(employeeView.getUserId())
                        .setActivity("Delete account " + accountDTO.getNumber())
                        .setDate(LocalDate.now())
                        .build();
                activityService.addActivity(activity);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully deleted!");

            } catch (EntityNotFoundException ex) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account could not be deleted.");
                ex.printStackTrace();
            }
        }
    }

    private class UpdateAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            AccountDTO accountDTO = employeeView.getAccountDTO();

            try {
                Account account = accountService.viewByNumber(accountDTO.getNumber());
                accountDTO.setIdClient(account.getIdClient());
                accountDTO.setId(account.getId());
                Notification<Account> accountUpdate = accountService.updateAccount(accountDTO.toAccount());

                if(!accountUpdate.hasErrors()){
                    Activity activity = new ActivityBuilder()
                            .setId(-1L)
                            .setUserId(employeeView.getUserId())
                            .setActivity("Update account " + accountDTO.getNumber())
                            .setDate(LocalDate.now())
                            .build();
                    activityService.addActivity(activity);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully updated!");

                }
                else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), accountUpdate.getFormattedErrors());
                }


            } catch (EntityNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class ViewAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Account> accountList = accountService.viewAll();

            if(!accountList.isEmpty()){
                Activity activity = new ActivityBuilder()
                        .setId(-1L)
                        .setUserId(employeeView.getUserId())
                        .setActivity("View all accounts")
                        .setDate(LocalDate.now())
                        .build();
                activityService.addActivity(activity);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountList.toString());

            }
            else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Clients not viewable at the moment maybe because of an error.");
            }
        }
    }

    private class TransferButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Account> accountList = accountService.viewAll();
                TransferView transferView = new TransferView(accountList);

                TransferDTO transferDTO = transferView.getTransferDTO();

                Notification<String> transferNotification = accountService.transferMoney(transferDTO.getSourceAccount(), transferDTO.getDestinationAccount(), transferDTO.getAmount());

                if(!transferNotification.hasErrors()){
                    Activity activity = new ActivityBuilder()
                            .setId(-1L)
                            .setUserId(employeeView.getUserId())
                            .setActivity("Transfer succeeded.")
                            .setDate(LocalDate.now())
                            .build();
                    activityService.addActivity(activity);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), transferNotification.getResult());
                }
                else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), transferNotification.getFormattedErrors());
                }

            } catch (EntityNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class LogOutButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            loginView.setVisible();
            employeeView.setNotVisible();
        }
    }





}
