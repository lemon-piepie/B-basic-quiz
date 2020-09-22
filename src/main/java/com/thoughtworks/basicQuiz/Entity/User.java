package com.thoughtworks.basicQuiz.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private Long age;

    private String avatar;

    private String description;
}
