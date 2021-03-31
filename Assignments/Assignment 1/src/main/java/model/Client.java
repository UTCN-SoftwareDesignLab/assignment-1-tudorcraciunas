package model;

public class Client {
    private Long id;
    private String name;
    private String cardNumber;
    private String numericalCode;
    private String address;

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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", numericalCode='" + numericalCode + '\'' +
                ", address='" + address + '\'' +
                '}' + "\n";
    }
}
