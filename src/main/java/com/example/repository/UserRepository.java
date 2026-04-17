package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	
	Optional<User> findByEmail(String email);

	    Optional<User> findByPhone(String phone);
	

	Optional<User> findByEmailOrPhoneOrUsername(
	        String email,
	        String phone,
	        String username
	);


    boolean existsByEmail(String email);
}
