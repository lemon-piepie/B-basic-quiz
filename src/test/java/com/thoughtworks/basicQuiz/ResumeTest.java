package com.thoughtworks.basicQuiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.basicQuiz.dto.Education;
import com.thoughtworks.basicQuiz.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResumeTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetUserById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(jsonPath("$.name",is("KAMIL")))
                .andExpect(jsonPath("$.age",is(24)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetEducationInformation() throws Exception {
        mockMvc.perform(get("/users/1/educations"))
                .andExpect(jsonPath("$[0].userId",is(1)))
                .andExpect(jsonPath("$[0].year",is(1990)))
                .andExpect(jsonPath("$[1].year",is(2005)))
                .andExpect(jsonPath("$[2].year",is(2009)))
                .andExpect(jsonPath("$[3].year",is(2012)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddNewUser() throws Exception {
        User user = new User((long)2, "Tommy",(long)17,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "I am a easy-going boy.");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/users/2"))
                .andExpect(jsonPath("$.name",is("Tommy")))
                .andExpect(jsonPath("$.age",is(17)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddEducationInformation() throws Exception {
        Education education = new Education((long)1,(long)2015,"I got Doctor degree",
                "I major in Computer Science and got Doctor degree.");
        ObjectMapper objectMapper = new ObjectMapper();
        String educationJson = objectMapper.writeValueAsString(education);

        mockMvc.perform(post("/users/1/educations")
                .content(educationJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/users/1/educations"))
                .andExpect(jsonPath("$[4].userId",is(1)))
                .andExpect(jsonPath("$[4].year",is(2015)))
                .andExpect(status().isOk());
    }
}
