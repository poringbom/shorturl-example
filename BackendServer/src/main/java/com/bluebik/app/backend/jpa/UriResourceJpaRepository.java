package com.bluebik.app.backend.jpa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bluebik.app.backend.entity.UriResource;

public interface UriResourceJpaRepository extends PagingAndSortingRepository<UriResource, Long> {
	
	List<UriResource> findByShortUri(String shortUri);
	
	List<UriResource> findByUri(String uri);

}
