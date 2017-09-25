package com.bluebik.app.backend.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bluebik.app.backend.entity.User;
import com.bluebik.app.backend.request.LoginRequest;
import com.bluebik.app.backend.response.UserCreateResponse;
import com.bluebik.app.backend.response.UserLoginResponse;
import com.bluebik.app.backend.service.LoginLogicService;

@RestController
public class UserController {
	
	@Resource
	private LoginLogicService loginLogicService;

	@PostMapping(value="/user", produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public UserCreateResponse singup(@RequestBody LoginRequest loginRequest) {
		
		UserCreateResponse response = new UserCreateResponse();
		response.setCode("999");
		response.setMessage("create failure");
		User user = loginLogicService.createByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
		if(user != null){
			response.setCode("000");
			response.setMessage("create suceess");
			response.setUsername(loginRequest.getUsername());
		}
	
		return response;
	}
	
	@PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public UserLoginResponse login(@RequestBody LoginRequest loginRequest) {
		UserLoginResponse response = new UserLoginResponse();
		response.setCode("999");
		response.setMessage("login failure");
		User user = loginLogicService.loginByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
		if(user != null) {
			response.setSecretKey(loginLogicService.generateSecretKeyByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword()));
			response.setCode("000");
			response.setMessage("login success");
		}			
		return response;
	}

}
