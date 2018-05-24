package com.zenika.microservices.resanet.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FACTURE")
public class Facture extends AbstractBusinessObject {

    private double total;
    private boolean reglee;

    @Temporal(TemporalType.DATE)
    private Date dateEmission;

    // Pour reservation -> facture
    //@Transient
    // Pour facture -> reservation
    @OneToOne(mappedBy = "facture")
    private Reservation reservation;

	public Date getDateEmission() {
		return dateEmission;
	}

	public void setDateEmission(Date dateEmission) {
		this.dateEmission = dateEmission;
	}

	public boolean isReglee() {
		return reglee;
	}

	public void setReglee(boolean reglee) {
		this.reglee = reglee;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
