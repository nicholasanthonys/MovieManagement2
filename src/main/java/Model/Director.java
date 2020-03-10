/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */
@Entity
@Table(name = "director")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Director.findAll", query = "SELECT d FROM Director d")
    , @NamedQuery(name = "Director.findById", query = "SELECT d FROM Director d WHERE d.id = :id")
    , @NamedQuery(name = "Director.findByName", query = "SELECT d FROM Director d WHERE d.name = :name")
    , @NamedQuery(name = "Director.findByShortbio", query = "SELECT d FROM Director d WHERE d.shortbio = :shortbio")
    , @NamedQuery(name = "Director.findByBirthdate", query = "SELECT d FROM Director d WHERE d.birthdate = :birthdate")
    , @NamedQuery(name = "Director.findByOccupation", query = "SELECT d FROM Director d WHERE d.occupation = :occupation")})
public class Director implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "picture")
    private byte[] picture;
    @Column(name = "shortbio")
    private String shortbio;
    @Column(name = "birthdate")
    private String birthdate;
    @Column(name = "occupation")
    private String occupation;
    @JoinTable(name = "daftarmoviedirected", joinColumns = {
        @JoinColumn(name = "iddirector", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "idmovie", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Movie> movieList;

    public Director() {
    }

    public Director(Integer id) {
        this.id = id;
    }

    public Director(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getShortbio() {
        return shortbio;
    }

    public void setShortbio(String shortbio) {
        this.shortbio = shortbio;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @XmlTransient
    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Director)) {
            return false;
        }
        Director other = (Director) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Director[ id=" + id + " ]";
    }
    
}
