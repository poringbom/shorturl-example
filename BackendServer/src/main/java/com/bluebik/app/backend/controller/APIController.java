package com.bluebik.app.backend.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bluebik.app.backend.entity.UriResource;
import com.bluebik.app.backend.entity.User;
import com.bluebik.app.backend.request.CreateURIRequest;
import com.bluebik.app.backend.request.LoginRequest;
import com.bluebik.app.backend.response.CreateUriResponse;
import com.bluebik.app.backend.response.UserCreateResponse;
import com.bluebik.app.backend.response.UserLoginResponse;
import com.bluebik.app.backend.service.LoginLogicService;
import com.bluebik.app.backend.service.UriResourceService;

@Controller
@RequestMapping("/api/v1")
public class APIController {
	
	@Resource
	private UriResourceService uriResourceService;
	
	@Value("${app.server.hostname}")
	private String hostName;
	
	@GetMapping(path="uri" ,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Page<UriResource> list(
			  @RequestParam(required=false, defaultValue="1") int page
			, @RequestParam(required=false, defaultValue="10") int size) {
		Pageable pageable = new PageRequest(page, size);
		Page<UriResource> uris = uriResourceService.list(pageable);
		return uris;
	}
	
	@PostMapping(path="uri", produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code=HttpStatus.CREATED)
	public @ResponseBody CreateUriResponse create(@RequestBody CreateURIRequest createURIRequest) {
		UriResource uriResource = uriResourceService.createUri(createURIRequest.getUri());
		CreateUriResponse response = new CreateUriResponse();
		if(uriResource != null) {
			response.setCode("000");
			response.setMessage("create uri success");
			response.setShortUri(hostName+"/"+uriResource.getShortUri());
			response.setUri(uriResource.getUri());
		}else {
			response.setCode("999");
			response.setMessage("create uri failure");
		}
		return response;
	}
	
	@Resource
	private LoginLogicService loginLogicService;

	@PostMapping(path="user", produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code=HttpStatus.CREATED)
	public @ResponseBody UserCreateResponse singup(@RequestBody LoginRequest loginRequest) {
		
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
	
	@PostMapping(path="login", produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody UserLoginResponse login(@RequestBody LoginRequest loginRequest) {
		UserLoginResponse response = new UserLoginResponse();
		response.setCode("999");
		response.setMessage("login failure");
		User user = loginLogicService.loginByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
		if(user != null) {
			response.setSecretKey(loginLogicService.generateSecretKeyByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword()));
			response.setCode("000");
			response.setMessage("login success");
			response.setUsername(loginRequest.getUsername());
		}			
		return response;
	}

}
