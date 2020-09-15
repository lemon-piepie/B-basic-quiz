package com.thoughtworks.basicQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Long id;

    @Size(min = 1, max =16, message = "用户名不能超过16位")
    @NotNull(message = "用户名不能为空")
    private String name;

    @Min(16)
    private Long age;

    @Size(min = 1, max =64, message = "头像地址不合法")
    private String avatar;

    @Size(min = 1, max =128, message = "请输入自我介绍，长度不大于128位")
    private String description;
}
