package com.zenika.microservices.resanet.domain;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "VILLE")
@Cacheable
public class Ville extends AbstractBusinessObject {

    private String nom;

    // Pour pays -> ville
    //@Transient
    // Pour ville -> pays
    @ManyToOne
    @JoinColumn(name = "PAYS_ID")
    @JsonIgnore
    private Pays pays;

    public Ville() {
    }

    public Ville(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
