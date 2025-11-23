package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.Category;
import com.tuwaiq.jpa_e_commerce.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryService;

    public boolean addProduct(Product product){
        for (Category category:categoryService.categories){
            if (category.getId().equalsIgnoreCase(product.getCategoryId())){
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public Boolean updateProduct(String id,Product product){
        for (int i = 0; i< products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteProduct(String id){
        for (Product product: products){
            if (product.getId().equalsIgnoreCase(id)){
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getProductsByCategorySortedByPrice(String categoryId){
        ArrayList<Product> sortedProductByPrice=new ArrayList<>();
        for (Product product:products){
            if (product.getCategoryId().equalsIgnoreCase(categoryId)){
                sortedProductByPrice.add(product);
            }
        }
        sortedProductByPrice.sort((product1, product2) -> Double.compare(product1.getPrice(), product2.getPrice()));
        return sortedProductByPrice;
    }

    public int countProductInCategoryName(String categoryName){                 /* extra */
        String categoryId="";
        ArrayList<Product> products1 =new ArrayList<>();
        for (Category category : categoryService.categories){
            if (category.getName().equalsIgnoreCase(categoryName)){
                categoryId= category.getId();
            }
        }
        for (Product product:products){
            if (product.getCategoryId().equalsIgnoreCase(categoryId)){
                products1.add(product);
            }
        }
        if (products.isEmpty()){
            return 0;
        }
        return products1.size();
    }
}
