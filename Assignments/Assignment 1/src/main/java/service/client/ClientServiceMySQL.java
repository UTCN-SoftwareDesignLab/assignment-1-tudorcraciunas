package service.client;

import model.Client;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import java.util.List;
import java.util.Random;

public class ClientServiceMySQL implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public Notification<Client> createClient(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        Notification<Client> clientNotification = new Notification<>();

        if(clientValidator.validate()){
            clientRepository.save(client);
            clientNotification.setResult(client);
        }
        else{
            for(String error: clientValidator.getErrors()){
                clientNotification.addError(error);
            }
        }
        return clientNotification;
    }

    @Override
    public Notification<Client> updateClient(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        Notification<Client> clientNotification = new Notification<>();

        if(clientValidator.validate()){
            clientRepository.update(client);
            clientNotification.setResult(client);
        }
        else{
            for(String error : clientValidator.getErrors()){
                clientNotification.addError(error);
            }
        }
        return clientNotification;
    }

    @Override
    public List<Client> viewAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client viewById(Long id) throws EntityNotFoundException {
        return clientRepository.findById(id);
    }

    @Override
    public Notification<Client> viewByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }

    @Override
    public void removeById(Long id) {
        clientRepository.removeById(id);
    }

    @Override
    public String generateCardNumber() {
        String number = "";
        Random random = new Random();

        number = "SX " +  random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + " " +
                random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
        return number;
    }

    @Override
    public String generateNumericalCode(){
        Random r = new Random();
        StringBuilder n = new StringBuilder();
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(" ");
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(" ");
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        n.append(r.nextInt(10));
        return n.toString();
    }
}
