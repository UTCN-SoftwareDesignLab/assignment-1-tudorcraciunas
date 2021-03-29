package model.builder;

import model.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountBuilder {

    private final Account account;

    public AccountBuilder(){
        this.account = new Account();
    }

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }
    public AccountBuilder setClientId(Long idClient){
        account.setIdClient(idClient);
        return this;
    }
    public AccountBuilder setNumber(String number){
        account.setNumber(number);
        return this;
    }
    public AccountBuilder setType (String type){
        account.setType(type);
        return this;
    }
    public AccountBuilder setBalance (Float balance){
        account.setBalance(balance);
        return this;
    }
    public AccountBuilder setCreationDate (LocalDate date) {
        account.setCreationDate(date);
        return this;
    }

    public Account build(){
        return account;
    }
}
