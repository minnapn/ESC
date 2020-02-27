/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esc.domain;

import esc.*;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author minna
 */
@Entity
@Table(name = "vote")
@NamedQueries({
    @NamedQuery(name = "Vote.findAll", query = "SELECT v FROM Vote v")
    , @NamedQuery(name = "Vote.findByGrade", query = "SELECT v FROM Vote v WHERE v.grade = :grade")
    , @NamedQuery(name = "Vote.findByNote", query = "SELECT v FROM Vote v WHERE v.note = :note")
    , @NamedQuery(name = "Vote.findByContestId", query = "SELECT v FROM Vote v WHERE v.votePK.contestId = :contestId")
    , @NamedQuery(name = "Vote.findByContestantId", query = "SELECT v FROM Vote v WHERE v.votePK.contestantId = :contestantId")
    , @NamedQuery(name = "Vote.findByVoterId", query = "SELECT v FROM Vote v WHERE v.votePK.voterId = :voterId")})
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected VotePK votePK;

    @Basic(optional = false)
    @Column(name = "GRADE")
    private int grade;

    @Column(name = "NOTE")
    private String note;

    @JoinColumns({
        @JoinColumn(name = "CONTEST_ID", referencedColumnName = "CONTEST_ID", insertable = false, updatable = false)
        , @JoinColumn(name = "CONTESTANT_ID", referencedColumnName = "CONTESTANT_ID", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Performance performance;

    @JoinColumn(name = "VOTER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Voter voter;

    public Vote() {
    }

    public Vote(VotePK votePK) {
        this.votePK = votePK;
    }

    public Vote(VotePK votePK, int grade) {
        this.votePK = votePK;
        this.grade = grade;
    }

    public Vote(VotePK votePK, int grade, String note) {
        this.votePK = votePK;
        this.grade = grade;
        this.note = note;
    }

    public Vote(long contestId, long contestantId, long voterId) {
        this.votePK = new VotePK(contestId, contestantId, voterId);
    }

    public Vote(Performance performance, Voter voter, int grade) {
        this.votePK = this.votePK = new VotePK(performance.getContest().getId(), performance.getContestant().getId(), voter.getId());
        this.grade = grade;
        setPerformance(performance);
        setVoter(voter);
    }

    public VotePK getVotePK() {
        return votePK;
    }

    public void setVotePK(VotePK votePK) {
        this.votePK = votePK;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
        performance.addVote(this);
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
        voter.addVote(this);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (votePK != null ? votePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vote)) {
            return false;
        }
        Vote other = (Vote) object;
        if ((this.votePK == null && other.votePK != null) || (this.votePK != null && !this.votePK.equals(other.votePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return grade + " from " + voter.getName();
    }

}
