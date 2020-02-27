/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esc.domain;

import esc.*;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author minna
 */
@Embeddable
public class VotePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CONTEST_ID")
    private long contestId;
    @Basic(optional = false)
    @Column(name = "CONTESTANT_ID")
    private long contestantId;
    @Basic(optional = false)
    @Column(name = "VOTER_ID")
    private long voterId;

    public VotePK() {
    }

    public VotePK(long contestId, long contestantId, long voterId) {
        this.contestId = contestId;
        this.contestantId = contestantId;
        this.voterId = voterId;
    }

    public long getContestId() {
        return contestId;
    }

    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    public long getContestantId() {
        return contestantId;
    }

    public void setContestantId(long contestantId) {
        this.contestantId = contestantId;
    }

    public long getVoterId() {
        return voterId;
    }

    public void setVoterId(long voterId) {
        this.voterId = voterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contestId;
        hash += (int) contestantId;
        hash += (int) voterId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotePK)) {
            return false;
        }
        VotePK other = (VotePK) object;
        if (this.contestId != other.contestId) {
            return false;
        }
        if (this.contestantId != other.contestantId) {
            return false;
        }
        if (this.voterId != other.voterId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "esc.VotePK[ contestId=" + contestId + ", contestantId=" + contestantId + ", voterId=" + voterId + " ]";
    }
    
}
