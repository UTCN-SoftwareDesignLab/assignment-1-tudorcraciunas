package model.DTO;

import model.Client;
import model.builder.ClientBuilder;

public class ClientDTO {

    private Long id;
    private String name;
    private String cardNumber;
    private String numericalCode;
    private String address;

    public ClientDTO(Long id, String name, String cardNumber, String numericalCode, String address) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.numericalCode = numericalCode;
        this.address = address;
    }
    public ClientDTO(String name, String cardNumber, String numericalCode, String address) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.numericalCode = numericalCode;
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setNumericalCode(String numericalCode) {
        this.numericalCode = numericalCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getNumericalCode() {
        return numericalCode;
    }

    public String getAddress() {
        return address;
    }

    public Client toClient(){
        Client client = new ClientBuilder()
                .setName(this.getName())
                .setCardNumber(this.getCardNumber())
                .setNumericalCode(this.getNumericalCode())
                .setAddress(this.getAddress())
                .setId(this.getId())
                .build();
        return client;
    }
}
