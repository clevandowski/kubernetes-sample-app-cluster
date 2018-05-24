package com.zenika.microservices.resanet.domain;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PAYS")
@Cacheable
public class Pays extends AbstractBusinessObject {

    private String nom;

    // Pour ville -> pays
    //@Transient
    // Pour pays -> ville
    //@OneToMany
    //@JoinColumn(name = "PAYS_ID")
    // Pour pays <-> ville
    //@OneToMany
    // Pour bidirectionnalité
    @OneToMany(mappedBy = "pays")
    private Set<Ville> villes = new HashSet<Ville>();

    public Pays() {
    }

    public Pays(String unNom) {
        this.nom = unNom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Ville> getVilles() {
        return villes;
    }

    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
    }

    public void ajouterVille(Ville ville) {
        Pays pays = ville.getPays();
        if (pays != null && !pays.equals(this)) {
            pays.retirerVille(ville);
        }
        ville.setPays(this);
        villes.add(ville);
    }

    public void retirerVille(Ville ville) {
        if (this.equals(ville.getPays())) {
            ville.setPays(null);
            villes.remove(ville);
        }
    }

    @Override
    public String toString() {
        return "Pays{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
