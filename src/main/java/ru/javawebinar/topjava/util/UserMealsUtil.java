package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> somelist= getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> list = new ArrayList<>();
        List<UserMealWithExceed> buffer = new ArrayList<>();
        int tempraryCalories = 0;

        LocalDateTime ldt = null;


        for (UserMeal um: mealList){
            if (ldt==null){
                ldt = um.getDateTime();
            }
            else {
                if (um.getDateTime().toLocalDate().isAfter(ldt.toLocalDate())){
                    checkCalories(caloriesPerDay, buffer, tempraryCalories);
                    buffer.clear();
                    tempraryCalories = 0;
                    ldt = um.getDateTime();
                }
            }
            tempraryCalories+=um.getCalories();
            UserMealWithExceed umwe = new UserMealWithExceed(um.getDateTime(),um.getDescription(),um.getCalories(),false);
            buffer.add(umwe);
            if (um.getDateTime().toLocalTime().isAfter(startTime)
                    &&
                    um.getDateTime().toLocalTime().isBefore(endTime)){
                list.add(umwe);
            }

        }
        checkCalories(caloriesPerDay, buffer, tempraryCalories);
        return list;
    }

    private static void checkCalories(int caloriesPerDay, List<UserMealWithExceed> buffer, int tempraryCalories) {
        if (tempraryCalories>caloriesPerDay){
            for(UserMealWithExceed meal:buffer){
                meal.setExceed(true);
            }
        }
    }
}
