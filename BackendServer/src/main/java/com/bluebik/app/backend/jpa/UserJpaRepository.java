package com.bluebik.app.backend.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bluebik.app.backend.entity.User;


public interface UserJpaRepository extends CrudRepository<User, String> {
	
	List<User> findByUsernameAndPassword(String username, String password);
	
}
