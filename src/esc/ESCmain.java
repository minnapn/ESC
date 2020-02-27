/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esc;

import UI.MainMenu;
import esc.domain.Contest;
import esc.domain.Contestant;
import esc.domain.Performance;
import esc.domain.PerformancePK;
import esc.domain.Voter;

/**
 *
 * @author minna
 */
public class ESCmain {

    public static void main(String[] args) {

        ESCmanager manager = new ESCmanager();
        VoterManager voterManager = new VoterManager();
        Statistics statistics = new Statistics();
        
        new MainMenu().show();

//        Contest contest2 = manager.createContest("SEMI 2 2020");
//
//        Contestant contestant = manager.createContestant("LalaArtist", "Fairytale", "Norge");
//
//        Performance NorgeSEMI2 = manager.addPerformance(2, contestant, contest2);
//
//        System.out.println(manager.getAllContests());
//
//        Voter voterDavid = voterManager.createVoter("David");
//        Voter voterS = voterManager.createVoter("Sigird");
//        System.out.println(voterManager.getAllVoters());
//
//        manager.addVoterToContest(voterS, contest2);
//        manager.addVoterToContest(voterDavid, contest2);
//        
//        voterManager.giveVote(voterS, NorgeSEMI2, 4);
        //Performance NorgeSEMI2 = manager.getPerformanceById(new PerformancePK(201L, 202L));
        //Performance SverigeSEMI2 = manager.addPerformance(3, 102L, 201L);
        
        //voterManager.giveVote(voterManager.getVoter(203L), SverigeSEMI2, 5);
        
        //Contest semi2 = manager.getContest(201L);
        //System.out.println(statistics.performancesSortedByAverageGrade(semi2));
        //System.out.println(semi2.getPerformances());
        //System.out.println(semi2.getVoters());
        //System.out.println(statistics.averageGradeForPerformance(NorgeSEMI2));
        
        

    }
}
