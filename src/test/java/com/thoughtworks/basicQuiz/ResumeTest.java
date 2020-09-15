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

    @Test
    void userNameShouldNotEmpty() throws Exception {
        User user = new User((long)2, null,(long)17,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "I am a easy-going boy.");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void userNameShouldNotLongerThan128() throws Exception {
        User user = new User((long)2, "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzz",(long)17,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "I am a easy-going boy.");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void userAgeShouldNotLessThan16() throws Exception {
        User user = new User((long)2, "Lily",(long)13,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "I am a optimistic girl.");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void userAvatarShouldNotLesserThan8() throws Exception {
        User user = new User((long)2, "Lily",(long)19,
                "https:/",
                "I am a optimistic girl.");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void userAvatarShouldNotLongerThan512() throws Exception {
        User user = new User((long)2, "Lily",(long)19,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/00000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                "I am a optimistic girl.");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void userDescriptionShouldNotLongerThan1024() throws Exception {
        User user = new User((long)2, "Lily",(long)19,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void educationUserIdShouldNotNull() throws Exception {
        Education education = new Education(null,(long)2015,"I got Doctor degree",
                "I major in Computer Science and got Doctor degree.");
        ObjectMapper objectMapper = new ObjectMapper();
        String educationJson = objectMapper.writeValueAsString(education);

        mockMvc.perform(post("/users/1/educations")
                .content(educationJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void educationYearShouldNotNull() throws Exception {
        Education education = new Education((long)1,null,"I got Doctor degree",
                "I major in Computer Science and got Doctor degree.");
        ObjectMapper objectMapper = new ObjectMapper();
        String educationJson = objectMapper.writeValueAsString(education);

        mockMvc.perform(post("/users/1/educations")
                .content(educationJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void educationTopicShouldNotLongerThan256() throws Exception {
        Education education = new Education((long)1,(long)2015,"I got Doctor degree.I got Doctor degree." +
                "I got Doctor degree.I got Doctor degree.I got Doctor degree.I got Doctor degree.I got Doctor degree." +
                "I got Doctor degree.I got Doctor degree.I got Doctor degree.I got Doctor degree.I got Doctor degree." +
                "I got Doctor degree.I got Doctor degree.I got Doctor degree.",
                "I major in Computer Science and got Doctor degree.");
        ObjectMapper objectMapper = new ObjectMapper();
        String educationJson = objectMapper.writeValueAsString(education);

        mockMvc.perform(post("/users/1/educations")
                .content(educationJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void educationDescriptionShouldNotLongerThan4096() throws Exception {
        Education education = new Education((long)1,(long)2015,"I got Doctor degree.",
                "I major in Computer Science and got Doctor degree.I am a optimistic girl.I am a " +
                        "optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimis" +
                        "tic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic girl.I am a optimistic " +
                        "girl.I am a optimistic girl.I am a optimistic girl.");
        ObjectMapper objectMapper = new ObjectMapper();
        String educationJson = objectMapper.writeValueAsString(education);

        mockMvc.perform(post("/users/1/educations")
                .content(educationJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
