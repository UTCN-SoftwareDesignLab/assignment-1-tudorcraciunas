package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientService {

    Notification<Client> createClient(Client client);
    Notification<Client> updateClient(Client client);

    List<Client> viewAll();

    Client viewById(Long id) throws EntityNotFoundException;
    Notification<Client> viewByName(String name);

    void removeAll();
    void removeById(Long id);

    String generateCardNumber();
    String generateNumericalCode();
}
