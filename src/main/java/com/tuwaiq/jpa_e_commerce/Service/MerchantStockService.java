package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.Merchant;
import com.tuwaiq.jpa_e_commerce.Model.MerchantStock;
import com.tuwaiq.jpa_e_commerce.Model.Product;
import com.tuwaiq.jpa_e_commerce.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService;

    public String addMerchantStock(MerchantStock merchantStock) {
        String value = merchantProductValidation(merchantStock.getMerchantId(), merchantStock.getProductId());
        if (value.equalsIgnoreCase("ok")) {
            merchantStocks.add(merchantStock);
        }
        return value;
    }

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public Boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteMerchantStock(String id) {
        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getId().equalsIgnoreCase(id)) {
                merchantStocks.remove(merchantStock);
                return true;
            }
        }
        return false;
    }

    public String merchantProductValidation(String merchantId, String productId) {
        String value = "General error";
        for (Merchant merchant : merchantService.merchants) {
            if (merchant.getId().equalsIgnoreCase(merchantId)) {
                for (Product product : productService.products) {
                    if (product.getId().equalsIgnoreCase(productId)) {
                        return "ok";
                    } else {
                        value = "product id error";
                    }
                }
            } else {
                value = "merchant id error";
            }
        }
        return value;
    }

    public String increaseProductStock(String merchantId, String productId, int newStock) {
        Boolean productFound = false;
        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getMerchantId().equalsIgnoreCase(merchantId)) {
                if (merchantStock.getProductId().equalsIgnoreCase(productId)) {
                    merchantStock.setStock(merchantStock.getStock() + newStock);
                    return "ok";
                } else {
                    productFound=true;
                }
            }
        }
        if (productFound){
            return "product id error";
        }else {
            return "merchant id error";
        }
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
        for (User user : userService.users) {
            if (user.getId().equalsIgnoreCase(userId)) {
                for (MerchantStock merchantStock : merchantStocks) {
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
                    if (product.getCategoryId().equalsIgnoreCase("02001")){
                        return product.getPrice()*count-(product.getPrice()*0.1);
                    } else if (product.getCategoryId().equalsIgnoreCase("02002")) {
                        return product.getPrice()*count-(product.getPrice()*0.5);
                    }
                }
                return product.getPrice();
            }
        }
        return 0;
    }

    public ArrayList<String> getProductStockFromAllMerchants(String productId){
        ArrayList<String> productStockFromAllMerchants = new ArrayList<>();
        int stockSum=0;
        for (MerchantStock merchantStock: merchantStocks){
            if (merchantStock.getProductId().equalsIgnoreCase(productId)){
                productStockFromAllMerchants.add("The product "+productId+" have "+merchantStock.getStock()+" stocks from "+merchantStock.getMerchantId());
                stockSum+= merchantStock.getStock();
            }
        }
        if (stockSum==0){
            return productStockFromAllMerchants;
        }
        productStockFromAllMerchants.add(0,"The product "+productId+" have "+stockSum+" stocks in total");
        return productStockFromAllMerchants;
    }

    public ArrayList<Product> getMoreProductFromMerchant(String merchantId){
        ArrayList<Product> moreProduct = new ArrayList<>();
        ArrayList<String> merchantProductIds = new ArrayList<>();
        for (MerchantStock merchantStock:merchantStocks){
            if (merchantStock.getMerchantId().equalsIgnoreCase(merchantId)){
                merchantProductIds.add(merchantStock.getProductId());
            }
        }
        for (Product product:productService.products){
            for (String productId:merchantProductIds){
                if (product.getId().equalsIgnoreCase(productId)){
                    moreProduct.add(product);
                }
            }
        }
        return moreProduct;
    }
}
