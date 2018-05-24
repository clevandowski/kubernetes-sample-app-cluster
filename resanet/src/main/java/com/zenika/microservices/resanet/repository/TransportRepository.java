package com.zenika.microservices.resanet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zenika.microservices.resanet.domain.Transport;

public interface TransportRepository extends PagingAndSortingRepository<Transport, Long>, JpaRepository<Transport, Long>{

}
