package com.dotmonsservice.customer.repository;

import com.dotmonsservice.customer.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    UserLogin findByUsernameAndPassword(String username, String password);

    @Query(value = "select userrole from user_details_login where username = :username ", nativeQuery = true)
    String findRoleByUsername(@Param("username") String username);
}
