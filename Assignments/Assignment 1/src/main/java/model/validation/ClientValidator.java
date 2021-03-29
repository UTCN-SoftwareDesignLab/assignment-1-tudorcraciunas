package model.validation;

import model.Account;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {

    private List<String> errors;
    private final String IDNUMBERVALID = "^[A-Z]{2} [0-9]{3} [0-9]{3}+$";
    private final String CODEVALID = "^[0-9]{4}+[0-9]{4}+[0-9]{4}+$";
    private Client client;

    public ClientValidator(Client client){
        errors = new ArrayList<>();
        this.client = client;
    }

    public List<String> getErrors(){
        return errors;
    }

    public boolean validate(){
        validateNumber(client.getCardNumber());
        validateCode(client.getNumericalCode());
        if(errors.isEmpty()){
            return true;
        }
        return false;
    }

    public void validateCode(String code){
        if(Pattern.compile(CODEVALID).matcher(code).matches()){
            errors.add(code + ": Numerical code must be of format example: 1234 1231 3544");
        }
    }

    public void validateNumber(String number){
        if(!Pattern.compile(IDNUMBERVALID).matcher(number).matches()){
            errors.add(number + " Identification number must be of format example: SX 245 123!");
        }
    }
}
