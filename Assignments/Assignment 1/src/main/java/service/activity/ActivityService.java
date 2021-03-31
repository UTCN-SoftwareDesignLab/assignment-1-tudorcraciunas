package service.activity;

import model.Activity;
import model.User;
import model.validation.Notification;

import java.time.LocalDate;
import java.util.List;

public interface ActivityService {
    Notification<Activity> addActivity(Activity activity);

    List<Activity> getActivitiesBetweenDates(LocalDate startDate, LocalDate stopDate, Long userId);

    User findByUserId(Long id);
}
