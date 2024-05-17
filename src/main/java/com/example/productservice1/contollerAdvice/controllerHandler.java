package com.example.productservice1.contollerAdvice;

import com.example.productservice1.dtos.ProductNotFountDto;
import com.example.productservice1.exceptions.ProductNotExistException;
import com.example.productservice1.exceptions.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class controllerHandler {

    @ExceptionHandler(ProductNotExistException.class)
    ResponseEntity<ProductNotFountDto> productNotExistExceptionHandle(){
        return new ResponseEntity<>(new ProductNotFountDto("ProductNotFound"), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotExistException.class)
    ResponseEntity<ProductNotFountDto> userNotExistExceptionHandle(){
        return new ResponseEntity<>(new ProductNotFountDto("UserNotFound"), HttpStatus.OK);
    }
}
