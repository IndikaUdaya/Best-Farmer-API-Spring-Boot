package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findFirstByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email=:email")
    User getUserByEmail(String email);

    @Query("SELECT COUNT(u.userType) from User u where u.userType=:userType")
    Integer findAllByUserType(String userType);

}
