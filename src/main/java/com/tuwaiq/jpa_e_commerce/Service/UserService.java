package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.MerchantStock;
import com.tuwaiq.jpa_e_commerce.Model.Product;
import com.tuwaiq.jpa_e_commerce.Model.User;
import com.tuwaiq.jpa_e_commerce.Repository.MerchantStockRepository;
import com.tuwaiq.jpa_e_commerce.Repository.ProductRepository;
import com.tuwaiq.jpa_e_commerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final MerchantStockRepository merchantStocksRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Boolean updateUser(Integer id, User user) {
        User oldUser =userRepository.getById(id);
        if (oldUser ==null){
            return false;
        }else {
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            oldUser.setRole(user.getRole());
            oldUser.setBalance(user.getBalance());
            userRepository.save(oldUser);
            return true;
        }
    }

    public Boolean deleteUser(Integer id) {
        User User =userRepository.getById(id);
        if (User ==null){
            return false;
        }else {
            userRepository.delete(User);
            return true;
        }
    }

    public boolean addBalanceFunds(Integer id,double addedBalance){
        for (User user:this.getUsers()){
            if (user.getId().equals(id)){
                user.setBalance(user.getBalance()+addedBalance);
                return true;
            }
        }
        return false;
    }

    public String buyProduct(Integer userId, Integer merchantId, Integer productId) {
        double productPrice = pricingProduct(productId,1);
        if (productPrice == 0) {
            return "product id error";
        }
        return buying(userId, merchantId, productId, productPrice,1);
    }

    public String bulkBuyProducts(Integer userId, Integer merchantId, Integer productId, int count){
        double productPrice = pricingProduct(productId, count);
        if (productPrice == 0) {
            return "product id error";
        }
        return buying(userId, merchantId, productId, productPrice,count);
    }

    private String buying(Integer userId, Integer merchantId, Integer productId, double productPrice, int count) {
        boolean productFound = false;
        boolean merchantFound = false;
        boolean userFound = false;
        for (User user : this.getUsers()) {
            if (user.getId().equals(userId)) {
                for (MerchantStock merchantStock : merchantStocksRepository.findAll()) {
                    if (merchantStock.getMerchantId().equals(merchantId)) {
                        if (merchantStock.getProductId().equals(productId)) {
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

    private double pricingProduct(Integer productId,int count) {
        for (Product product : productRepository.findAll()) {
            if (product.getId().equals(productId)) {
                if (count>1){
                    if (product.getCategoryId().equals(1)){                 /* check if category is electronics */
                        return product.getPrice()*count-(product.getPrice()*0.1);
                    } else if (product.getCategoryId().equals(2)) {         /* check if category is books */
                        return product.getPrice()*count-(product.getPrice()*0.5);
                    }
                }
                return product.getPrice();
            }
        }
        return 0;
    }

}
