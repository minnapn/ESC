/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import esc.ESCmanager;
import esc.VoterManager;
import esc.domain.Contest;
import esc.domain.Contestant;
import esc.domain.Performance;
import esc.domain.Voter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author minna
 */
public class UI {

    static Scanner scanner = new Scanner(System.in);
            
    protected static Long showContestsAndAskForId(ESCmanager escManager){
        showAllContests(escManager);
        Long contestId = askForLong("ID of contest: ");
        return contestId;
    }
    
    protected static Long showVotersAndAskForId(VoterManager voterManager){
        List<Voter> voters = voterManager.getAllVoters();
        showList(voters, "VOTERS");
        Long voterId = askForLong("ID of voter: ");
        return voterId;
    }

    protected static Long showContestantsAndAskForId(ESCmanager escManager) {
        List<Contestant> contestants = escManager.getAllContestants();
        showList(contestants, "CONTESTANTS");
        Long contestantId = askForLong("ID of contestant: ");
        return contestantId;
    }
    
     protected static void showAllContests(ESCmanager escManager) {
        List<Contest> contests = escManager.getAllContests();
        showList(contests, "CONTESTS");
    }
     
     protected static <A> void showList(List<A> theList, String header){
           System.out.println();
           System.out.println(header);
           line();
            for (Object object : theList) {
                System.out.println(object);                
            }
            line();            
     }
     
     
     protected static String printFormatPerformance(Performance performance) {
        return fixLengthString(performance.getStartnumber(), 3)
               + fixLengthString(performance.getContestant().getCountry(), 10) + " " 
               + fixLengthString(performance.getContestant().getArtist(), 10) + " - " 
                + fixLengthString(performance.getContestant().getSong(), 10) + " ";
    }

    static void showContestants(ESCmanager escManager) {
        List<Contestant> contestants = escManager.getAllContestants();
        showList(contestants, "CONTESTANTS");
    }

    public static String askForString(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    static Long askForLong(String question) {
        long answer = 0L;
        boolean showQuestion = true;
        while (showQuestion) {
            System.out.print(question);
            try {
                answer = scanner.nextLong();
                scanner.nextLine();
                showQuestion = false;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Input should be a number, try again");
            }
        }
        return answer;
    }

    public static void stars() {
        System.out.println("******************************************************************");
    }

    public static int askForInt(String question) {
        int answer = 0;
        boolean showQuestion = true;
        while (showQuestion) {
            System.out.print(question);
            try {
                answer = scanner.nextInt();
                scanner.nextLine();
                showQuestion = false;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Input should be number, try again");
            }
        }
        return answer;
    }

    public static void line() {
        System.out.println("-------------------------------------------------------------------");
    }

    public static String fixLengthString(String start, int length) {
        if (start.length() >= length) {
            return start.substring(0, length);
        } else {
            while (start.length() < length) {
                start += " ";
            }
            return start;
        }
    }

    public static String fixLengthString(int start, int length) {
        String startString = String.valueOf(start);
        return fixLengthString(startString, length);
    }
    
    
    

}
