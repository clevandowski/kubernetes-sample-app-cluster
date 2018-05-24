package com.zenika.microservices.resanet.domain;

public class ResaBean {

    private Long id;
    private String villeDepart;

    public ResaBean(Long id, String villeDepart) {
        this.id = id;
        this.villeDepart = villeDepart;
    }

    public Long getId() {
        return id;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    @Override
    public String toString() {
        return "ResaBean{" +
                "id=" + id +
                ", villeDepart='" + villeDepart + '\'' +
                '}';
    }
}
