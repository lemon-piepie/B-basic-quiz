package com.thoughtworks.basicQuiz.Controller;

import com.thoughtworks.basicQuiz.Service.ResumeService;
import com.thoughtworks.basicQuiz.dto.Education;
import com.thoughtworks.basicQuiz.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class ResumeController {
    ResumeService resumeService;

    public ResumeController (ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/users/{id}")
    public User getUserInformation(@PathVariable Long id) {
        return resumeService.getUserById(id);
    }

    @GetMapping("/users/{id}/educations")
    public List<Education> getEducationInformation(@PathVariable Long id) {
        return resumeService.getEducationInformationById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserInformation(@RequestBody @Valid User user) {
        resumeService.addNewUser(user);
    }

    @PostMapping("/users/{id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEducationInformation(@RequestBody @Valid Education education) {
        resumeService.addNewEducationInformation(education);
    }
}
