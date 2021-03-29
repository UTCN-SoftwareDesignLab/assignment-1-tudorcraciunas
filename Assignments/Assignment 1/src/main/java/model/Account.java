package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {

    private Long id;
    private Long idClient;
    private String number;
    private String type;
    private Float balance;
    private LocalDate creationDate;


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
}
