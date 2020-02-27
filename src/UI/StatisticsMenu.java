/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import esc.StatisticsManager;
import esc.VoterManager;
import esc.domain.Contest;
import esc.domain.Performance;
import esc.domain.Voter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author minna
 */
public class StatisticsMenu extends Menu {

    VoterManager voterManager = new VoterManager();
    
    public StatisticsMenu() {
        super("STATISTICS");
    }

    @Override
    protected boolean performOption(int option) {
        boolean goBack = false;
        switch (option) {
            case 1:
                showVotersSortedByAverageVote();
                break;
            case 2:
                 sortByAverageVoteInContest();
                break;
            case 3:
                 showBestPerformance();
                break;
            case 4:
                showWorstPerformance();
                break;
            case 0:
                goBack = true;
                break;
            default:
                System.out.println("Not a valid choice");
                break;
        }
        return !goBack;
    }

    @Override
    protected void showOptions() {
        System.out.println("1. See voters sorted by average vote");
        System.out.println("2. See performances sorted by average vote in a specific contest");
        System.out.println("3. See performance with all time highest average vote");
        System.out.println("4. See performance with all time lowest average vote");
        System.out.println("0. Go back");    
    }

    private void showVotersSortedByAverageVote() {
        List<Voter> votersSorted = StatisticsManager.getVotersSortedByAverageVoteDesc(voterManager.getAllVoters());
        List<String> votersString = votersSorted.stream()
                .map(v -> v.toString() + ", average vote: " + StatisticsManager.getAverageVoteForVoter(v))
                .collect(Collectors.toList());
                       
        UI.showList(votersString, "Voters sorted by average vote");     
    }

    private void showWorstPerformance() {
        List<Performance> performances = escManager.getAllPerformances();        
        Performance performance = StatisticsManager.getPerformancesSortedDesc(performances).get(performances.size()-1);
       System.out.println("\nThe performance of [" + performance.getContestant() + "] in contest [" + performance.getContest() + "] has lowest average vote: " + StatisticsManager.getAverageVoteForPerformance(performance));    }

    private void showBestPerformance() {
       Performance performance = StatisticsManager.getPerformancesSortedDesc(escManager.getAllPerformances()).get(0);
       System.out.println("\nThe performance of [" + performance.getContestant() + "] in contest [" + performance.getContest() + "] has highest average vote: " + StatisticsManager.getAverageVoteForPerformance(performance));
        
         
    }

    private void sortByAverageVoteInContest() {
        Long id = UI.showContestsAndAskForId(escManager);
        Contest contest = escManager.getContest(id);
        List<Performance> performancesSorted = StatisticsManager.getPerformancesSortedDesc(contest.getPerformances());
        
        List<String> performancesString = performancesSorted.stream()
                .map(p -> p.toString() + ", average vote: " + StatisticsManager.getAverageVoteForPerformance(p))
                .collect(Collectors.toList());
        UI.showList(performancesString, contest.toString());
        
    }
    
}
