package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.DaoInterface;
import ru.javawebinar.topjava.dao.HardCodedMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealServlet extends HttpServlet {
    private DaoInterface dao;
    private static String MEALS_TABLE = "/meals.jsp";
    private static String INSERT_OR_EDIT = "/addMeal.jsp";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new HardCodedMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "mealList";
        }
        switch (action){
            case ("delete"): {
                Integer userId = Integer.parseInt(req.getParameter("id"));
                dao.deleteMeal(userId);
                resp.sendRedirect(req.getContextPath()+"/meals");
                break;
            }
            case ("edit"): {
                Integer userId = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("meal", dao.getAllMeals().get(userId));
                req.getRequestDispatcher(INSERT_OR_EDIT).forward(req, resp);
                break;
            }
            case ("mealList"): {
                req.setAttribute("mealList", getAllMealsWithExceed());
                req.getRequestDispatcher(MEALS_TABLE).forward(req, resp);
                break;
            }
            default: {
                req.getRequestDispatcher(INSERT_OR_EDIT).forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime localDateTime;
        try {
            String income = req.getParameter("dateTime");
            income = income.split("T")[0] + " " + income.split("T")[1];
            localDateTime = LocalDateTime.parse(income, formatter);

        } catch (Exception ex) {
            ex.printStackTrace();
            localDateTime = LocalDateTime.of(1900, Month.APRIL, 14, 0, 0);
        }

        String desc = "";
        if (req.getParameter("description") != null && !req.getParameter("description").isEmpty()) {
            desc = req.getParameter("description");
        }
        int calories = 0;

        try {
            calories = Integer.parseInt(req.getParameter("calories"));
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        Integer mealId;
        String id = req.getParameter("id");
        if (id.equals("")  || id.isEmpty()){
            mealId=0;
        }
        else {
            mealId = Integer.parseInt(req.getParameter("id"));
        }

        if (mealId == 0) {
            dao.addMeal(new Meal(dao.getNextId(),localDateTime, desc, calories));
        } else {
            dao.updateMeal(new Meal(mealId,localDateTime, desc, calories));
        }

        RequestDispatcher view = req.getRequestDispatcher(MEALS_TABLE);
        req.setAttribute("mealList", getAllMealsWithExceed());
        view.forward(req, resp);
    }

    private List<MealWithExceed> getAllMealsWithExceed(){
        Map<Integer, Meal> allMeals = dao.getAllMeals();
        Map<LocalDate, Integer> caloriesPerDay = new HashMap<>();

        for (Map.Entry<Integer,Meal> entry: allMeals.entrySet()){
             Meal meal = entry.getValue();
             if (caloriesPerDay.containsKey(meal.getDateTime().toLocalDate())){
                 Integer newCalories = caloriesPerDay.get(meal.getDateTime().toLocalDate())+meal.getCalories();
                 caloriesPerDay.put(meal.getDateTime().toLocalDate(),newCalories);
             }
             else {
                 caloriesPerDay.put(meal.getDateTime().toLocalDate(),meal.getCalories());
             }
        }

        List< MealWithExceed> allMealsWithExceeded = new ArrayList<>();
        for (Map.Entry<Integer,Meal> entry: allMeals.entrySet()){
            Meal meal = entry.getValue();
            allMealsWithExceeded.add(
                    new MealWithExceed(meal.getId(),meal.getDateTime(),meal.getDescription(),meal.getCalories(),
                            caloriesPerDay.get(meal.getDateTime().toLocalDate()) > 2000));
        }
        return  allMealsWithExceeded;
    }

}
