package com.example.productservice1.dtos;

public class ProductNotFountDto {
    private String message;

    public ProductNotFountDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
