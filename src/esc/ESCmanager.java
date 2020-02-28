package esc;

import esc.domain.Contest;
import esc.domain.Contestant;
import esc.domain.Performance;
import esc.domain.PerformancePK;
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

    public Contest createContest(String name) {
        EntityManager em = factory.createEntityManager();
        
        em.getTransaction().begin();
        Contest contest = new Contest(name);
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
    
    public void editNameOfContest(Contest contest, String newName) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        contest.setName(newName);
        em.merge(contest);
        em.getTransaction().commit();

        em.close();

    }

    public void addVoterToContest(Voter voter, Contest contest) {
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        contest.addVoter(voter);
        em.merge(contest);
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

        em.getTransaction().begin();
        Performance performance = new Performance(startNumber, contestant, contest);
        em.persist(performance);
        em.getTransaction().commit();

        em.close();

        return performance;
    }



    public Performance getPerformanceByStartNbr(int startNmbr, Contest contest) {
        Performance performance = contest.getPerformances().stream()
                .filter(p -> p.getStartnumber() == startNmbr)
                .findAny().get();

        return performance;
    }


    public List<Contestant> getAllContestants() {
        EntityManager em = factory.createEntityManager();
        List<Contestant> contestants
                = em.createNamedQuery("Contestant.findAll").getResultList();
        em.close();
        return contestants;
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
    

//    public void removePerformance(Performance performance, Contest contest) {
//        EntityManager em = factory.createEntityManager();
//        Contestant contestant = performance.getContestant();
//        em.getTransaction().begin();
//        performance = em.merge(performance);        
//        contest.removePerformance(performance);
//        contestant.removePerformance(performance);
//        em.remove(performance);
//        em.getTransaction().commit();
//        
//        em.close();
//    }
    
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
    
    public Performance findPerformanceForContestantInContest(Contestant contestant, Contest contest){
        EntityManager em = factory.createEntityManager();
        Performance performance = em.find(Performance.class, new PerformancePK(contest.getId(), contestant.getId()));
        em.close();
        return performance;
    }
    
 
     
        public List<Performance> getAllPerformances() {
        EntityManager em = factory.createEntityManager();
        List<Performance> performances
                = em.createNamedQuery("Performance.findAll").getResultList();
        em.close();
        return performances;
    }
      

}
