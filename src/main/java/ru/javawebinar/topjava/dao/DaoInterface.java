package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface DaoInterface {

    void addMeal(Meal meal);

    void deleteMeal(String mealId);

    void updateMeal(Meal meal);

    List<MealWithExceed> getAllMealsWithExceed();

    Meal findMealById(String mealId);

}
