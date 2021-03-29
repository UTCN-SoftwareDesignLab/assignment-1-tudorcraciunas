package model.builder;

import model.Client;

public class ClientBuilder {

    private final Client client;

    public ClientBuilder(){
        this.client = new Client();
    }

    public ClientBuilder setId(Long id){
        client.setId(id);
        return this;
    }
    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }
    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }
    public ClientBuilder setCardNumber(String number){
        client.setCardNumber(number);
        return this;
    }
    public ClientBuilder setNumericalCode(String code){
        client.setNumericalCode(code);
        return this;
    }

    public Client build(){
        return client;
    }

}
