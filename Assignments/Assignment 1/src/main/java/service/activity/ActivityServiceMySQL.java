package service.activity;

import model.Activity;
import model.validation.Notification;
import repository.activity.ActivityRepository;

import java.time.LocalDate;
import java.util.List;

public class ActivityServiceMySQL implements ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
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
}
