package model.validation;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final User user;
    private final List<String> errors;

    public UserValidator(User user) {
        this.user = user;
        errors = new ArrayList<>();
    }

    public List<String> getErrors(){
        return errors;
    }

    public boolean validate(){
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        validateRole(user.getRole().getRole());
        return errors.isEmpty();
    }

    private void validateUsername(String username) {
        if (!Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(username).matches()){
            errors.add("Invalid email!");
        }
    }

    private void validateRole(String role){
        if(!role.equals("admin") && !role.equals("employee"))
            errors.add("Role must be admin or employee but is instead " + role);
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH){
            errors.add("Password must contain at least 8 characters!");
        }
        if(!containsSpecialCharacter(password)){
            errors.add("Password must conatin at least one special character!");
        }
        if(!containsDigit(password)){
            errors.add("Password must contain at least one digit!");
        }
        if(!containsUppercase(password)){
            errors.add("Password must contain at least one uppercase letter!");
        }
    }

    private boolean containsDigit(String password) {
        if (password != null && !password.isEmpty()){
            for (char c : password.toCharArray()){
                if (Character.isDigit(c)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsUppercase(String password){
        if(password != null && !password.isEmpty()){
            for(char c : password.toCharArray()){
                if(Character.isUpperCase(c)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsSpecialCharacter(String password) {
        if (password == null || password.trim().isEmpty()){
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(password);
        return m.find();
    }

}
