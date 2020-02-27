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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author minna
 */
@Entity
@Table(name = "contest")
@NamedQueries({
    @NamedQuery(name = "Contest.findAll", query = "SELECT c FROM Contest c")
    , @NamedQuery(name = "Contest.findById", query = "SELECT c FROM Contest c WHERE c.id = :id")
    , @NamedQuery(name = "Contest.findByName", query = "SELECT c FROM Contest c WHERE c.name = :name")})
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NAME")
    private String name;
    
    @JoinTable(name = "contest_voter", joinColumns = {
        @JoinColumn(name = "contests_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "voters_ID", referencedColumnName = "ID")})
    @ManyToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    private List<Voter> voterList;
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER,
                mappedBy = "contest")
    private List<Performance> performanceList;

    public Contest() {
    }

    public Contest(String name) {
        this.name = name;
    }
    
    

    public Contest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public List<Voter> getVoters() {
        if (voterList == null) {
            voterList = new ArrayList<>();
        }
        return this.voterList;
    }

    public void setVoters(List<Voter> voterList) {
        this.voterList = voterList;
    }

    public void addVoter(Voter voter) {
        getVoters().add(voter);
        voter.getContests().add(this);
    }

    public void removeVoter(Voter voter) {
        getVoters().remove(voter);
        voter.getContests().remove(this);
    }

        
    public List<Performance> getPerformances() {
        if (performanceList == null) {
            performanceList = new ArrayList<>();
        }
        return this.performanceList;
    }

    public void setPerformances(List<Performance> performanceList) {
        this.performanceList = performanceList;
    }

    public void addPerformance(Performance performance) {
        getPerformances().add(performance);
        performance.setContest(this);
    }

    public void removePerformance(Performance performance) {
        getPerformances().remove(performance);
        //performance.setContest(null);
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
        if (!(object instanceof Contest)) {
            return false;
        }
        Contest other = (Contest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " (ID:" + id+")";
    }
    
}
