package com.example.productservice1.exceptions;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String me){
        super(me);
    }
}
