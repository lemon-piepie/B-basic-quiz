package com.thoughtworks.basicQuiz.Entity;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Builder
public class Education {
    private Long userId;

    private Long year;

    private String title;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
}
