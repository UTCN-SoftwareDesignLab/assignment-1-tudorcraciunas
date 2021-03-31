package view;

import jdk.vm.ci.meta.JavaField;
import model.DTO.UserDTO;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {

    private JPanel buttonsPanel;
    private JPanel fieldsPanel;

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;

    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField roleField;


    private JButton btnCreateEmployee;
    private JButton btnReadEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnDeleteEmployee;
    private JButton btnGenerateReport;
    private JButton btnLogout;


    public AdminView() throws HeadlessException {
        setSize(800,600);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        addComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        buttonsPanel = new JPanel();
        fieldsPanel = new JPanel();

        btnCreateEmployee = new JButton("Add User");
        btnDeleteEmployee = new JButton("Delete User");
        btnGenerateReport = new JButton("Generate Report");
        btnReadEmployee = new JButton("View Users");
        btnUpdateEmployee = new JButton("Update User");
        btnLogout = new JButton("Log out");

        usernameField = new JTextField();
        usernameField.setColumns(20);
        passwordField = new JTextField();
        passwordField.setColumns(20);
        roleField = new JTextField();
        roleField.setColumns(10);

        usernameLabel = new JLabel("username:");
        passwordLabel = new JLabel("password:");
        roleLabel = new JLabel("role:");


    }

    private void addComponents(){
        buttonsPanel.add(btnCreateEmployee);
        buttonsPanel.add(btnDeleteEmployee);
        buttonsPanel.add(btnReadEmployee);
        buttonsPanel.add(btnUpdateEmployee);
        buttonsPanel.add(btnGenerateReport);
        buttonsPanel.add(btnLogout);

        add(buttonsPanel);

        fieldsPanel.add(usernameLabel);
        fieldsPanel.add(usernameField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(roleLabel);
        fieldsPanel.add(roleField);


        add(fieldsPanel);


    }

    public UserDTO getUserDTO(){
        String role = "employee";
        if(roleField.getText().equals("admin")){
            role = "admin";
        }

        return new UserDTO(usernameField.getText(),
                passwordField.getText(),
                role
        );
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void setNotVisible(){ this.setVisible(false); }


    public void setAddButtonListener(ActionListener actionListener) {
        btnCreateEmployee.addActionListener(actionListener);
    }
    public void setUpdateButtonListener(ActionListener actionListener){
        btnUpdateEmployee.addActionListener(actionListener);
    }
    public void setDeleteButtonListener(ActionListener actionListener){
        btnDeleteEmployee.addActionListener(actionListener);
    }
    public void setViewButtonListener(ActionListener actionListener){
        btnReadEmployee.addActionListener(actionListener);
    }
    public void setGenerateButtonListener(ActionListener actionListener){
        btnGenerateReport.addActionListener(actionListener);
    }
    public void setLogoutButtonListener(ActionListener actionListener){
        btnLogout.addActionListener(actionListener);
    }

}
