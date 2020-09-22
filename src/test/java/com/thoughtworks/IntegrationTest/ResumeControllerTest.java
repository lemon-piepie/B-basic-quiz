package com.thoughtworks.capability.gtb.springdatajpaintro;

import com.thoughtworks.capability.gtb.springdatajpaintro.Controller.ResumeController;
import com.thoughtworks.capability.gtb.springdatajpaintro.Entity.Education;
import com.thoughtworks.capability.gtb.springdatajpaintro.Entity.User;
import com.thoughtworks.capability.gtb.springdatajpaintro.Error.UserNoExistException;
import com.thoughtworks.capability.gtb.springdatajpaintro.Service.ResumeService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResumeController.class)
@AutoConfigureJsonTesters
public class ResumeControllerTest {

    @MockBean
    private ResumeService resumeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<User> userJson;
    @Autowired
    private JacksonTester<List<Education>> educationsJson;
    @Autowired
    private JacksonTester<Education> educationJson;

    private User firstUser;
    private Education firstEducation;
    private List<Education> educationsForFirstUser = new ArrayList<Education>();

    @BeforeEach
    public void beforeEach() {
        firstUser = User.builder()
                .id(10L)
                .name("Tommy")
                .age(20L)
                .avatar("http://...")
                .description("He is a optimistic young man")
                .build();
        firstEducation = Education.builder()
                .userId(10L)
                .year(2018L)
                .title("University")
                .description("Major in Computer Science.")
                .user(firstUser)
                .build();
        educationsForFirstUser.add(firstEducation);
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(resumeService);
    }

    @Nested
    class GetUserById {

        @Nested
        class WhenUserIdExists {

            @Test
            public void should_return_user_by_id_with_jsonPath() throws Exception {
                when(resumeService.getUserById(10L)).thenReturn(firstUser);

                mockMvc.perform(get("/users/{id}", 10L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name", is("Tommy")))
                        .andExpect(jsonPath("$.age", is(20)))
                        .andExpect(jsonPath("$.avatar", is("http://...")));

                verify(resumeService).getUserById(10L);
            }

            @Test
            public void should_return_user_by_id_with_jacksontester() throws Exception {
                when(resumeService.getUserById(10L)).thenReturn(firstUser);

                MockHttpServletResponse response = mockMvc.perform(get("/users/{id}", 10))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(
                        userJson.write(firstUser).getJson());

                verify(resumeService).getUserById(10L);
            }
        }

        @Nested
        class WhenUserIdNotExisted {

            @Test
            public void should_return_NOT_FOUND() throws Exception {
                when(resumeService.getUserById(1L)).thenThrow(new UserNoExistException());

                mockMvc.perform(get("/users/{id}", 1L))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.message", containsString("用户不存在")));

                verify(resumeService).getUserById(1L);
            }
        }
    }

    @Nested
    class CreateUser {

        private User newUser;

        @BeforeEach
        public void beforeEach() {
            newUser = User.builder()
                    .id(15L)
                    .name("Lily")
                    .age(19L)
                    .avatar("http://...")
                    .description("A pretty girl")
                    .build();
        }

        @Nested
        class WhenRequestIsValid {

            @Test
            public void should_create_new_user_and_return_its_id() throws Exception {
                when(resumeService.addNewUser(newUser)).thenReturn(15L);

                MockHttpServletRequestBuilder requestBuilder = post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson.write(newUser).getJson());

                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

                verify(resumeService).addNewUser(newUser);
            }
        }
    }

    @Nested
    class GetEducationInformationByUserId {

        @Nested
        class WhenUserIdExists {

            @Test
            public void should_return_education_by_user_id_with_jsonPath() throws Exception {
                when(resumeService.getEducationInformationById(10L)).thenReturn(educationsForFirstUser);

                mockMvc.perform(get("/users/{id}/educations", 10L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$[0].userId", is(10)))
                        .andExpect(jsonPath("$[0].year", is(2018)))
                        .andExpect(jsonPath("$[0].title", is("University")))
                        .andExpect(jsonPath("$[0].user.name", is("Tommy")));

                verify(resumeService).getEducationInformationById(10L);
            }

            @Test
            public void should_return_user_by_id_with_jacksontester() throws Exception {
                when(resumeService.getEducationInformationById(10L)).thenReturn(educationsForFirstUser);

                MockHttpServletResponse response = mockMvc.perform(get("/users/{id}/educations", 10))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(
                        educationsJson.write(educationsForFirstUser).getJson());

                verify(resumeService).getEducationInformationById(10L);
            }
        }

        @Nested
        class WhenUserIdNotExisted {

            @Test
            public void should_return_NOT_FOUND() throws Exception {
                when(resumeService.getEducationInformationById(1L)).thenThrow(new UserNoExistException());

                mockMvc.perform(get("/users/{id}/educations", 1L))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.message", containsString("用户不存在")));

                verify(resumeService).getEducationInformationById(1L);
            }
        }
    }

    @Nested
    class AddEducationInfo {

        private Education newEducationInfo;

        @BeforeEach
        public void beforeEach() {
            newEducationInfo = Education.builder()
                                .userId(10L)
                                .year(2020L)
                                .title("Change Major")
                                .description("Major in Economic.")
                                .user(firstUser)
                                .build();
        }

        @Nested
        class WhenRequestIsValid {

            @Test
            public void should_create_new_user_and_return_its_id() throws Exception {
                when(resumeService.addNewEducationInformation(newEducationInfo)).thenReturn(10L);

                MockHttpServletRequestBuilder requestBuilder = post("/users/{id}/educations", 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(educationJson.write(newEducationInfo).getJson());

                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

                verify(resumeService).addNewEducationInformation(newEducationInfo);
            }
        }
    }
}
