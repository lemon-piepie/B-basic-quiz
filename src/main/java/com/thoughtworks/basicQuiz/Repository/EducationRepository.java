package com.thoughtworks.basicQuiz.Repository;

import com.thoughtworks.basicQuiz.Entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    Optional<List<Education>> findALlByUserId(Long userId);
}
