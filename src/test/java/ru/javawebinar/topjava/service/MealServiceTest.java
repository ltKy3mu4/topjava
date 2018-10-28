package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal =service.get(MealTestData.USER_MEAL_1, SecurityUtil.authUserId());
        MealTestData.assertMatch(meal,MealTestData.UMEAL1);
    }

    @Test (expected = NotFoundException.class)
    public void getOtherUserMeal() {
        Meal meal =service.get(MealTestData.USER_MEAL_1, 100001);
        MealTestData.assertMatch(meal,MealTestData.UMEAL1);
    }

    @Test (expected = NotFoundException.class)
    public void deleteOtherUserMeal() {
        service.delete(MealTestData.USER_MEAL_1, 100001);
    }


    @Test (expected = NotFoundException.class)
    public void updateOtherUserMeal() {
        service.update(MealTestData.UMEAL1, 100001);
    }

    @Test
    public void delete() {
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(SecurityUtil.authUserId());
        MealTestData.assertMatch(meals, MealTestData.UMEAL3, MealTestData.UMEAL2, MealTestData.UMEAL4, MealTestData.UMEAL1);
    }

    @Test
    public void update() {
    }

    @Test
    public void create() {
    }
}