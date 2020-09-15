package com.thoughtworks.basicQuiz.Service;

import com.thoughtworks.basicQuiz.Exception.UserNoExistException;
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
    private static List<Education> educationMap = new ArrayList<Education>();

    public ResumeService(){
        userMap.put((long) 1, new User((long)1, "KAMIL",(long)24,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque " +
                        "distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores " +
                        "voluptatem dolorum! Quasi."));
        educationMap.add(new Education((long)1,(long)1990,"I was born in Katowice",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sapiente, exercitationem, " +
                        "totam, dolores iste dolore est aut modi."));
        educationMap.add(new Education((long)1,(long)2005,"Secondary school specializing in artistic",
                "Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus."));
        educationMap.add(new Education((long)1,(long)2009,"First level graduation in Graphic Design",
                "Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat " +
                        "quibusdam perferendis? Iusto, quibusdam asperiores unde repellat."));
        educationMap.add(new Education((long)1,(long)2012,"Second level graduation in Graphic Design",
                "Ducimus, aliquam tempore autem itaque et accusantium!"));
    }

    public User getUserById(Long id) {
        if (userMap.get(id) == null) {
            throw new UserNoExistException();
        }
        return userMap.get(id);
    }

    public List<Education> getEducationInformationById (Long id) {
        if (userMap.get(id) == null) {
            throw new UserNoExistException();
        }else{
            List<Education> resultList = new ArrayList<>();
            for (var education : educationMap) {
                if (education.getUserId() == id) {
                    resultList.add(education);
                }
            }
            return resultList;
        }
    }

    public void addNewUser(User user){
        userMap.put(user.getId(), user);
    }

    public void addNewEducationInformation(Education education){
        educationMap.add(education);
    }
}
