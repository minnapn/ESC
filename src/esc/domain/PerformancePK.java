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
public class PerformancePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CONTEST_ID")
    private long contestId;
    
    @Basic(optional = false)
    @Column(name = "CONTESTANT_ID")
    private long contestantId;

    public PerformancePK() {
    }

    public PerformancePK(long contestId, long contestantId) {
        this.contestId = contestId;
        this.contestantId = contestantId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contestId;
        hash += (int) contestantId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerformancePK)) {
            return false;
        }
        PerformancePK other = (PerformancePK) object;
        if (this.contestId != other.contestId) {
            return false;
        }
        if (this.contestantId != other.contestantId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "esc.PerformancePK[ contestId=" + contestId + ", contestantId=" + contestantId + " ]";
    }
    
}
