package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.Merchant;
import com.tuwaiq.jpa_e_commerce.Model.MerchantStock;
import com.tuwaiq.jpa_e_commerce.Model.Product;
import com.tuwaiq.jpa_e_commerce.Repository.MerchantRepository;
import com.tuwaiq.jpa_e_commerce.Repository.MerchantStockRepository;
import com.tuwaiq.jpa_e_commerce.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantStockRepository merchantStockRepository;
    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;


    public String addMerchantStock(MerchantStock merchantStock) {
        String value = merchantProductValidation(merchantStock.getMerchantId(), merchantStock.getProductId());
        if (value.equalsIgnoreCase("ok")) {
            merchantStockRepository.save(merchantStock);
        }
        return value;
    }

    public List<MerchantStock> getMerchantStocks() {
        return merchantStockRepository.findAll();
    }

    public Boolean updateMerchantStock(Integer id, MerchantStock merchantStock) {
        MerchantStock oldMerchantStocks = merchantStockRepository.getById(id);
        if (oldMerchantStocks == null) {
            return false;
        } else {
            oldMerchantStocks.setMerchantId(merchantStock.getMerchantId());
            oldMerchantStocks.setProductId(merchantStock.getProductId());
            oldMerchantStocks.setStock(merchantStock.getStock());
            merchantStockRepository.save(oldMerchantStocks);
            return true;
        }

    }

    public Boolean deleteMerchantStock(Integer id) {
        MerchantStock merchantStock = merchantStockRepository.getById(id);
        if (merchantStock == null) {
            return false;
        } else {
            merchantStockRepository.delete(merchantStock);
            return true;
        }
    }

    public String merchantProductValidation(Integer merchantId, Integer productId) {
        String value = "General error";
        for (Merchant merchant : merchantRepository.findAll()) {
            if (merchant.getId().equals(merchantId)) {
                for (Product product : productRepository.findAll()) {
                    if (product.getId().equals(productId)) {
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

    public String increaseProductStock(Integer merchantId, Integer productId, Integer newStock) {
        Boolean productFound = false;
        for (MerchantStock merchantStock : this.getMerchantStocks()) {
            if (merchantStock.getMerchantId().equals(merchantId)) {
                if (merchantStock.getProductId().equals(productId)) {
                    merchantStock.setStock(merchantStock.getStock() + newStock);
                    return "ok";
                } else {
                    productFound = true;
                }
            }
        }
        if (productFound) {
            return "product id error";
        } else {
            return "merchant id error";
        }
    }


    public List<String> getProductStockFromAllMerchants(Integer productId) {
        List<String> productStockFromAllMerchants = new ArrayList<>();
        int stockSum = 0;
        for (MerchantStock merchantStock : this.getMerchantStocks()) {
            if (merchantStock.getProductId().equals(productId)) {
                productStockFromAllMerchants.add("The product " + productId + " have " + merchantStock.getStock() + " stocks from " + merchantStock.getMerchantId());
                stockSum += merchantStock.getStock();
            }
        }
        if (stockSum == 0) {
            return productStockFromAllMerchants;
        }
        productStockFromAllMerchants.add(0, "The product " + productId + " have " + stockSum + " stocks in total");
        return productStockFromAllMerchants;
    }

    public List<Product> getMoreProductFromMerchant(Integer merchantId) {
        List<Product> moreProduct = new ArrayList<>();
        List<Integer> merchantProductIds = new ArrayList<>();
        for (MerchantStock merchantStock : this.getMerchantStocks()) {
            if (merchantStock.getMerchantId().equals(merchantId)) {
                merchantProductIds.add(merchantStock.getProductId());
            }
        }
        for (Product product : productRepository.findAll()) {
            for (Integer productId : merchantProductIds) {
                if (product.getId().equals(productId)) {
                    moreProduct.add(product);
                }
            }
        }
        return moreProduct;
    }
}
