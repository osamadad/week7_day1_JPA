package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    ArrayList<User> users = new ArrayList<>();

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


}
