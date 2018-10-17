package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public List<MealWithExceed> getAllWithExceeded() {
        return service.getAllWithExceeded(SecurityUtil.authUserId());
    }

    public List<MealWithExceed> getFilteredMealsByTime (LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getFilteredWithExceeded(service.getAll(SecurityUtil.authUserId()),MealsUtil.DEFAULT_CALORIES_PER_DAY,startTime,endTime);
    }

    public Meal get(int id) { return service.get(id, SecurityUtil.authUserId()); }

    public Meal create(Meal meal) {
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        service.delete(id,SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) { service.update(meal,id, SecurityUtil.authUserId());    }
}