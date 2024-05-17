package com.example.productservice1.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.example.completebackendspringboot.product.models.Product}
 */

@Setter
@Getter
public class FakeStoreProductDto implements Serializable {
    @JsonProperty("id")
    Long id;

    String title;
    Long price;
    String category;
    String description;
    String image;
}