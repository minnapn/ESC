/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esc;

import esc.domain.Performance;
import esc.domain.Voter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 *
 * @author minna
 */
public class StatisticsManager {

    public static double getAverageVoteForPerformance(Performance performance) {
        try {
            double average = performance.getVotes().stream()
                    .mapToDouble(v -> v.getGrade())
                    .average()
                    .getAsDouble();
            return average;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public static List<Performance> getPerformancesSortedDesc(List<Performance> performances) {
        List<Performance> performancesSorted = performances.stream()
                .sorted((p1, p2) -> (int) (100 * (getAverageVoteForPerformance(p2) - getAverageVoteForPerformance(p1))))
                .collect(Collectors.toList());
        return performancesSorted;
    }
    
    public static List<Voter> getVotersSortedByAverageVoteDesc(List<Voter> voters) {
        voters = voters.stream()
                        .sorted((v1, v2) -> (int) (100* getAverageVoteForVoter(v2) -
                        - getAverageVoteForVoter(v1)))
                        .collect(Collectors.toList());

        return voters;

    }
    
    public static double getAverageVoteForVoter(Voter voter) {
        try {
        double average = voter.getVotes().stream()
                        .mapToDouble(vote -> vote.getGrade())
                        .average()
                        .getAsDouble();
                        System.out.println("in getAverageVoteForVoter, average = " + average);
        return average;
        }catch (NoSuchElementException e) {
            return 0;
        }
    }


}
