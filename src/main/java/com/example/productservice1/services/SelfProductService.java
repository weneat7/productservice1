package com.example.productservice1.services;

import com.example.productservice1.exceptions.ProductNotExistException;
import com.example.productservice1.models.Category;
import com.example.productservice1.models.Product;
import com.example.productservice1.repositories.CategoryRepository;
import com.example.productservice1.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("SelfProductService")
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotExistException("Product with id: "+id+ ", does not exist");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();
        if(category.getId() == null){
            Optional<Category> optionalId = categoryRepository.findByName(category.getName());
            if(optionalId.isPresent()){
                category.setId(optionalId.get().getId());
            }
             else category = categoryRepository.save(category);
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        Product productToReplace = getSingleProduct(id);
        productToReplace.setTitle(product.getTitle());
        productToReplace.setPrice(product.getPrice());
        productToReplace.setDescription(product.getDescription());
        productToReplace.setImageUrl(product.getImageUrl());
        productToReplace.setCategory(product.getCategory());
        return productRepository.save(productToReplace);
    }

    @Override
    public Product patchProduct(Product product, Long id) {
        Product productToPatch = getSingleProduct(id);
        if(product.getTitle() != null){
            productToPatch.setTitle(product.getTitle());
        }
        if(product.getPrice() != null){
            productToPatch.setPrice(product.getPrice());
        }
        if(product.getDescription() != null){
            productToPatch.setDescription(product.getDescription());
        }
        if(product.getImageUrl() != null){
            productToPatch.setImageUrl(product.getImageUrl());
        }
        if(product.getCategory() != null){
            productToPatch.setCategory(product.getCategory());
        }
        return productRepository.save(productToPatch);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productToDelete = getSingleProduct(id);
        productToDelete.setDeleted(true);
        return productRepository.save(productToDelete);
    }

    @Override
    public List<Product> addAllProducts(List<Product> products) {
        List<Product> ans = new ArrayList<>();
        for (Product product : products) {
           ans.add(createProduct(product));
        }
        return ans;

//        return productRepository.saveAll(products);
    }
}
