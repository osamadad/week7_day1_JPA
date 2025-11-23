package com.tuwaiq.jpa_e_commerce.Repository;

import com.tuwaiq.jpa_e_commerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
