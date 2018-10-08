package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class HardCodedMealDao implements DaoInterface {
    private List<Meal> mealList;

    public HardCodedMealDao() {
        mealList = new CopyOnWriteArrayList<>();
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public void addMeal(Meal meal) {
        getMealData().add(meal);
    }

    @Override
    public void deleteMeal(String mealId) {
        Meal removedMeal = findMealById(mealId);
        getMealData().remove(removedMeal);
    }

    @Override
    public void updateMeal(Meal meal) {
        getMealData().remove(findMealById(meal.getId()));
        getMealData().add(meal);
    }

    @Override
    public List<MealWithExceed> getAllMealsWithExceed() {
        Map<LocalDate, Integer> caloriesSumByDate = getMealData().stream().collect(
                Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));
        List<MealWithExceed> mealWithExceedList = getMealData().stream()
                .map(meal -> new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId(), caloriesSumByDate.get(meal.getDateTime().toLocalDate()) > 2000))
                .collect(Collectors.toList());

        return mealWithExceedList.stream().sorted((o1, o2) -> o1.getLocalDateTime().isBefore(o2.getLocalDateTime()) ? -1 : 1).collect(Collectors.toList());
    }

    @Override
    public Meal findMealById(String id) {
        List<Meal> mealData = getMealData();
        Meal searchingMeal = getMealData().stream().filter(meal -> meal.getId().equals(id)).findFirst().get();
        return searchingMeal;
    }


    public List<Meal> getMealData() {
        return mealList;
    }


}
