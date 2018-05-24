package com.zenika.microservices.resanet.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zenika.microservices.resanet.domain.Train;

public interface TrainRepository extends PagingAndSortingRepository<Train, Long> {

}
