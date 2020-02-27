/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import esc.ESCmanager;
import esc.VoterManager;
import esc.domain.Contest;
import esc.domain.Performance;
import esc.domain.PerformancePK;
import esc.domain.Vote;
import esc.domain.Voter;
import esc.notValidGradeException;
import java.util.List;

/**
 *
 * @author minna
 */
public class VotingMode extends Menu {

    private Contest contest;
    private ESCmanager escManager = new ESCmanager();
    private VoterManager voterManager = new VoterManager();
    private List<Voter> voters;

    public VotingMode(Contest contest) {
        super("VOTING MODE");
        this.contest = contest;
        this.voters = contest.getVoters();
    }

    /*public void run() {

        contest.getPerformances().sort((p1, p2) -> p1.getStartnumber() - p2.getStartnumber());
        voters.sort((v1, v2) -> v1.getName().compareTo(v2.getName()));

        printVotingTable();

        vote();

        printVotingTable();

    }*/
    private void vote(int option) throws notValidGradeException {
        Long voterId = new Long(option);
        Voter voter = voterManager.getVoter(voterId);
        int startNbr = Utils.askForInt("Startnumber: ");
        Performance performance = escManager.getPerformanceByStartNbr(startNbr, contest);
        int grade = Utils.askForInt("Grade: ");
        if (voterManager.validGrade(grade)) {
            Vote vote = voterManager.findVoteForPerformanceByVoter(performance, voter);
            if (vote == null) {
                voterManager.giveVote(voter, performance, grade);
            } else {
                voterManager.updateVote(vote, voter, performance, grade);
            }
        } else {
            System.out.println("\nNot a valid vote (should be 1-5)");
        }
    }

    public void printVotingTable() {
        printVotingTableHeader();
        for (Performance performance : contest.getPerformances()) {
            printPerformanceAndVotes(performance);
        }
        Utils.line();
    }

    public void printVotingTableHeader() {
        System.out.println();
        String performance = Utils.fixLengthString("#", 3)
                + Utils.fixLengthString("COUNTRY", 10) + " "
                + Utils.fixLengthString("ARTIST", 10) + "   "
                + Utils.fixLengthString("SONG", 10) + " ";
        System.out.print(performance);
        for (Voter voter : voters) {
            System.out.print(Utils.fixLengthString("(" + voter.getId() + ")" + voter.getName(), 10) + " ");
        }
        System.out.println();
        Utils.line();
    }

    public void printPerformanceAndVotes(Performance performance) {
        List<Vote> votes = performance.getVotes();
        votes.sort((v1, v2) -> v1.getVoter().getName().compareTo(v2.getVoter().getName()));
        System.out.print(UI.printFormatPerformance(performance));
        for (Voter voter : contest.getVoters()) {
            Vote vote = voterManager.findVoteForPerformanceByVoter(performance, voter);
            if (vote != null) {
                System.out.print(Utils.fixLengthString("  " + vote.getGrade(), 10) + " ");
            } else {
                System.out.print(Utils.fixLengthString("  " + " ", 10) + " ");
            }
        }
        System.out.println("");
    }

    @Override
    protected boolean performOption(int option) {
        boolean goBack = false;
        switch (option) {
            case 0:
                goBack = true;
                break;
            default:
                try {
                vote(option);
                }
                catch (Exception e){};
                break;
        }
        return !goBack;
    }

    @Override
    protected void showOptions() {
        contest.getPerformances().sort((p1, p2) -> p1.getStartnumber() - p2.getStartnumber());
        voters.sort((v1, v2) -> v1.getName().compareTo(v2.getName()));
        printVotingTable();
    }

    @Override
    protected int selectOption() {
        int option = Utils.askForInt("Vote by entering ID of voter or press 0 to go back: ");
        return option;
    }

}
