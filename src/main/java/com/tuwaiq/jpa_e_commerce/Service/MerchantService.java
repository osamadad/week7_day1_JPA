package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants= new ArrayList<>();

    public void addMerchant(Merchant merchant){
        merchants.add(merchant);
    }

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean updateMerchant(String id, Merchant merchant){
        for (int i=0;i<merchants.size();i++){
            if (merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id){
        for (Merchant merchant:merchants){
            if (merchant.getId().equalsIgnoreCase(id)){
                merchants.remove(merchant);
                return true;
            }
        }
        return false;
    }


}

