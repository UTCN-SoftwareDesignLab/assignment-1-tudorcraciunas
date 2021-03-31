package service.admin;

import model.DTO.UserDTO;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import java.util.List;

public class AdminServiceMySQL implements AdminService{

    private final UserRepository userRepository;

    public AdminServiceMySQL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Notification<User> createEmployee(User user) {
        UserValidator userValidator = new UserValidator(user);
        Notification<User> userNotification = new Notification<>();

        if(userValidator.validate()){
            userNotification.setResult(user);
            userRepository.save(user);
        }
        else{
            for(String error : userValidator.getErrors()){
                userNotification.addError(error);
            }
        }
        return userNotification;

    }

    @Override
    public Notification<User> createEmployee(UserDTO userDTO) {

        Role userRole = new Role((long) -1, userDTO.getRole());
        User user = new UserBuilder()
                .setUsername(userDTO.getUsername())
                .setPassword(userDTO.getPassword())
                .setRole(userRole)
                .build();

        UserValidator userValidator = new UserValidator(user);
        Notification<User> userNotification = new Notification<>();

        if(userValidator.validate()){
            userNotification.setResult(user);
            userRepository.save(user);
        }
        else{
            for(String error : userValidator.getErrors()){
                userNotification.addError(error);
            }
        }
        return userNotification;

    }

    @Override
    public Notification<User> readEmployee(Long id) {
        Notification<User> userNotification = new Notification<>();

        userNotification.setResult(userRepository.findById(id));
        return userNotification;
    }

    @Override
    public Notification<User> updateEmployee(User user) {
        UserValidator userValidator = new UserValidator(user);
        Notification<User> userNotification = new Notification<>();

        if(userValidator.validate()){
            userNotification.setResult(user);
            userRepository.updatePassword(user);
        }
        else{
            for(String error : userValidator.getErrors()){
                userNotification.addError(error);
            }
        }
        return userNotification;
    }

    @Override
    public void deleteEmployee(Long id) {
        userRepository.removeById(id);
    }

    @Override
    public void deleteAll(){
        userRepository.removeAll();
    }

    @Override
    public List<User> viewAll(){
        return userRepository.findAll();

    }
    @Override
    public Notification<User> viewByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }
    @Override
    public Notification<User> viewByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
