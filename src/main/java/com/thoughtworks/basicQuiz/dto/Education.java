package com.thoughtworks.basicQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    @NotNull(message="请选择加入教育经历的用户ID")
    private Long userId;

    @NotNull(message="年份不能为空")
    private Long year;

    @Size(min = 1, max =256, message = "标题长度不多于256字")
    private String title;

    @Size(min = 1, max =4096, message = "请输入教育经历详情介绍，不多于4096字")
    private String description;
}
