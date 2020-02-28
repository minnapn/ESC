package esc;

import esc.domain.Contest;
import esc.domain.Performance;
import esc.domain.Vote;
import esc.domain.Voter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author minna
 */
public class VoterManager {

    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ESC_PU");

    public Voter createVoter(String name) {
        EntityManager em = factory.createEntityManager();
        
        em.getTransaction().begin();
        Voter voter = new Voter(name);
        em.persist(voter);
        em.getTransaction().commit();

        em.close();
        return voter;
    }

    public void deleteVoter(Long id) {
        EntityManager em = factory.createEntityManager();
        Voter voter = em.find(Voter.class, id);

        em.getTransaction().begin();
        voter.getVotes().forEach((vote) -> em.remove(vote));
        em.remove(voter);
        em.getTransaction().commit();

        em.close();
    }

    public Voter getVoter(Long id) {
        EntityManager em = factory.createEntityManager();
        Voter voter = em.find(Voter.class, id);
        em.close();
        return voter;
    }

    public List<Voter> getAllVoters() {
        EntityManager em = factory.createEntityManager();
        List<Voter> voters = em.createNamedQuery("Voter.findAll").getResultList();
        em.close();
        return voters;
    }

    public void giveVote(Voter voter, Performance performance, int grade) throws NotValidGradeException {
        if (!validGrade(grade)) {
            throw new NotValidGradeException();
        }
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        Vote vote = new Vote(performance, voter, grade);
        em.persist(vote);
        em.getTransaction().commit();

        em.close();
    }
    
    public boolean validGrade(int grade) {
        return (grade>0 && grade<=5);
    }

    public void updateVote(Vote vote, Voter voter, Performance performance, int grade) throws NotValidGradeException {
         if (!validGrade(grade)) {
            throw new NotValidGradeException();
        }
        EntityManager em = factory.createEntityManager();
       
        em.getTransaction().begin();
        vote.setGrade(grade);
        em.merge(vote);
        em.getTransaction().commit();

        em.close();
    }

    public void removeVoterFromContest(Contest contest, Voter voter) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        contest.removeVoter(voter);
        for (Vote vote : voter.getVotes()) {
            if (vote.getPerformance().getContest().equals(contest)) {
                vote.getPerformance().removeVote(vote);
                em.merge(vote.getPerformance());
                vote = em.merge(vote);
                em.remove(vote);
            }
        }
        em.merge(contest);
        em.getTransaction().commit();

        em.close();
    }

    public Vote findVoteForPerformanceByVoter(Performance performance, Voter voter) {
        try {
            Vote vote = performance.getVotes().stream()
                    .filter(p -> p.getVoter().equals(voter))
                    .findAny().get();
            return vote;
        } catch (Exception e) {
            return null;
        }
    }

    
    public void editVoterName(Voter voter, String newName) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        voter.setName(newName);
        em.merge(voter);
        em.getTransaction().commit();

        em.close();
    }

}
