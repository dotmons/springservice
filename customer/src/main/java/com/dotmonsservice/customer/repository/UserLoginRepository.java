package com.dotmonsservice.customer.repository;

import com.dotmonsservice.customer.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    UserLogin findByUsernameAndPassword(String username, String password);

}
