package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(Long id);

    Notification<User> findByUsernameAndPassword(String username, String password);

    void removeById(Long id);

    boolean save(User user);

    boolean updatePassword(User user);

    void removeAll();
}
