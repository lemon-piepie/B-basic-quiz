package com.thoughtworks.basicQuiz.Service;

import com.thoughtworks.basicQuiz.dto.Education;
import com.thoughtworks.basicQuiz.dto.User;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResumeService {

    private static Map<Long, User> userMap = new HashMap<>();


    public ResumeService(){
        userMap.put((long) 1, new User((long)1, "KAMIL",(long)24,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque " +
                        "distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores " +
                        "voluptatem dolorum! Quasi."));
        
    }

    public User getUserById(Long id) {
        return userMap.get(id);
    }


}
