package com.bluebik.app.backend.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bluebik.app.backend.entity.UriResource;
import com.bluebik.app.backend.service.UriResourceService;

@RestController("/uri")
public class URIController {
	
	@Resource
	private UriResourceService uriResourceService;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<UriResource> list(
			  @RequestParam(required=false, defaultValue="1") int page
			, @RequestParam(required=false, defaultValue="10") int size) {
		Pageable pageable = new PageRequest(page, size);
		Page<UriResource> uris = uriResourceService.list(pageable);
		return uris;
	}
	
	@PostMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public UriResource create(@RequestParam String uri) {
		UriResource uriResource = uriResourceService.createUri(uri);
		return uriResource;
	}

}
