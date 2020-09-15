package com.thoughtworks.basicQuiz.Exception;

public class UserNoExistException extends RuntimeException{
    public UserNoExistException() {
        super("该用户不存在");
    }
}
