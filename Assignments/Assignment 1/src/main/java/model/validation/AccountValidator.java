package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AccountValidator {

    private List<String> errors;
    private final String IDNUMBERVALID = "[0-9]{4} [0-9]{4} [0-9]{4}";
    private Account account;

    public AccountValidator(Account account){
        errors = new ArrayList<>();
        this.account = account;
    }

    public List<String> getErrors(){
        return errors;
    }

    public boolean validate(){
        validateNumber(account.getNumber());
        validateType(account.getType());
        if(errors.isEmpty()){
            return true;
        }
        return false;
    }

    public void validateType(String type){
        if(!type.equals("credit") && !type.equals("debit") && !type.equals("Credit") && !type.equals("Debit")){
            errors.add("Card type should be credit or debit!");
        }
    }

    public void validateNumber(String number){
        if(!Pattern.compile(IDNUMBERVALID).matcher(number).matches()){
            errors.add(number + " Identification number must be of format example: 1234 5678 1234!");
        }
    }


}
