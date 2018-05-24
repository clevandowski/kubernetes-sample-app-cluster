package com.zenika.microservices.resanet.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zenika.microservices.resanet.domain.Ville;
import com.zenika.microservices.resanet.repository.VilleRepository;

@RestController
@RequestMapping("/villes")
public class VilleController {

	private final VilleRepository villeRepository;
	private static final int NB_PROCESSOR = Runtime.getRuntime().availableProcessors();
	private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();
	private static final long TOTAL_MEMORY = Runtime.getRuntime().totalMemory();
	    
	private static final Logger LOGGER = LoggerFactory.getLogger(VilleController.class);
	
	@Inject
	public VilleController(final VilleRepository villeRepository) {
		this.villeRepository = villeRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Ville> getList() {
		LOGGER.info("demande de la liste des villes");
		LOGGER.info("NB_PROCESSOR: " + NB_PROCESSOR + ", FREE_MEMORY:" + Runtime.getRuntime().freeMemory() + ", MAX_MEMORY: " + MAX_MEMORY + ", TOTAL_MEMORY:" + TOTAL_MEMORY);
		return villeRepository.findAll();
	}
	
	@RequestMapping(value = "/{villeId}", method = RequestMethod.GET)
	public Ville readVille(@PathVariable Long villeId) {
	  long start = 0, end = 0;
	  try {
  	  start = new Date().getTime();
  	  if (villeId == null || villeId == 0) {
  	    throw new RuntimeException("villeId " + villeId + " is not allowed");
  	  }

  		return villeRepository.findOne(villeId);
	  } finally {
      end = new Date().getTime();
      LOGGER.info("Demande de la ville: " + villeId + " traité en " + (end - start) + "ms");	    
	  }
	}
	
	
}
