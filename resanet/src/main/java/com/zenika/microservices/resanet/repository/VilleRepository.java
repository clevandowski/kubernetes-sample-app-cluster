package com.zenika.microservices.resanet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zenika.microservices.resanet.domain.Ville;

public interface VilleRepository extends PagingAndSortingRepository<Ville, Long>, JpaRepository<Ville, Long> {

}
