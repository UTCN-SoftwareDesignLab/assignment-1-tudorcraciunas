package repository.client;

import model.Client;
import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(Long id) throws EntityNotFoundException;

    Notification<Client> findByName(String name);

    Client findByCardNumber(String card);

    boolean update(Client client);

    boolean save(Client client);

    void removeAll();

    void removeById(Long id);
}
