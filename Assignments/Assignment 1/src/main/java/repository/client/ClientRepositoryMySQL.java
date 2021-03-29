package repository.client;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from client";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                clients.add(buildClientFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private Client buildClientFromResultSet(ResultSet resultSet) throws SQLException {
        return new ClientBuilder()
                .setAddress(resultSet.getString("address"))
                .setCardNumber(resultSet.getString("card_number"))
                .setName(resultSet.getString("surname"))
                .setNumericalCode(resultSet.getString("numerical_code"))
                .setId(resultSet.getLong("idclient"))
                .build();

    }


    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        try{
            Statement statement = connection.createStatement();
            String sql = "SELECT * from client where idclient =" + id;
            ResultSet resultSet = statement.executeQuery(sql);


            if(resultSet.next()){
                return buildClientFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Notification<Client> findByName(String name) {
        Notification<Client> findByNameNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from client where surname='" + name + "'";
            ResultSet clientResultSet = statement.executeQuery(sql);
            if (clientResultSet.next()) {
                Client client = buildClientFromResultSet(clientResultSet);
                findByNameNotification.setResult(client);
            } else {
                findByNameNotification.addError("Invalid email or password!");
            }
            return findByNameNotification;
        } catch (SQLException e) {
            e.printStackTrace();
            findByNameNotification.addError("Something is wrong with the Database");
        }
        return findByNameNotification;
    }

    @Override
    public Client findByCardNumber(String card) {
        try{
            Statement statement = connection.createStatement();
            String sql = "SELECT * from client where card_number =" + card;
            ResultSet resultSet = statement.executeQuery(sql);


            if(resultSet.next()){
                return buildClientFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Client client) {
        try{
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE client SET `surname` = ?, card_number = ?, numerical_code = ?, address = ? WHERE `idclient` = " + client.getId());
            statement.setString(1, client.getName());
            statement.setString(2, client.getCardNumber());
            statement.setString(3, client.getNumericalCode());
            statement.setString(4, client.getAddress());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setString(2, client.getCardNumber());
            insertUserStatement.setString(3, client.getNumericalCode());
            insertUserStatement.setString(4, client.getAddress());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            client.setId(clientId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String alterTable = "ALTER TABLE client AUTO_INCREMENT = 1";
            String sql = "DELETE from client where idclient >= 0";
            statement.executeUpdate(sql);
            statement.execute(alterTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeById(Long id){
        try{
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where idclient =" + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
