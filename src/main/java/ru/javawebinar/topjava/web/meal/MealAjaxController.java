package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/meals")
public class MealAjaxController extends AbstractMealController{

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("dateTime") String dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") Integer calories) {
//TODO: there is an error related to autoparsing of the dateTime variable by spring
        Meal meal = new Meal(id,  LocalDateTime.parse(dateTime), description, calories);

        if (meal.isNew()) {
            super.create(meal);
        }
    }

    @GetMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public  List<MealTo> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "startTime", required = false) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        System.out.println(startDate+" " +endDate);
        return super.getBetween(startDate, startTime, endDate, endTime);
//        return super.getAll();
    }


}
