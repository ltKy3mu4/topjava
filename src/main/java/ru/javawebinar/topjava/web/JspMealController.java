package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractController {

    private MealService service;

    public JspMealController(MealService service) {
        super(service);
        this.service=service;
    }

    @GetMapping("/meals")
    private String showMainUserMenu(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
        return "meals";
    }

    @GetMapping("/meals/delete")
    private String deleteMeal(HttpServletRequest request) {
        service.delete(Integer.valueOf(request.getParameter("id")), SecurityUtil.authUserId());
        return "redirect:/meals";
    }



    @GetMapping("/meals/update")
    private String updateOrAdd(HttpServletRequest request, Model model) {
        if (!(request.getParameter("id") ==null)) {
            int mealId = Integer.valueOf(request.getParameter("id"));
            Meal meal = get(mealId);
            model.addAttribute("meal", meal);
        }
        return "mealForm";
    }

    @PostMapping("/meals/update")
    private String executeUpdateOrAdd(HttpServletRequest request) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            meal.setId(Integer.valueOf(request.getParameter("id")));
            service.update(meal, SecurityUtil.authUserId());
        }

        return "redirect:/meals";
    }

    @PostMapping("meals/filter")
    private String filterMeals(HttpServletRequest request,Model model){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals",getBetween(startDate, startTime, endDate, endTime));
//        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
            return "meals";
    }


}
