package com.bluebik.app.backend.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${middleware.hostname}")
	private String hostName;
	
	public Page<UriResource> list(Pageable page) {
		try {
			return uriResourceJpaRepository.findAll(page);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUriByShortUri(String shortUri) {
		String result = null;
		try {
			UriResource uri = this.getUriResourceByShortUri(shortUri);
			if(uri != null) {
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
	
	public Long incrementCountingByShortUri(String shortUri) {
		Long count = null;
		try {
			UriResource uri = this.getUriResourceByShortUri(shortUri);
			if(uri != null) {
				count = uri.getCount();
				uri.setCount(count++);
				uriResourceJpaRepository.save(uri);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
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
		URLShortener u = new URLShortener(5, hostName);
		String result = "";
		try {
			result = u.shortenURL(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
