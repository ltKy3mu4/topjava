package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    Meal create(Meal meal, int loggedinUserId);

    void delete(int mealId, int userId) throws NotFoundException;

    Meal get(int mealId, int userId) throws NotFoundException;

    void update(Meal meal, int mealId, int userId);

    List<MealWithExceed> getAllWithExceeded(int loggedinUserId);


}