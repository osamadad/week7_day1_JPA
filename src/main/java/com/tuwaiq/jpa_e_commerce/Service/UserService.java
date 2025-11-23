package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.MerchantStock;
import com.tuwaiq.jpa_e_commerce.Model.Product;
import com.tuwaiq.jpa_e_commerce.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;
    private final MerchantStockService merchantStocksService;

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public Boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equalsIgnoreCase(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteUser(String id) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(id)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    public boolean addBalanceFunds(String id,double addedBalance){
        for (User user:users){
            if (user.getId().equalsIgnoreCase(id)){
                user.setBalance(user.getBalance()+addedBalance);
                return true;
            }
        }
        return false;
    }

    public String buyProduct(String userId, String merchantId, String productId) {
        double productPrice = pricingProduct(productId,1);
        if (productPrice == 0) {
            return "product id error";
        }
        return buying(userId, merchantId, productId, productPrice,1);
    }

    public String bulkBuyProducts(String userId, String merchantId, String productId, int count){
        double productPrice = pricingProduct(productId, count);
        if (productPrice == 0) {
            return "product id error";
        }
        return buying(userId, merchantId, productId, productPrice,count);
    }

    private String buying(String userId, String merchantId, String productId, double productPrice, int count) {
        boolean productFound = false;
        boolean merchantFound = false;
        boolean userFound = false;
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(userId)) {
                for (MerchantStock merchantStock : merchantStocksService.merchantStocks) {
                    if (merchantStock.getMerchantId().equalsIgnoreCase(merchantId)) {
                        if (merchantStock.getProductId().equalsIgnoreCase(productId)) {
                            if (merchantStock.getStock() > 0) {
                                if (user.getBalance() >= productPrice) {
                                    merchantStock.setStock(merchantStock.getStock() - count);
                                    user.setBalance(user.getBalance() - productPrice);
                                    return "ok";
                                } else {
                                    return "balance error";
                                }
                            } else {
                                return "stock error";
                            }
                        } else {
                            productFound = true;
                        }
                    } else {
                        merchantFound = true;
                    }
                }
            } else {
                userFound = true;
            }
        }
        if (!userFound) {
            return "user id error";
        }else if (!merchantFound){
            return "merchant id error";
        }else if ((!productFound)){
            return "product id error";
        }else {
            return "general error";
        }
    }

    private double pricingProduct(String productId,int count) {
        for (Product product : productService.getProducts()) {
            if (product.getId().equalsIgnoreCase(productId)) {
                if (count>1){
                    if (product.getCategoryId().equalsIgnoreCase("02001")){                 /* check if category is electronics */
                        return product.getPrice()*count-(product.getPrice()*0.1);
                    } else if (product.getCategoryId().equalsIgnoreCase("02002")) {         /* check if category is books */
                        return product.getPrice()*count-(product.getPrice()*0.5);
                    }
                }
                return product.getPrice();
            }
        }
        return 0;
    }

}
