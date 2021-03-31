package view;

import model.Account;
import model.DTO.AccountDTO;
import model.DTO.TransferDTO;

import javax.swing.*;
import java.util.List;

public class TransferView extends JFrame {

    private final JTextField amount = new JTextField();
    private JComboBox<String> clientOption;
    private JComboBox<String> clientOption2;
    private String[] accountNumbers;

    public TransferView(List<Account> accounts) {

        accountNumbers = new String[accounts.size()];

        clientOption = new JComboBox();
        clientOption2 = new JComboBox();

        for(int i = 0; i < accounts.size(); i++){
            clientOption.addItem(accounts.get(i).getNumber());
            clientOption2.addItem(accounts.get(i).getNumber());
        }


        Object[] TransferBox = {
                "Account:", clientOption,
                "Amount of money:", amount,
                "Account:", clientOption2
        };
        JOptionPane.showConfirmDialog(this, TransferBox, "Transfer", JOptionPane.OK_CANCEL_OPTION);

    }

    public TransferDTO getTransferDTO(){
        return new TransferDTO(
                clientOption.getSelectedItem().toString(),
                clientOption2.getSelectedItem().toString(),
                Float.valueOf(amount.getText())
                );
    }

}
