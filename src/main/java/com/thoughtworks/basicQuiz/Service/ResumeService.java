package com.thoughtworks.basicQuiz.Service;

import com.thoughtworks.basicQuiz.Entity.Education;
import com.thoughtworks.basicQuiz.Entity.User;
import com.thoughtworks.basicQuiz.Exception.UserNoExistException;
import com.thoughtworks.basicQuiz.Repository.EducationRepository;
import com.thoughtworks.basicQuiz.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ResumeService {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    public ResumeService(UserRepository userRepository, EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findOneById(id)
                .orElseThrow(() -> new UserNoExistException());
    }

    public List<Education> getEducationInformationById (Long id) {
        return educationRepository.findALlByUserId(id)
                .orElseThrow(() -> new UserNoExistException());
    }

    public Long addNewUser(User user){
        userRepository.save(user);
        return user.getId();
    }

    public Long addNewEducationInformation(Education education){
        educationRepository.save(education);
        return education.getUserId();
    }
}
