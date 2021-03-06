package com.zenika.microservices.resanet.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse {

	private String rue;
	private String codePostal;
	private String ville;

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
}
