package service.activity;

import model.Activity;
import model.User;
import model.validation.Notification;
import repository.activity.ActivityRepository;
import repository.user.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class ActivityServiceMySQL implements ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;

    }


    @Override
    public Notification<Activity> addActivity(Activity activity) {
        Notification<Activity> activityNotification = new Notification<>();

        if(activityRepository.add(activity)){
            activityNotification.setResult(activity);
        }
        else {
            activityNotification.addError("Activity was not added.");
        }
        return activityNotification;
    }

    @Override
    public List<Activity> getActivitiesBetweenDates(LocalDate startDate, LocalDate stopDate, Long userId) {
        return activityRepository.findBetweenDates(startDate,stopDate,userId);
    }

    @Override
    public User findByUserId(Long id) {
        return userRepository.findById(id);
    }


}
