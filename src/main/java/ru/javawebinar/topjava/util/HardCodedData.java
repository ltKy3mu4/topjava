package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class HardCodedData {
     private static List<MealWithExceed> mealWithExceeds = Arrays.asList(
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500, false),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000,false),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500,false),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000, true),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500, true),
            new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510,true)
    );

    public static List<MealWithExceed> getMealData(){
        return mealWithExceeds;
    }
}
