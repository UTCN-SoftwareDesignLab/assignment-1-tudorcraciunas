package model.DTO;

public class TransferDTO {

    private String sourceAccount;
    private String destinationAccount;
    private Float amount;

    public TransferDTO(String sourceAccount, String destinationAccount, Float amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public Float getAmount() {
        return amount;
    }
}
