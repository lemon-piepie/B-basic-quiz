package com.thoughtworks.basicQuiz.Exception;

public class UserNoExistException extends RuntimeException {

    public UserNoExistException() {
        super("用户不存在");
    }

}
