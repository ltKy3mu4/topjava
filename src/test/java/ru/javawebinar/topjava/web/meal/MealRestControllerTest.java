package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.TestUtil.readFromJson;


class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL+"/";

    @Test
    void testGet()throws Exception {
        mockMvc.perform(get(REST_URL+ MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(MealTestData.contentJson(MealTestData.MEAL1));
                .andExpect(content().json(JsonUtil.writeValue(MEAL1)));
    }


    @Test
    void testDelete() throws Exception{
        mockMvc.perform(delete(REST_URL+MEAL1_ID))
                .andExpect(status().isNoContent())
                .andDo(print());
        MealTestData.assertMatch(mealService.getAll(UserTestData.USER_ID),MEAL6,MEAL5,MEAL4,MEAL3,MEAL2);

    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL2);
        updated.setDescription("Измененное");
        mockMvc.perform(put(REST_URL+updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        assertMatch(mealService.get(updated.getId(),SecurityUtil.authUserId()), updated);
    }

    @Test
    void testCreate() throws Exception {
        Meal created = getCreated();
        ResultActions resultActions = mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        Meal returned = readFromJson (resultActions,Meal.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), created, MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1);

    }

    @Test
    void testFilter() throws Exception {
        String start = "2015-05-31T00:00:30";
        String end = "2015-06-01T23:00:30";
        String expectedJsons = JsonUtil.writeIgnoreProps(List.of(MEAL6,MEAL5,MEAL4));
        ResultActions action = mockMvc.perform(get(REST_URL +"filter/"+ start + "/" + end))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJsons));

    }

}