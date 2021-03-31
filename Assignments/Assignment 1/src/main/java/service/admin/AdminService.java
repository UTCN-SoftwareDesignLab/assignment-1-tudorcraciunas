package service.admin;

import model.DTO.UserDTO;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface AdminService {
        Notification<User> createEmployee(User user);

    Notification<User> createEmployee(UserDTO userDTO);

    Notification<User> readEmployee(Long id);
        Notification<User> updateEmployee(User user);
        void deleteEmployee(Long id);

    void deleteAll();

    List<User> viewAll();

    Notification<User> viewByUsernameAndPassword(String username, String password);

    Notification<User> viewByUsername(String username);
}
