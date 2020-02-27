/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.Menu.escManager;
import esc.ESCmanager;
import esc.VoterManager;
import esc.domain.Contest;
import esc.domain.Contestant;
import esc.domain.Performance;
import esc.domain.Voter;
import java.util.List;

/**
 *
 * @author minna
 */
public class UI {
            
    protected static Long showContestsAndAskForId(ESCmanager escManager){
        showAllContests(escManager);
        Long contestId = Utils.askForLong("ID of contest: ");
        return contestId;
    }
    
    protected static Long showVotersAndAskForId(VoterManager voterManager){
        List<Voter> voters = voterManager.getAllVoters();
        showList(voters, "VOTERS");
        Long voterId = Utils.askForLong("ID of voter: ");
        return voterId;
    }

    protected static Long showContestantsAndAskForId(ESCmanager escManager) {
        List<Contestant> contestants = escManager.getAllContestants();
        showList(contestants, "CONTESTANTS");
        Long contestantId = Utils.askForLong("ID of contestant: ");
        return contestantId;
    }
    
     protected static void showAllContests(ESCmanager escManager) {
        List<Contest> contests = escManager.getAllContests();
        showList(contests, "CONTESTS");
    }
     
     protected static <A> void showList(List<A> theList, String header){
           System.out.println();
           System.out.println(header);
           Utils.line();
            for (Object object : theList) {
                System.out.println(object);                
            }
            Utils.line();            
     }
     
     
     protected static String printFormatPerformance(Performance performance) {
        return Utils.fixLengthString(performance.getStartnumber(), 3)
               + Utils.fixLengthString(performance.getContestant().getCountry(), 10) + " " 
               + Utils.fixLengthString(performance.getContestant().getArtist(), 10) + " - " 
                + Utils.fixLengthString(performance.getContestant().getSong(), 10) + " ";
    }

    static void showContestants(ESCmanager escManager) {
        List<Contestant> contestants = escManager.getAllContestants();
        showList(contestants, "CONTESTANTS");
    }
    

}
