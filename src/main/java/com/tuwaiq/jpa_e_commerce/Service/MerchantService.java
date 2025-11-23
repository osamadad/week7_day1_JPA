package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.Merchant;
import com.tuwaiq.jpa_e_commerce.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public void addMerchant(Merchant merchant){
        merchantRepository.save(merchant);
    }

    public List<Merchant> getMerchants(){
        return merchantRepository.findAll();
    }

    public boolean updateMerchant(Integer id, Merchant merchant){
        Merchant oldMerchant=merchantRepository.getById(id);
        if (oldMerchant==null){
            return false;
        }else {
            oldMerchant.setName(merchant.getName());
            return true;
        }
    }

    public boolean deleteMerchant(Integer id){
        Merchant merchant=merchantRepository.getById(id);
        if (merchant==null){
            return false;
        }
        else {
            merchantRepository.delete(merchant);
            return true;
        }
    }


}

