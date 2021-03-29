package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {
    private JPanel listsPanel;
    private JPanel buttonsPanel;
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
    private JButton btnProcessBill;
    private JButton btnLogOut;

    private JList<String> clientsJList;
    private JList<String> accountsJList;

    public JTextField clientNameField;
    public JTextField clientCardNumberField;
    public JTextField clientPersonalNumberField;
    public JTextField clientAddressField;
    public JTextField clientPhoneNumberField;

    private JLabel clientNameLabel;
    private JLabel clientCardLabel;
    private JLabel clientPersonalNumberLabel;
    private JLabel clientAddressLabel;
    private JLabel clientPhoneLabel;

    public JTextField accountIdentityField;
    public JTextField accountTypeField;
    public JTextField accountBalanceField;
    public JTextField accountDateField;

    private JLabel accountIdentityLabel;
    private JLabel accountTypeLabel;
    private JLabel accountBalanceLabel;
    private JLabel accountDateLabel;


    public EmployeeView() throws HeadlessException {
        setSize(800,600);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        addComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addComponents(){
        buttonsPanel.add(btnAddClient);
        buttonsPanel.add(btnUpdateClient);
        buttonsPanel.add(btnViewClient);
        buttonsPanel.add(btnCreateAccount);
        buttonsPanel.add(btnUpdateAccount);
        buttonsPanel.add(btnViewAccount);
        buttonsPanel.add(btnDeleteAccount);
        buttonsPanel.add(btnTransfer);
        buttonsPanel.add(btnProcessBill);
        buttonsPanel.add(btnLogOut);

        add(buttonsPanel);


        listsPanel.add(clientsJList);
        listsPanel.add(accountsJList);
        add(listsPanel);

        clientPanel.add(clientNameLabel);
        clientPanel.add(clientNameField);
        clientPanel.add(clientCardLabel);
        clientPanel.add(clientCardNumberField);
        clientPanel.add(clientPersonalNumberLabel);
        clientPanel.add(clientPersonalNumberField);
        clientPanel.add(clientAddressLabel);
        clientPanel.add(clientAddressField);
        clientPanel.add(clientPhoneLabel);
        clientPanel.add(clientPhoneNumberField);
        add(clientPanel);

        accountPanel.add(accountIdentityLabel);
        accountPanel.add(accountIdentityField);
        accountPanel.add(accountTypeLabel);
        accountPanel.add(accountTypeField);
        accountPanel.add(accountBalanceLabel);
        accountPanel.add(accountBalanceField);
        accountPanel.add(accountDateLabel);
        accountPanel.add(accountDateField);

        add(accountPanel);
    }

    private void initializeFields(){
        listsPanel = new JPanel();
        buttonsPanel = new JPanel();
        clientPanel = new JPanel();
        accountPanel = new JPanel();

        btnAddClient = new JButton("Add client");
        btnUpdateClient = new JButton("Update client");
        btnViewClient = new JButton("View client");
        btnCreateAccount = new JButton("Create account");
        btnUpdateAccount = new JButton("Update account");
        btnViewAccount = new JButton("View account");
        btnDeleteAccount = new JButton("Delete account");
        btnTransfer = new JButton("Transfer");
        btnProcessBill = new JButton("Process bill");
        btnLogOut = new JButton("Log Out");

        clientsJList = new JList<>();
        clientsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientsJList.setVisibleRowCount(-1);
        accountsJList = new JList<>();
        accountsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsJList.setVisibleRowCount(-1);

        clientAddressField = new JTextField();
        clientAddressField.setColumns(20);
        clientPersonalNumberField = new JTextField();
        clientPersonalNumberField.setColumns(15);
        clientCardNumberField = new JTextField();
        clientCardNumberField.setColumns(15);
        clientNameField = new JTextField();
        clientNameField.setColumns(15);
        clientPhoneNumberField = new JTextField();
        clientPhoneNumberField.setColumns(15);

        clientNameLabel = new JLabel("Name: ");
        clientCardLabel = new JLabel("Card Number: ");
        clientPersonalNumberLabel = new JLabel("Personal Number: ");
        clientAddressLabel = new JLabel("Address: ");
        clientPhoneLabel = new JLabel("Phone: ");

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
    public void setProcessBillButtonListener(ActionListener actionListener){
        btnProcessBill.addActionListener(actionListener);
    }
    public void setLogOutButtonListener(ActionListener actionListener){
        btnLogOut.addActionListener(actionListener);
    }

    public String getSelectedClientId(){return clientsJList.getSelectedValue();}

    public String getSelectedAccountIdNumber(){return accountsJList.getSelectedValue();}


    public String getSelectedClientNumber() {return clientsJList.getSelectedValue(); }

}
