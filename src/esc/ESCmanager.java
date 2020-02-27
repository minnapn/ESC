/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esc;

import esc.domain.Contest;
import esc.domain.Contestant;
import esc.domain.Performance;
import esc.domain.PerformancePK;
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
public class ESCmanager {

    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ESC_PU");

    //CONTEST
    public Contest createContest(String name) {
        EntityManager em = factory.createEntityManager();
        Contest contest = new Contest(name);

        em.getTransaction().begin();
        em.persist(contest);
        em.getTransaction().commit();

        em.close();
        return contest;
    }

    public void deleteContest(Long id) {
        EntityManager em = factory.createEntityManager();
        Contest contest = em.find(Contest.class, id);

        em.getTransaction().begin();
        em.remove(contest);
        em.getTransaction().commit();

        em.close();
    }

    public Contest getContest(Long id) {
        EntityManager em = factory.createEntityManager();
        Contest contest = em.find(Contest.class, id);
        em.close();
        return contest;
    }

    public List<Contest> getAllContests() {
        EntityManager em = factory.createEntityManager();
        List<Contest> contests = em.createNamedQuery("Contest.findAll").getResultList();
        em.close();
        return contests;
    }

    public void addVoterToContest(Voter voter, Contest contest) {
        EntityManager em = factory.createEntityManager();

        //voter.addContest(contest);
        em.getTransaction().begin();
        contest.addVoter(voter);
        em.merge(contest);

        //em.merge(voter);
        em.getTransaction().commit();

        em.close();
    }

    public Contestant createContestant(String artist, String song, String country) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Contestant contestant = new Contestant(artist, song, country);
        em.persist(contestant);
        em.getTransaction().commit();

        em.close();
        return contestant;
    }

    
    public void deleteContestantById(Long id) {
        EntityManager em = factory.createEntityManager();
        Contestant contestant = em.find(Contestant.class, id);

        em.getTransaction().begin();
        contestant.getPerformances().forEach(p -> em.remove(p));
        em.remove(contestant);
        em.getTransaction().commit();

        em.close();
    }

    public Contestant getContestantById(Long id) {
        EntityManager em = factory.createEntityManager();
        Contestant contestant = em.find(Contestant.class, id);
        em.close();
        return contestant;
    }

    public Performance addPerformance(int startNumber, Contestant contestant, Contest contest) {
        EntityManager em = factory.createEntityManager();

        Performance performance = new Performance(startNumber, contestant, contest);

        em.getTransaction().begin();
        em.merge(contestant);
        em.merge(contest);
        em.persist(performance);

        em.getTransaction().commit();

        em.close();

        return performance;
    }

    public Performance addPerformance(int startNumber, Long contestantId, Long contestId) {
        EntityManager em = factory.createEntityManager();

        Contestant contestant = em.find(Contestant.class, contestantId);
        Contest contest = em.find(Contest.class, contestId);

        Performance performance = addPerformance(startNumber, contestant, contest);

        em.close();

        return performance;
    }

    public void deletePerformanceById(PerformancePK performancePK) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        Performance performance = em.find(Performance.class, performancePK);
        performance.getContest().removePerformance(performance);
        performance.getContestant().removePerformance(performance);
        em.remove(performance);
        em.getTransaction().commit();

        em.close();
    }

    public Performance getPerformanceById(PerformancePK performancePK) {
        EntityManager em = factory.createEntityManager();
        Performance performance = em.find(Performance.class, performancePK);
        em.close();
        return performance;
    }

    public Performance getPerformanceByStartNbr(int startNmbr, Contest contest) {
        Performance performance = contest.getPerformances().stream()
                .filter(p -> p.getStartnumber() == startNmbr)
                .findAny().get();

        return performance;
    }

    public List<Performance> getAllPerformancesInContest(Contest contest) {
        EntityManager em = factory.createEntityManager();
        List<Performance> performances
                = em.createNamedQuery("Performance.findByContestId")
                        .setParameter("contestId", contest.getId())
                        .getResultList();
        em.close();
        return performances;
    }

    public List<Contestant> getAllContestants() {
        EntityManager em = factory.createEntityManager();
        List<Contestant> contestants
                = em.createNamedQuery("Contestant.findAll").getResultList();
        em.close();
        return contestants;
    }

    public void editNameOfContest(Contest contest, String newName) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        contest.setName(newName);
        em.merge(contest);
        em.getTransaction().commit();

        em.close();

    }

//    public void removePerformance(Performance performance) {
//        EntityManager em = factory.createEntityManager();
//        performance = em.find(Performance.class, performance.getPerformancePK());
//        em.getTransaction().begin();
//        //performance = em.merge(performance);
//        Contest contest = em.find(Contest.class, performance.getContest().getId());
//        Contestant contestant = em.find(Contestant.class, performance.getContestant().getId());
//        contest.removePerformance(performance);
//        contestant.removePerformance(performance);
//        em.remove(performance);
//        em.refresh(contest);
//        em.refresh(contestant);
//        em.getTransaction().commit();
//        
//        em.close();
//    }
    public void removePerformance(Performance performance, Contest contest) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        performance = em.merge(performance);
        contest.removePerformance(performance);
        em.remove(performance);
        em.getTransaction().commit();

        em.close();
    }
    
    public Performance findPerformanceForContestantInContest(Contestant contestant, Contest contest){
        EntityManager em = factory.createEntityManager();
        Performance performance = em.createQuery("SELECT p FROM Performance p WHERE p.performancePK.contestantId = :contestantId AND p.performancePK.contestId = :contestId", Performance.class)
                .setParameter("contestId", contest.getId())
                .setParameter("contestantId", contestant.getId()).getSingleResult();
        return performance;
    }
    
     public void removePerformance(Contestant contestant, Contest contest) {
        EntityManager em = factory.createEntityManager();
        Performance performance = findPerformanceForContestantInContest(contestant, contest);
        em.getTransaction().begin();
        performance = em.merge(performance);
        contest.removePerformance(performance);
        contestant.removePerformance(performance);
        em.remove(performance);
        em.getTransaction().commit();

        em.close();
    }
     
     

    public void editContestantCountry(Contestant contestant, String newCountry) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        contestant.setCountry(newCountry);
        em.merge(contestant);
        em.getTransaction().commit();

        em.close();
    }

    public void editContestantArtist(Contestant contestant, String newArtist) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        contestant.setArtist(newArtist);
        em.merge(contestant);
        em.getTransaction().commit();

        em.close();
    }

    public void editContestantSong(Contestant contestant, String newSong) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        contestant.setSong(newSong);
        em.merge(contestant);
        em.getTransaction().commit();

        em.close();
    }

    public List<Performance> getAllPerformances() {
        EntityManager em = factory.createEntityManager();
        List<Performance> performances
                = em.createNamedQuery("Performance.findAll")
                        .getResultList();
        em.close();
        return performances;
    }

   
   
    
   

}
