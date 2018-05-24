package com.zenika.microservices.resanet.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zenika.microservices.resanet.domain.Pays;

public interface PaysRepository extends PagingAndSortingRepository<Pays, Long> {

}
