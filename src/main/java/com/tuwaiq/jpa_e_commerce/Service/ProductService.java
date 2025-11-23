package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.Category;
import com.tuwaiq.jpa_e_commerce.Model.Product;
import com.tuwaiq.jpa_e_commerce.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public boolean addProduct(Product product){
        for (Category category:categoryService.getCategories()){
            if (category.getId().equals(product.getCategoryId())){
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Boolean updateProduct(Integer id,Product product){
        Product oldProduct =productRepository.getById(id);
        if (oldProduct ==null){
            return false;
        }else {
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setCategoryId(product.getCategoryId());
            productRepository.save(oldProduct);
            return true;
        }
    }

    public Boolean deleteProduct(Integer id){
        Product product =productRepository.getById(id);
        if (product ==null){
            return false;
        }else {
            productRepository.delete(product);
            return true;
        }
    }

    public List<Product> getProductsByCategorySortedByPrice(Integer categoryId){
        List<Product> sortedProductByPrice=new ArrayList<>();
        for (Product product:this.getProducts()){
            if (product.getCategoryId().equals(categoryId)){
                sortedProductByPrice.add(product);
            }
        }
        sortedProductByPrice.sort((product1, product2) -> Double.compare(product1.getPrice(), product2.getPrice()));
        return sortedProductByPrice;
    }

    public int countProductInCategoryName(Integer categoryId){                 /* extra */
        ArrayList<Product> products =new ArrayList<>();
        for (Product product:this.getProducts()){
            if (product.getCategoryId().equals(categoryId)){
                products.add(product);
            }
        }
        if (products.isEmpty()){
            return 0;
        }
        return products.size();
    }
}
