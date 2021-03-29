package controller;

import service.user.AuthenticationService;
import view.EmployeeView;

public class EmployeeControler {

    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;


    public EmployeeControler(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.employeeView = new EmployeeView();
    }

    public void setVisible(){
        employeeView.setVisible();
    }
}
