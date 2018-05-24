package com.zenika.microservices.resanet.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zenika.microservices.resanet.domain.Avion;

public interface AvionRepository extends PagingAndSortingRepository<Avion, Long> {

}
