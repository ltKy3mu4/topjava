package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.Map;

public interface DaoInterface {

    void addMeal(Meal meal);

    void deleteMeal(Integer mealId);

    void updateMeal(Meal meal);

    Map<Integer,Meal> getAllMeals();

    Integer getNextId();
}
