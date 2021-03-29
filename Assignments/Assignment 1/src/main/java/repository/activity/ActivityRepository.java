package repository.activity;

import model.Activity;
import model.validation.Notification;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ActivityRepository {
    List<Activity> findAll();

    Activity findByUserId(Long id);
    Activity findById(Long id);

    boolean add(Activity activity);

    List<Activity> findBetweenDates(LocalDate startDate, LocalDate stopDate, Long idUser);

    void removeAll();

}
