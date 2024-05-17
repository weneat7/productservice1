package com.example.productservice1.controllers;


import com.example.productservice1.models.Product;
import com.example.productservice1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;


    @Autowired
    public ProductController(@Qualifier("SelfProductService") ProductService productService){
        this.productService = productService;

    }   //DONE

    @GetMapping("/{id}")    //DONE
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
            Product product = productService.getSingleProduct(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/")    //DONE
    public ResponseEntity<List<Product>> getAllProducts(String token) {


        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create") //DONE
    public ResponseEntity<Product> createProduct(@RequestBody Product productDto) {
       Product product = productService.createProduct(productDto);
       return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/replace/{id}")    //DONE
    public ResponseEntity<Product> replaceProduct(@RequestBody Product productDto,@PathVariable("id") Long id) {
        Product product = productService.replaceProduct(productDto,id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")       //TODO: patch method
    public ResponseEntity<Product> patchProduct(@RequestBody Product productDto,@PathVariable("id") Long id) {
       Product product= productService.patchProduct(productDto,id);
       return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")      //DONE
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.deleteProduct(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<List<Product>> addAllProducts(@RequestBody List<Product> products){
        List<Product> products1 = productService.addAllProducts(products);
        return new ResponseEntity<>(products1, HttpStatus.CREATED);
    }
}
