/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esc.domain;

import esc.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author minna
 */
@Entity
@Table(name = "contestant")
@NamedQueries({
    @NamedQuery(name = "Contestant.findAll", query = "SELECT c FROM Contestant c")
    , @NamedQuery(name = "Contestant.findById", query = "SELECT c FROM Contestant c WHERE c.id = :id")
    , @NamedQuery(name = "Contestant.findByArtist", query = "SELECT c FROM Contestant c WHERE c.artist = :artist")
    , @NamedQuery(name = "Contestant.findByCountry", query = "SELECT c FROM Contestant c WHERE c.country = :country")
    , @NamedQuery(name = "Contestant.findBySong", query = "SELECT c FROM Contestant c WHERE c.song = :song")})
public class Contestant implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "ARTIST")
    private String artist;
    
    @Basic(optional = false)
    @Column(name = "COUNTRY")
    private String country;
    
    @Basic(optional = false)
    @Column(name = "SONG")
    private String song;
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER,
            mappedBy = "contestant")
    private List<Performance> performanceList;

    public Contestant() {
    }

    public Contestant(Long id) {
        this.id = id;
    }

    public Contestant(String artist, String song, String country) {
        this.artist = artist;
        this.country = country;
        this.song = song;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

   public List<Performance> getPerformances() {
        if (performanceList == null) {
            performanceList = new ArrayList<>();
        }
        return this.performanceList;
    }

    public void setPerformances(List<Performance> performances) {
        this.performanceList = performances;
    }

    public void addPerformance(Performance performance) {
        getPerformances().add(performance);
        performance.setContestant(this);
    }

    public void removePerformance(Performance performance) {
        getPerformances().remove(performance);
        //performance.setContestant(null);
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
        if (!(object instanceof Contestant)) {
            return false;
        }
        Contestant other = (Contestant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contestant: " + country + ", " + artist + " - " + song + ", ID = " + id;
    }
    
}
