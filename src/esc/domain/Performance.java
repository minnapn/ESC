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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author minna
 */
@Entity
@Table(name = "performance")
@NamedQueries({
    @NamedQuery(name = "Performance.findAll", query = "SELECT p FROM Performance p")
    , @NamedQuery(name = "Performance.findByStartnumber", query = "SELECT p FROM Performance p WHERE p.startnumber = :startnumber")
    , @NamedQuery(name = "Performance.findByContestId", query = "SELECT p FROM Performance p WHERE p.performancePK.contestId = :contestId")
    , @NamedQuery(name = "Performance.findByContestantId", query = "SELECT p FROM Performance p WHERE p.performancePK.contestantId = :contestantId")})
public class Performance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected PerformancePK performancePK;
    
    @Column(name = "STARTNUMBER")
    private Integer startnumber;
    
    @JoinColumn(name = "CONTESTANT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    private Contestant contestant;
    
    @JoinColumn(name = "CONTEST_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    private Contest contest;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER,
            mappedBy = "performance")
    private List<Vote> voteList;

    public Performance() {
    }

    public Performance(Integer startnumber, Contestant contestant, Contest contest) {
        this.startnumber = startnumber;
        this.contestant = contestant;
        this.contest = contest;
        this.performancePK = new PerformancePK(contest.getId(), contestant.getId());
        contestant.addPerformance(this);
        contest.addPerformance(this);
    }

    
    
    public Performance(PerformancePK performancePK) {
        this.performancePK = performancePK;
    }

    public Performance(long contestId, long contestantId) {
        this.performancePK = new PerformancePK(contestId, contestantId);
    }

    public PerformancePK getPerformancePK() {
        return performancePK;
    }

    public void setPerformancePK(PerformancePK performancePK) {
        this.performancePK = performancePK;
    }

    public Integer getStartnumber() {
        return startnumber;
    }

    public void setStartnumber(Integer startnumber) {
        this.startnumber = startnumber;
    }

    public Contestant getContestant() {
        return contestant;
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
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
        //vote.setPerformance(this);
    }

    public void removeVote(Vote vote) {
        getVotes().remove(vote);
        vote.setPerformance(null);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (performancePK != null ? performancePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Performance)) {
            return false;
        }
        Performance other = (Performance) object;
        if ((this.performancePK == null && other.performancePK != null) || (this.performancePK != null && !this.performancePK.equals(other.performancePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return startnumber + ". " + contestant.getCountry() + " " + contestant.getArtist() + " - " + contestant.getSong();
    }
    
}
