package com.zenika.microservices.resanet.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
//@Table(name="AVION")
// Pour héritage "une classe pour toute la hiérarchie"
@DiscriminatorValue("Avion")
// Pour héritage "une classe par table avec jointure"
//@PrimaryKeyJoinColumn(name="TRANSPORT_ID")
public class Avion extends Transport {

	private String compagnie;
	private String typeAppareil;

	public Avion() {
	}

	public Avion(Ville villeDepart, Ville villeArrivee, Date dateDepart) {
		super(villeDepart, villeArrivee, dateDepart);
	}

	public String getCompagnie() {
		return compagnie;
	}

	public void setCompagnie(String compagnie) {
		this.compagnie = compagnie;
	}

	public String getTypeAppareil() {
		return typeAppareil;
	}

	public void setTypeAppareil(String typeAppareil) {
		this.typeAppareil = typeAppareil;
	}

}
