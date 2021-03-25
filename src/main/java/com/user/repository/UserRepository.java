package com.user.repository;

import com.user.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long > {

    @Modifying
    @Query("UPDATE User u set u.email = :email,u.role = :role,u.firstName = :firstName,u.lastName = :lastName, u.password = :password where u.id = :idUser")
    public void updateUser(@Param("idUser") long idUser,@Param("email") String email,
       @Param("password") String password,@Param("firstName") String firstName,@Param("lastName") String lastName,
            @Param("role") String role);

    public User getUserById(@Param("id") long idUser);

    @Query("select u from User u where LOWER(u.firstName) LIKE LOWER(CONCAT('%',:firstName, '%'))")
    public Page<User> getUsersByFirstName(@Param("firstName") String firstName, Pageable pageable);

    public User getUsersByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
