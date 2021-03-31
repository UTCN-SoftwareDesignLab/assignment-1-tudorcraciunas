package model.DTO;

import model.Account;
import model.builder.AccountBuilder;

import java.time.LocalDate;

public class AccountDTO {

    private Long id;
    private Long idClient;
    private String owner;
    private String number;
    private String type;
    private Float balance;
    private LocalDate creationDate;


    public AccountDTO(String number) {
        this.number = number;
    }

    public AccountDTO(String owner, String number, String type, Float balance, LocalDate creationDate) {
        this.owner = owner;
        this.number = number;
        this.type = type;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public Float getBalance() {
        return balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Account toAccount(){
        Account account = new AccountBuilder()
                .setClientId(this.getIdClient())
                .setBalance(this.getBalance())
                .setCreationDate(this.getCreationDate())
                .setNumber(this.getNumber())
                .setType(this.getType())
                .setId(this.getId())
                .build();
        return account;
    }
}
