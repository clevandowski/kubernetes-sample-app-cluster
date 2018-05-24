package com.zenika.microservices.resanet.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
// Pour héritage "une classe par table concrète"
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// Pour héritage "une classe pour toute la hiérarchie"
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "DTYPE",
        discriminatorType = DiscriminatorType.STRING
)
// Pour héritage "une classe par table avec jointure"
//@Inheritance(strategy = InheritanceType.JOINED)
// Pour l'externalisation des requêtes
@NamedQueries({
@NamedQuery(
    name = "transportVilleAVilleB",
    query = "select t " +
            "from Transport t " +
            "join t.villeDepart vd " +
            "join t.villeArrivee va " +
            "where vd.nom = :villeDepart " +
            "and va.nom = :villeArrivee")
})
public abstract class Transport extends AbstractBusinessObject {

    @Temporal(TemporalType.DATE)
	private Date dateDepart;

	private String heureDepart;
	private String heureArrivee;
	private double prix;
	private int nbSiegesTotal;
	private int nbSiegesDispo;

    // Pour reservation -> transport
    //@Transient
    // Pour transport -> reservation et pour transport <-> réservation
    //@ManyToMany
    //@JoinTable(
    //        name = "RESERVATION_TRANSPORT",
    //        joinColumns = @JoinColumn(name = "RESERVATIONS_ID"),
    //        inverseJoinColumns =  @JoinColumn(name="TRANSPORTS_ID")
    //)
    // Pour bidirectionnalité
    @ManyToMany(mappedBy = "transports") @JsonIgnore
	private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "VILLE_A")
    private Ville villeArrivee;

    @ManyToOne
    @JoinColumn(name = "VILLE_D")
    private Ville villeDepart;

	public Transport() {

	}

	public Transport(Ville villeDepart, Ville villeArrivee, Date dateDepart) {
		this.villeDepart = villeDepart;
		this.villeArrivee = villeArrivee;
		this.dateDepart = dateDepart;
	}

	public Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public int getNbSiegesDispo() {
		return nbSiegesDispo;
	}

	public void setNbSiegesDispo(int nbSiegesDispo) {
		this.nbSiegesDispo = nbSiegesDispo;
	}

	public int getNbSiegesTotal() {
		return nbSiegesTotal;
	}

	public void setNbSiegesTotal(int nbSiegesTotal) {
		this.nbSiegesTotal = nbSiegesTotal;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Ville getVilleArrivee() {
		return villeArrivee;
	}

	public void setVilleArrivee(Ville villeArrivee) {
		this.villeArrivee = villeArrivee;
	}

	public Ville getVilleDepart() {
		return villeDepart;
	}

	public void setVilleDepart(Ville villeDepart) {
		this.villeDepart = villeDepart;
	}

	public String getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(String heureDepart) {
		this.heureDepart = heureDepart;
	}

	public String getHeureArrivee() {
		return heureArrivee;
	}

	public void setHeureArrivee(String heureArrivee) {
		this.heureArrivee = heureArrivee;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

    @Override
    public String toString() {
        return "Transport{" +
                "villeDepart=" + villeDepart +
                ", villeArrivee=" + villeArrivee +
                ", dateDepart=" + dateDepart +
                ", heureDepart='" + heureDepart + '\'' +
                ", prix=" + prix +
                '}';
    }
}
