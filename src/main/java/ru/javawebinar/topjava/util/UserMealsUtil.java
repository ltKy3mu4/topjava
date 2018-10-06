package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,10,0), "Завтрак", 2000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,13,0), "Обед", 600),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceeded.forEach(userMealWithExceed -> System.out.println(userMealWithExceed));



    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        Map<LocalDate,Integer> sumCaloriesPerDay = new HashMap<>();
        for (UserMeal um: mealList){
            if (sumCaloriesPerDay.containsKey(um.getDateTime().toLocalDate())){
                Integer dayCalories = sumCaloriesPerDay.get(um.getDateTime().toLocalDate())+um.getCalories();
                sumCaloriesPerDay.put(um.getDateTime().toLocalDate(),dayCalories);
            }
            else {
                sumCaloriesPerDay.put(um.getDateTime().toLocalDate(),um.getCalories());
            }
        }
        for (UserMeal um:mealList){
            userMealWithExceeds.add(new UserMealWithExceed(um.getDateTime(),um.getDescription(),um.getCalories(),sumCaloriesPerDay.get(um.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        List<UserMealWithExceed> filteredMeal = userMealWithExceeds.stream().
                filter(userMealWithExceed -> TimeUtil.isBetween(userMealWithExceed.getDateTime().toLocalTime(),startTime,endTime)).collect(Collectors.toList());

        return filteredMeal;
    }
}
