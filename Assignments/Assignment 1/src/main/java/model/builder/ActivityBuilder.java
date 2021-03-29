package model.builder;

import model.Activity;

import java.time.LocalDate;
import java.util.Date;

public class ActivityBuilder {
    private final Activity activity;

    public ActivityBuilder() {
        this.activity = new Activity();
    }

    public ActivityBuilder setId(Long id){
        activity.setId(id);
        return this;
    }
    public ActivityBuilder setActivity(String act){
        activity.setActivity(act);
        return this;
    }
    public ActivityBuilder setDate(LocalDate date){
        activity.setDate(date);
        return this;
    }
    public ActivityBuilder setUserId(Long id){
        activity.setIdUser(id);
        return this;
    }

    public Activity build(){
        return activity;
    }
}
