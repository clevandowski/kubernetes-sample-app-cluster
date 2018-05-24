package com.zenika.microservices.resanet.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OPERATEUR")
public class Operateur extends AbstractBusinessObject {

    @Basic
    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @Basic
    @Column(name = "PASSWORD")
    private String password;

    @Basic
    @Column(name = "COURRIEL", length = 100)
    private String courriel;

    @Basic
    @Column(name = "ROLE")
    private String role;

    @Basic
    @Column(name = "TELEPHONE")
    private String telephone;

    public Operateur() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
