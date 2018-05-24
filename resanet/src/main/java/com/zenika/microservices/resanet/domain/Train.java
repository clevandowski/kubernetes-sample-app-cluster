package com.zenika.microservices.resanet.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;

@Entity
//@Table(name = "TRAIN")
// Pour héritage "une classe pour toute la hiérarchie"
@DiscriminatorValue("Train")
// Pour héritage "une classe par table avec jointure"
//@PrimaryKeyJoinColumn(name="TRANSPORT_ID")
public class Train extends Transport {

    private int nbWagons;

    private boolean voitureBar;

    public Train() {
    	super();
    }
    
    public Train(Ville villeDepart, Ville villeArrivee, Date dateDepart) {
    	super(villeDepart, villeArrivee, dateDepart);
    }
    
	public int getNbWagons() {
		return nbWagons;
	}

	public void setNbWagons(int nbWagons) {
		this.nbWagons = nbWagons;
	}

	public boolean isVoitureBar() {
		return voitureBar;
	}

	public void setVoitureBar(boolean voitureBar) {
		this.voitureBar = voitureBar;
	}
 }
