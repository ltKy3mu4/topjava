package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> somelist = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        System.out.println();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> list = new ArrayList<>();
        List<UserMealWithExceed> buffer = new ArrayList<>();

        mealList.sort((o1, o2) -> {
            if (o1.getDateTime().isBefore(o2.getDateTime()))
                return -1;
            else
                return 1;
        });


        int tempraryCalories = 0;

        LocalDateTime newDayDistinguisher = null;


        for (UserMeal userMeal : mealList) {
            if (newDayDistinguisher == null) {
                newDayDistinguisher = userMeal.getDateTime();
            } else {
                if (userMeal.getDateTime().toLocalDate().isAfter(newDayDistinguisher.toLocalDate())) {
                    checkCalories(caloriesPerDay, buffer, tempraryCalories);
                    buffer.clear();
                    tempraryCalories = 0;
                    newDayDistinguisher = userMeal.getDateTime();
                }
            }
            tempraryCalories += userMeal.getCalories();
            UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false);
            buffer.add(userMealWithExceed);
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(userMealWithExceed);
            }
        }
        checkCalories(caloriesPerDay, buffer, tempraryCalories);
        return list;
    }

    private static void checkCalories(int caloriesPerDay, List<UserMealWithExceed> buffer, int tempraryCalories) {
        if (tempraryCalories > caloriesPerDay) {
            for (UserMealWithExceed meal : buffer) {
                meal.setExceed(true);
            }
        }
    }
}
