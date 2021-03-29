package repository.activity;

import model.Activity;
import model.Client;
import model.builder.ActivityBuilder;
import model.validation.Notification;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ActivityRepositoryMySQL implements ActivityRepository {

    private final Connection connection;

    public ActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from activities";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                activities.add(buildActivityFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    private Activity buildActivityFromResultSet(ResultSet resultSet) throws SQLException {
        Activity activity = new ActivityBuilder()
                .setActivity(resultSet.getString("activity"))
                .setDate(resultSet.getDate("date").toLocalDate())
                .setId(resultSet.getLong("idactivities"))
                .setUserId(resultSet.getLong("iduser"))
                .build();
        return activity;
    }

    @Override
    public Activity findByUserId(Long id) {
        Activity activity = new Activity();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from activities where iduser =" + id;
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                return buildActivityFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Activity findById(Long id) {
        Activity activity = new Activity();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from activities where idactivities =" + id;
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                return buildActivityFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(Activity activity) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO activities values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, activity.getIdUser());
            insertUserStatement.setString(2, activity.getActivity());
            insertUserStatement.setDate(3, java.sql.Date.valueOf(activity.getDate()));
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long activityId = rs.getLong(1);
            activity.setId(activityId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Activity> findBetweenDates(LocalDate startDate, LocalDate stopDate, Long id) {
        List<Activity> activities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from activities where iduser =" + id + " AND `date` between '" + Date.valueOf(startDate) + "' AND '" + Date.valueOf(stopDate) + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                activities.add(buildActivityFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String alterTable = "ALTER TABLE activities AUTO_INCREMENT = 1";
            String sql = "DELETE from activities where idactivities >= 0";
            statement.executeUpdate(sql);
            statement.execute(alterTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
