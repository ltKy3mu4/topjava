package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_1 = START_SEQ+2;
    public static final int USER_MEAL_2 = START_SEQ+3;
    public static final int USER_MEAL_3 = START_SEQ+4;
    public static final int USER_MEAL_4 = START_SEQ+5;
    public static final int ADMIN_MEAL_1 = START_SEQ+6;
    public static final int UNEXISTED = 2;

    public static final Meal UMEAL1 = new Meal(USER_MEAL_1, LocalDateTime.of(2018,10,10,14,0,0), "полдник",500);
    public static final Meal UMEAL2 = new Meal(USER_MEAL_2, LocalDateTime.of(2018,10,12,7,0,0), "завтрак",600);
    public static final Meal UMEAL3 = new Meal(USER_MEAL_3, LocalDateTime.of(2018,10,12,13,0,0), "обед",500);
    public static final Meal UMEAL4 = new Meal(USER_MEAL_4, LocalDateTime.of(2018,10,11,14,0,0), "недообед",500);

    public static final Meal ADMINMEAL = new Meal(ADMIN_MEAL_1, LocalDateTime.of(2018,10,12,7,0,0), "хххх",600 );

    public static final Meal UNXISTEDMEAL = new Meal(UNEXISTED, LocalDateTime.of(2018,10,12,7,0,0), "завтрак",600);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

}
