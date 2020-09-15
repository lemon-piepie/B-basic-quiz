package com.thoughtworks.basicQuiz.Controller;

import com.thoughtworks.basicQuiz.Service.ResumeService;
import com.thoughtworks.basicQuiz.dto.Education;
import com.thoughtworks.basicQuiz.dto.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ResumeController {
    ResumeService resumeService;

    public ResumeController (ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/user/{id}")
    public User getUserInformation(@PathVariable Long id) {
        return resumeService.getUserById(id);
    }


}
