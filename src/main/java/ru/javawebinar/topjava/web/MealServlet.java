package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.DaoInterface;
import ru.javawebinar.topjava.dao.HardCodedMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.HardCodedData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MealServlet extends HttpServlet {
    private DaoInterface dao;
    private static String MEALS_TABLE = "/meals.jsp";
    private static String INSERT_OR_EDIT = "/addMeal.jsp";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//    private DateTimeFormatter dateTimeFormatter = new DateTimeFormatter();

    public MealServlet() {
        super();
        //TODO: where should i set the implementation of DAO?
        dao = new HardCodedMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String forward = "";
        String action = req.getParameter("action");
        if (action == null) {
            action = "mealList";
        }
        if (action.equalsIgnoreCase("delete")) {
            forward = MEALS_TABLE;
            String userId = req.getParameter("id");
            dao.deleteMeal(userId);
            req.setAttribute("mealList", dao.getAllMealsWithExceed());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            String userId = req.getParameter("id");
            Meal meal = dao.findMealById(userId);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("mealList")) {
            forward = MEALS_TABLE;
            req.setAttribute("mealList", dao.getAllMealsWithExceed());
        } else {
            forward = INSERT_OR_EDIT;
        }
//        req.setAttribute("mealList", HardCodedData.getMealData());
        req.getRequestDispatcher(forward).forward(req, resp);
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

        String mealId = req.getParameter("id");

        if (mealId == null || mealId.isEmpty()) {
            dao.addMeal(new Meal(localDateTime, desc, calories));
        } else {
            dao.updateMeal(new Meal(localDateTime, desc, calories, mealId));
        }

        RequestDispatcher view = req.getRequestDispatcher(MEALS_TABLE);
        req.setAttribute("mealList", dao.getAllMealsWithExceed());
        view.forward(req, resp);
    }
}
