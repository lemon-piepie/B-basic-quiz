package com.thoughtworks.capability.gtb.springdatajpaintro;

import com.thoughtworks.capability.gtb.springdatajpaintro.Entity.Education;
import com.thoughtworks.capability.gtb.springdatajpaintro.Entity.User;
import com.thoughtworks.capability.gtb.springdatajpaintro.Repository.EducationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class EducationRepositoryTest {
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_return_education_when_user_exists() {
        User user = User.builder()
                .id(10L)
                .name("Tommy")
                .age(20L)
                .avatar("http://...")
                .description("He is a optimistic young man")
                .build();
        entityManager.persistAndFlush(Education.builder()
                .userId(10L)
                .year(2018L)
                .title("University")
                .description("Major in Computer Science.")
                .user(user)
                .build());

        Optional<List<Education>> founds = educationRepository.findALlByUserId(10L);

        assertThat(founds.isPresent()).isTrue();
        assertThat(founds.get().get(0)).isEqualTo(Education.builder()
                .userId(10L)
                .year(2018L)
                .title("University")
                .description("Major in Computer Science.")
                .user(user)
                .build());
    }
}
