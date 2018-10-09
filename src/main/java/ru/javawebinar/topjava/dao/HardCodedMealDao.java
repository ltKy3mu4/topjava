package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HardCodedMealDao implements DaoInterface {
    private AtomicInteger idCounter = new AtomicInteger(0);

    private Map<Integer, Meal> mealMap;

    public HardCodedMealDao() {
        mealMap = new ConcurrentHashMap<>();
        mealMap.put(idCounter.incrementAndGet(), new Meal(idCounter.get(),LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealMap.put(idCounter.incrementAndGet(), new Meal(idCounter.get(),LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealMap.put(idCounter.incrementAndGet(), new Meal(idCounter.get(),LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealMap.put(idCounter.incrementAndGet(), new Meal(idCounter.get(),LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealMap.put(idCounter.incrementAndGet(), new Meal(idCounter.get(),LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealMap.put(idCounter.incrementAndGet(), new Meal(idCounter.get(),LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public void addMeal(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(Integer mealId) {
        mealMap.remove(mealId);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealMap.remove(meal.getId());
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public Map<Integer, Meal> getAllMeals() {
        return mealMap;
    }

    @Override
    public Integer getNextId() {
        return idCounter.incrementAndGet();
    }
}