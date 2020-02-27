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
@Table(name = "voter")
@NamedQueries({
    @NamedQuery(name = "Voter.findAll", query = "SELECT v FROM Voter v")
    , @NamedQuery(name = "Voter.findById", query = "SELECT v FROM Voter v WHERE v.id = :id")
    , @NamedQuery(name = "Voter.findByName", query = "SELECT v FROM Voter v WHERE v.name = :name")})
public class Voter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    
    @ManyToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER,
            mappedBy = "voterList")
    private List<Contest> contestList;
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER, 
            mappedBy = "voter")       
    private List<Vote> voteList;

    public Voter() {
    }

    public Voter(Long id) {
        this.id = id;
    }

    public Voter(String name) {
        this.name = name;
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

     public List<Contest> getContests() {
        if (contestList == null) {
            contestList = new ArrayList<>();
        }
        return this.contestList;
    }

    public void setContests(List<Contest> contestList) {
        this.contestList = contestList;
    }

    public void addContest(Contest contest) {
        getContests().add(contest);
    }

    public void removeContest(Contest contest) {
        getContests().remove(contest);
    }


    public List<Vote> getVotes() {
        if (voteList == null) {
            voteList = new ArrayList<>();
        }
        return this.voteList;
    }

    public void setVotes(List<Vote> voteList) {
        this.voteList = voteList;
    }

    public void addVote(Vote vote) {
        getVotes().add(vote);
        //vote.setVoter(this);
    }

    public void removeVote(Vote vote) {
        getVotes().remove(vote);
        vote.setVoter(null);
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
        if (!(object instanceof Voter)) {
            return false;
        }
        Voter other = (Voter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ", ID = " + id;
    }
    
}
