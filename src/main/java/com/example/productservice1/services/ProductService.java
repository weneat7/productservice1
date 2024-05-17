package com.example.productservice1.services;

import com.example.productservice1.models.Product;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(Long id);

    public List<Product> getAllProducts();

    public Product createProduct(Product product);

    public Product replaceProduct(Product product, Long id);

    public Product patchProduct(Product product, Long id);

    public Product deleteProduct(Long id);

    public List<Product> addAllProducts(List<Product> products);
}
