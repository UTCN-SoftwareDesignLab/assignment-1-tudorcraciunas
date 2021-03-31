package view;

import model.DTO.AccountDTO;
import model.DTO.ClientDTO;
import service.client.ClientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

public class EmployeeView extends JFrame {

    private JPanel buttonsPanelClient;
    private JPanel buttonsPanelAccount;
    private JPanel buttonsPanelLog;

    private JPanel clientPanel;
    private JPanel accountPanel;

    private JButton btnAddClient;
    private JButton btnUpdateClient;
    private JButton btnViewClient;
    private JButton btnCreateAccount;
    private JButton btnUpdateAccount;
    private JButton btnViewAccount;
    private JButton btnDeleteAccount;
    private JButton btnTransfer;
    private JButton btnLogOut;

    public JTextField clientNameField;
    public JTextField clientCardNumberField;
    public JTextField clientPersonalNumberField;
    public JTextField clientAddressField;

    private JLabel clientNameLabel;
    private JLabel clientCardLabel;
    private JLabel clientPersonalNumberLabel;
    private JLabel clientAddressLabel;

    public JTextField accountIdentityField;
    public JTextField accountTypeField;
    public JTextField accountBalanceField;
    public JTextField accountDateField;

    private JLabel accountIdentityLabel;
    private JLabel accountTypeLabel;
    private JLabel accountBalanceLabel;
    private JLabel accountDateLabel;


    private Long userId = -1L;


    public EmployeeView() throws HeadlessException {
        super("Employee Operations");
        setSize(1200,900);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        addComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addComponents(){
        buttonsPanelClient.add(btnAddClient);
        buttonsPanelClient.add(btnUpdateClient);
        buttonsPanelClient.add(btnViewClient);
        buttonsPanelAccount.add(btnCreateAccount);
        buttonsPanelAccount.add(btnUpdateAccount);
        buttonsPanelAccount.add(btnViewAccount);
        buttonsPanelAccount.add(btnDeleteAccount);
        buttonsPanelAccount.add(btnTransfer);

        buttonsPanelLog.add(btnLogOut);


        add(buttonsPanelClient);
        add(buttonsPanelAccount);
        add(buttonsPanelLog);

        clientPanel.add(clientNameLabel);
        clientPanel.add(clientNameField);
        clientPanel.add(clientCardLabel);
        clientPanel.add(clientCardNumberField);
        clientPanel.add(clientPersonalNumberLabel);
        clientPanel.add(clientPersonalNumberField);
        clientPanel.add(clientAddressLabel);
        clientPanel.add(clientAddressField);


        add(clientPanel, BorderLayout.SOUTH);

        accountPanel.add(accountIdentityLabel);
        accountPanel.add(accountIdentityField);
        accountPanel.add(accountTypeLabel);
        accountPanel.add(accountTypeField);
        accountPanel.add(accountBalanceLabel);
        accountPanel.add(accountBalanceField);



        add(accountPanel, BorderLayout.SOUTH);
    }

    private void initializeFields(){

        buttonsPanelClient = new JPanel();
        buttonsPanelAccount = new JPanel();
        buttonsPanelLog = new JPanel();

       buttonsPanelClient.setLayout(new GridLayout(1,5,2,2));
        buttonsPanelAccount.setLayout(new GridLayout(1,5,2,2));
        buttonsPanelLog.setLayout(new GridLayout(1,5,1,1));


        clientPanel = new JPanel();
        accountPanel = new JPanel();

        btnAddClient = new JButton("Add client" );
        btnUpdateClient = new JButton("Update client");
        btnViewClient = new JButton("View client");
        btnCreateAccount = new JButton("Create account");
        btnUpdateAccount = new JButton("Update account");
        btnViewAccount = new JButton("View account");
        btnDeleteAccount = new JButton("Delete account");
        btnTransfer = new JButton("Transfer");

        btnLogOut = new JButton("Log Out");


        clientAddressField = new JTextField();
        clientAddressField.setColumns(20);
        clientPersonalNumberField = new JTextField();
        clientPersonalNumberField.setColumns(15);
        clientCardNumberField = new JTextField();
        clientCardNumberField.setColumns(15);
        clientNameField = new JTextField();
        clientNameField.setColumns(15);



        clientNameLabel = new JLabel("Name: ");
        clientCardLabel = new JLabel("Identity Card Number: ");
        clientPersonalNumberLabel = new JLabel("Personal Numerical Code: ");
        clientAddressLabel = new JLabel("Address: ");


        accountIdentityField = new JTextField();
        accountIdentityField.setColumns(15);
        accountTypeField = new JTextField();
        accountTypeField.setColumns(5);
        accountBalanceField = new JTextField();
        accountBalanceField.setColumns(8);
        accountDateField = new JTextField();
        accountDateField.setColumns(8);

        accountBalanceLabel = new JLabel("Balance: ");
        accountDateLabel = new JLabel("Date: ");
        accountIdentityLabel = new JLabel("Identification Number: ");
        accountTypeLabel = new JLabel("Type: ");

    }

    public ClientDTO getClientDTO(){
        return new ClientDTO(
                clientNameField.getText(),
                clientCardNumberField.getText(),
                clientPersonalNumberField.getText(),
                clientAddressField.getText()
                );

    }

    public AccountDTO getAccountDTO(){
        LocalDate now = LocalDate.now() ;

        return new AccountDTO(
                clientNameField.getText(),
                accountIdentityField.getText(),
                accountTypeField.getText(),
                Float.valueOf(accountBalanceField.getText()),
                now
            );
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void setNotVisible(){ this.setVisible(false); }

    public void setAddClientButtonListener(ActionListener actionListener) {
        btnAddClient.addActionListener(actionListener);
    }
    public void setUpdateClientButtonListener(ActionListener actionListener){
        btnUpdateClient.addActionListener(actionListener);
    }
    public void setViewClientButtonListener(ActionListener actionListener){
        btnViewClient.addActionListener(actionListener);
    }
    public void setCreateAccountButtonListener(ActionListener actionListener){
        btnCreateAccount.addActionListener(actionListener);
    }
    public void setViewAccountButtonListener(ActionListener actionListener){
        btnViewAccount.addActionListener(actionListener);
    }
    public void setUpdateAccountButtonListener(ActionListener actionListener){
        btnUpdateAccount.addActionListener(actionListener);
    }
    public void setDeleteAccountButtonListener(ActionListener actionListener){
        btnDeleteAccount.addActionListener(actionListener);
    }
    public void setTransferButtonListener(ActionListener actionListener){
        btnTransfer.addActionListener(actionListener);
    }

    public void setLogOutButtonListener(ActionListener actionListener){
        btnLogOut.addActionListener(actionListener);
    }

}
