package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceImpl.class);

    @Autowired
    private MealRepository repository;


    @Override
    public Meal create(Meal meal, int loggedinUserId) {
        meal.setUserId(loggedinUserId);
        return repository.save(meal);
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        Meal deletedMeal = repository.get(mealId);
        if (deletedMeal == null) {
            LOG.warn("the required element for deleting was not found");
            throw new NotFoundException("element " + mealId + " was not found");
        } else {
            if (deletedMeal.getUserId().equals(userId)) {
                repository.delete(mealId);
                LOG.info("The element " + mealId + " was successfully deleted");
            } else {
                LOG.warn("The user " + SecurityUtil.authUserId() + "  doesn't have permission to delete the element " + mealId);
                throw new NotFoundException("element " + mealId + " cannot be deleted by this user");
            }
        }
    }

    @Override
    public Meal get(int mealId, int loggedinUserId) throws NotFoundException {
        Meal chosenMeal = repository.get(mealId);
        if (chosenMeal == null) {
            LOG.warn("the required element for deleting was not found");
            throw new NotFoundException("element " + mealId + " was not found");
        } else {
            if (chosenMeal.getUserId().equals(loggedinUserId)) {
                LOG.info("The element " + mealId + " was chosen");
                return repository.get(mealId);
            } else {
                LOG.warn("The user with id #" + loggedinUserId + " doesn't have permission to get the element " + mealId);
                return null;
            }
        }
    }

    @Override
    public void update(Meal meal, int mealId, int loggedinUserId) {
        Meal updatedMeal = repository.get(mealId);
        if (updatedMeal == null) {
            create(meal, loggedinUserId);
        } else {
            if (updatedMeal.getUserId().equals(loggedinUserId)) {
                repository.delete(meal.getId());
                repository.save(new Meal(updatedMeal.getId(), updatedMeal.getDateTime(), updatedMeal.getDescription(), updatedMeal.getCalories(), updatedMeal.getUserId()));
            } else {
                LOG.warn("The user " + SecurityUtil.authUserId() + "  doesn't have permission to modify the element " + meal.getId());
            }
        }

    }

    @Override
    public List<MealWithExceed> getAllWithExceeded(int loggedinUserId) {
        return MealsUtil.getWithExceeded(repository.getAll(loggedinUserId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }




}