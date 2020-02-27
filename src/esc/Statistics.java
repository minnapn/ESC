/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esc;

import esc.domain.Contest;
import esc.domain.Performance;
import esc.domain.Vote;
import esc.domain.Voter;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Statistics {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ESC_PU");
    ESCmanager escManager = new ESCmanager();
    
    public double averageGradeForPerformance(Performance performance) {
        EntityManager em = factory.createEntityManager();
        List<Vote> votes 
                = em.createQuery("SELECT v FROM Vote v WHERE v.performance = :performance")
                .setParameter("performance", performance)
                .getResultList();
        
        return votes.stream()
                .mapToDouble(s -> s.getGrade())
                .average().getAsDouble();
        
    }
    
    public double averageGradeForVoter(Voter voter) {
        EntityManager em = factory.createEntityManager();
        List<Vote> votes 
                = em.createQuery("SELECT v FROM Vote v WHERE v.voter = :voter")
                .setParameter("voter", voter)
                .getResultList();
        
        return votes.stream()
                .mapToDouble(s -> s.getGrade())
                .average().getAsDouble();
        
    }
    
    public List<Performance> performancesSortedByAverageGrade(Contest contest) {
        List<Performance> performancesSorted
                = escManager.getAllPerformancesInContest(contest).stream()
                        .sorted((p1,p2)->((int)(1000*(averageGradeForPerformance(p2) - averageGradeForPerformance(p1)))))
                        .collect(Collectors.toList());
        
        return performancesSorted;
    }
    
}
