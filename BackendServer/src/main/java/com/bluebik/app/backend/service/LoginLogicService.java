package com.bluebik.app.backend.service;

import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bluebik.app.backend.entity.User;
import com.bluebik.app.backend.jpa.UserJpaRepository;

@Component
public class LoginLogicService {
	
	@Resource
	private UserJpaRepository userJpaRepository;
	
	public User createByUsernameAndPassword(String username, String password) {
		User user = new User();
		try {
			user.setUsername(username);
			user.setPassword(password);
			userJpaRepository.save(user);
		} catch (Exception e) {
			user = null;
			e.printStackTrace();
		}
		
		return user;
	}
	
	public User loginByUsernameAndPassword(String username, String password) {
		User user = null;
		try {
			List<User> users = userJpaRepository.findByUsernameAndPassword(username, password);
			if(users != null && !users.isEmpty()){
				user = users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public String generateSecretKeyByUsernameAndPassword(String username, String password) {
		String result = "";
		try {
			result = Base64.getEncoder().encodeToString((username+password).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean checkAccessByUsernameAndSecreKey(String username, String secretKey) {
		boolean result = false;
		try {
			User user = userJpaRepository.findOne(username);
			if(user != null) {
				result = generateSecretKeyByUsernameAndPassword(user.getUsername(), user.getPassword()).equals(secretKey);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
