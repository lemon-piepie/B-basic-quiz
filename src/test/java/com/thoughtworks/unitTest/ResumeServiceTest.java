package com.thoughtworks.unitTest;

import com.thoughtworks.basicQuiz.Entity.Education;
import com.thoughtworks.basicQuiz.Entity.User;
import com.thoughtworks.basicQuiz.Exception.UserNoExistException;
import com.thoughtworks.basicQuiz.Repository.EducationRepository;
import com.thoughtworks.basicQuiz.Repository.UserRepository;
import com.thoughtworks.basicQuiz.Service.ResumeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResumeServiceTest {

    private ResumeService resumeService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EducationRepository educationRepository;

    private User user;
    private Education education;

    @BeforeEach
    void setUp() {
        resumeService = new ResumeService(userRepository,educationRepository);
        user = User.builder()
                .id(10L)
                .name("Tommy")
                .age(20L)
                .avatar("http://...")
                .description("He is a optimistic young man")
                .build();
        education = Education.builder()
                .userId(10L)
                .year(2018L)
                .title("University")
                .description("Major in Computer Science.")
                .user(user)
                .build();
    }

    @Nested
    class FindUserById {

        @Nested
        class WhenIdExists {

            @Test
            public void should_return_user() {
                when(userRepository.findOneById(123L)).thenReturn(Optional.of(user));

                User foundUser = resumeService.getUserById(123L);

                assertThat(foundUser).isEqualTo(User.builder()
                        .id(10L)
                        .name("Tommy")
                        .age(20L)
                        .avatar("http://...")
                        .description("He is a optimistic young man")
                        .build());
            }
        }

        @Nested
        class WhenUserIdNotExisted {

            @Test
            void should_throw_exception() {
                when(userRepository.findOneById(1L)).thenReturn(Optional.empty());

                UserNoExistException thrownException = assertThrows(UserNoExistException.class, () -> {
                    resumeService.getUserById(1L);
                });

                assertThat(thrownException.getMessage()).containsSequence("用户不存在");
            }
        }
    }

    @Nested
    class findEducationInformationById{
        @Nested
        class whenUserExists {
            @Test
            void should_return_education_information() {
                List<Education> educations = new ArrayList<Education>();
                educations.add(education);
                when(educationRepository.findALlByUserId(10L)).thenReturn(Optional.of(educations));

                List<Education> foundEducations = resumeService.getEducationInformationById(10L);

                assertThat(foundEducations.get(0)).isEqualTo(Education.builder()
                        .userId(10L)
                        .year(2018L)
                        .title("University")
                        .description("Major in Computer Science.")
                        .user(user)
                        .build());
            }
        }

        @Nested
        class whenUserNotExists {
            @Test
            void should_throw_exception () {
                when(educationRepository.findALlByUserId(1L)).thenReturn(Optional.empty());

                UserNoExistException thrownException = assertThrows(UserNoExistException.class, () -> {
                    resumeService.getEducationInformationById(1L);
                });

                assertThat(thrownException.getMessage()).containsSequence("用户不存在");
            }
        }
    }

    @Nested
    class addInformation {
        @Nested
        class addUserInformation {
            @Test
            void should_success () {
                User newUser = User.builder()
                        .id(15L)
                        .name("Lily")
                        .age(19L)
                        .avatar("http://...")
                        .description("A pretty girl")
                        .build();
                assertThat(resumeService.addNewUser(newUser)).isEqualTo(15L);
            }
        }

        @Nested
        class addEducationInformation {
            @Test
            void should_success () {
                Education newEducation = Education.builder()
                        .userId(10L)
                        .year(2020L)
                        .title("Change Major")
                        .description("Major in Economic.")
                        .user(user)
                        .build();
                assertThat(resumeService.addNewEducationInformation(newEducation)).isEqualTo(10L);
            }
        }
    }
}
