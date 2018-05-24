package com.zenika.microservices.resanet.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractBusinessObject {

    // Identifiant technique des objets métiers
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    // Pour les TP sur l'héritage
    @Id
//    @TableGenerator(name = "myGenerator")
//    @GeneratedValue(generator = "myGenerator")
    @GeneratedValue
    protected Long id;

    // Numéro de version pour le verrouillage optimiste
    @Version
    protected int version;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    private void setVersion(int version) {
        this.version = version;
    }
}
