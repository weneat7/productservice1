package com.example.productservice1.services;

import com.example.productservice1.dtos.FakeStoreProductDto;
import com.example.productservice1.exceptions.ProductNotExistException;
import com.example.productservice1.models.Category;
import com.example.productservice1.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    private RestTemplate restTemplate;

    @Autowired
    FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product convertProductDtoIntoProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProductDto.getCategory());
        product.setImageUrl(fakeStoreProductDto.getImage());
        return product;
    }  //DONE
    public FakeStoreProductDto convertProductIntoFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setImage(product.getImageUrl());
        return fakeStoreProductDto;
    }   //DONE

    @Override
    public Product getSingleProduct(Long id) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);
        if(fakeStoreProductDto == null) {
            System.out.println("Present");
            throw new ProductNotExistException("Product with id: " + id + ", does not exist");
        }

        return convertProductDtoIntoProduct(fakeStoreProductDto);
    }   //DONE


    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products",FakeStoreProductDto[].class);
        if(fakeStoreProductDtos == null)
            throw new ProductNotExistException("Unable to access the products at the moment");

       List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto productDto : fakeStoreProductDtos){
            products.add(convertProductDtoIntoProduct(productDto));
        }
        return products;
    }       //DONE



    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = convertProductIntoFakeStoreProductDto(product);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto productDto = restTemplate.execute("https://fakestoreapi.com/products", HttpMethod.POST, requestCallback, responseExtractor);
        if(productDto == null)
            throw new ProductNotExistException("Unable to create the product at the moment");
        return convertProductDtoIntoProduct(productDto);
    }       //DONE



    @Override               //DONE
    public Product replaceProduct(Product product, Long id) {
        FakeStoreProductDto fakeStoreProductDto = convertProductIntoFakeStoreProductDto(product);
       // restTemplate.put("https://fakestoreapi.com/products/"+product.getId(),fakeStoreProductDto,FakeStoreProductDto.class);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto fakeStoreProductDto1= restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PUT, requestCallback, responseExtractor);
        assert fakeStoreProductDto1 != null;
        return convertProductDtoIntoProduct(fakeStoreProductDto1);
    }   //PUT



    @Override
    public Product patchProduct(Product product, Long id) {
       FakeStoreProductDto fakeStoreProductDto = convertProductIntoFakeStoreProductDto(product);
       fakeStoreProductDto = restTemplate.patchForObject("https://fakestoreapi.com/products/"+id,fakeStoreProductDto,FakeStoreProductDto.class);
       return convertProductDtoIntoProduct(fakeStoreProductDto);
    }       //TODO: patch method



    @Override
    public Product deleteProduct(Long id) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        fakeStoreProductDto= restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.DELETE, requestCallback, responseExtractor);
        return convertProductDtoIntoProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> addAllProducts(List<Product> products) {
        return null;
    }
}
