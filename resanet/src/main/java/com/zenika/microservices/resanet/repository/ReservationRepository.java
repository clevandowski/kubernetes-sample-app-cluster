package com.zenika.microservices.resanet.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zenika.microservices.resanet.domain.Reservation;

public interface ReservationRepository extends PagingAndSortingRepository<Reservation, Long> {

}
