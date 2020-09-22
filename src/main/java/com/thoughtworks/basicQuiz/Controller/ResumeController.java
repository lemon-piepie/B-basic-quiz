package com.thoughtworks.basicQuiz.Controller;

import com.thoughtworks.basicQuiz.Entity.Education;
import com.thoughtworks.basicQuiz.Entity.User;
import com.thoughtworks.basicQuiz.Service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ResumeController {
    ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/users/{id}")
    public Object getUserInformation(@PathVariable Long id) {
        return resumeService.getUserById(id);
    }

    @GetMapping("/users/{id}/educations")
    public Object getEducationInformation(@PathVariable Long id) {
        return resumeService.getEducationInformationById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserInformation(@RequestBody User user) {
        resumeService.addNewUser(user);
    }

    @PostMapping("/users/{id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEducationInformation(@RequestBody Education education) {
        resumeService.addNewEducationInformation(education);
    }
}
