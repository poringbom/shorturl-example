package com.bluebik.app.backend.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.bluebik.app.backend.bean.URLShortener;
import com.bluebik.app.backend.entity.UriResource;
import com.bluebik.app.backend.jpa.UriResourceJpaRepository;

@Component
public class UriResourceService {
	
	@Resource
	private UriResourceJpaRepository uriResourceJpaRepository;
	
	private static URLShortener uRLShortener = new URLShortener();
	
	public Page<UriResource> list(Pageable page) {
		try {
			return uriResourceJpaRepository.findAll(page);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUriByShortUriAndcounting(String shortUri) {
		String result = null;
		try {
			UriResource uri = this.getUriResourceByShortUri(shortUri);
			if(uri != null) {
				uri.setCount(uri.getCount()+1);
				uriResourceJpaRepository.save(uri);
				result = uri.getUri();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public UriResource createUri(String uri) {
		UriResource uriResource = null;
		try {
			List<UriResource> uris = uriResourceJpaRepository.findByUri(uri);
			if(uris != null && !uris.isEmpty()) {
				uriResource = uris.get(0);
			}else {
				uriResource = new UriResource();
				uriResource.setCount(0L);
				uriResource.setUri(uri);
				uriResource.setShortUri(this.generateShortUriByUri(uri));
				uriResourceJpaRepository.save(uriResource);
			}
		} catch (Exception e) {
			uriResource = null;
			e.printStackTrace();
		}
		return uriResource;
	}
	
	private UriResource getUriResourceByShortUri(String shortUri) {
		UriResource uriResource = null;
		try {
			List<UriResource> uris = uriResourceJpaRepository.findByShortUri(shortUri);
			if(uris != null && !uris.isEmpty() && uris.get(0) != null) {
				uriResource = uris.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uriResource;
	}

	
	private String generateShortUriByUri(String uri) {
		String result = "";
		try {
			result = uRLShortener.shortenURL(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
