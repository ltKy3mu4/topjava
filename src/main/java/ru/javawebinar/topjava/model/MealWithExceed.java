package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    private final Integer id;


    public MealWithExceed(Integer id,LocalDateTime dateTime, String description, int calories,  boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.id=id;
    }

    public String getDateTime(){
        return dateTime.toLocalDate()+ " "+ dateTime.toLocalTime();
    }

//    public LocalDateTime getDateTime() {return dateTime;}

    public LocalDateTime getLocalDateTime() {return dateTime;}

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    public Integer getId() { return id; }
}
