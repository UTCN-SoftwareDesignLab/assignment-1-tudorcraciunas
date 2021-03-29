package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from account";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                accounts.add(buildAccountFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findByNumber(String number) throws EntityNotFoundException {
        try{
            Statement statement = connection.createStatement();
            String sql = "SELECT * from account where number='" + number+"'";
            ResultSet rs = statement.executeQuery(sql);

            if(rs.next()){
                return buildAccountFromResultSet(rs);
            }
            else
            {
                throw new EntityNotFoundException(1L,Account.class.getSimpleName());
            }

        } catch (SQLException | EntityNotFoundException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(1L, Account.class.getSimpleName());
        }
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where idaccount=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return buildAccountFromResultSet(rs);
            }
            else
            {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, account.getIdClient());
            insertUserStatement.setString(2, account.getNumber());
            insertUserStatement.setString(3, account.getType());
            insertUserStatement.setFloat(4, account.getBalance());
            insertUserStatement.setDate(5, Date.valueOf(account.getCreationDate()));
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long accountid = rs.getLong(1);
            account.setId(accountid);


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
            String alterTable = "ALTER TABLE account AUTO_INCREMENT = 1";
            String sql = "DELETE from account where idaccount >= 0";
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
            String sql = "DELETE from account where idaccount=" + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Account account){
        try{
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE account SET `number` = ?, `type` = ?, balance = ?, creation_date = ? WHERE `idaccount` = " + account.getId());
            statement.setString(1, account.getNumber());
            statement.setString(2, account.getType());
            statement.setFloat(3, account.getBalance());
            statement.setDate(4, Date.valueOf(account.getCreationDate()));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private Account buildAccountFromResultSet(ResultSet rs) throws SQLException{
        return new AccountBuilder()
                .setId(rs.getLong("idaccount"))
                .setBalance(rs.getFloat("balance"))
                .setCreationDate(rs.getDate("creation_date").toLocalDate())
                .setNumber(rs.getString("number"))
                .setType(rs.getString("type"))
                .setClientId(rs.getLong("idclient"))
                .build();
    }
}
