package repository.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {
    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from user";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                users.add(buildUserFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(Long id){
        try{
            Statement statement = connection.createStatement();
            String sql = "SELECT * from user where iduser =" + id;
            ResultSet resultSet = statement.executeQuery(sql);


            if(resultSet.next()){
                return buildUserFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User buildUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setRole(rightsRolesRepository.findRoleById(resultSet.getLong("idrole")))
                .setId(resultSet.getLong("iduser"))
                .build();
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRole(rightsRolesRepository.findRoleById(userResultSet.getLong("idrole")))
                        .setId(userResultSet.getLong("iduser"))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public Notification<User> findByUsername(String username) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRole(rightsRolesRepository.findRoleById(userResultSet.getLong("idrole")))
                        .setId(userResultSet.getLong("iduser"))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
            }
            return findByUsernameAndPasswordNotification;
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.setLong(3, rightsRolesRepository.findRoleByTitle(user.getRole().getRole()).getId());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeById(Long id){
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where `iduser` =" + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updatePassword(User user){
        try{
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE user SET password = ?, role = ? WHERE `iduser` = " + user.getId());
            statement.setString(1, user.getPassword());
            statement.setLong(2, rightsRolesRepository.findRoleByTitle(user.getRole().getRole()).getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String alterTable = "ALTER TABLE user AUTO_INCREMENT = 1";
            String sql = "DELETE from user where iduser >= 0";
            statement.executeUpdate(sql);
            statement.execute(alterTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
