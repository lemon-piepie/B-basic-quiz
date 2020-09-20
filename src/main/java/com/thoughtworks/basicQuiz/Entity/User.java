package com.thoughtworks.basicQuiz.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.TABLE)
    private Long id;

    @Size(max =128, message = "用户名不能超过128位")
    @NotNull(message = "用户名不能为空")
    private String name;

    @Min(16)
    private Long age;

    @Size(min = 8, max =512, message = "头像地址不合法")
    private String avatar;

    @Size(min = 0, max =1024, message = "请输入自我介绍，长度不大于1024位")
    private String description;
}
