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
import java.util.stream.Collectors;

/**
 *
 * @author minna
 */
public class EditContestMenu extends Menu {

    private Contest contest;
    private ESCmanager escManager = new ESCmanager();
    private VoterManager voterManager = new VoterManager();

    public EditContestMenu(Contest contest) {
        super("CONTEST " + contest.toString() + " MENU");
        this.contest = contest;
    }

    @Override
    protected boolean performOption(int option) {
        boolean goBack = false;
        switch (option) {
            case 1:
                seeAllVotersInContest();
                break;
            case 2:
                seeAllPerformancesInContest();
                break;
            case 3:
                addVoterToContest();
                break;
            case 4:
                addPerformanceToContest();
                break;
            case 5:
                removeVoter();
                break;
            case 6:
                removePerformance();
                break;
            case 7:
                editName();
                updateHeader(contest.toString());
                break;
            case 8:
                goBack = deleteContest();
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
        System.out.println("1. See all voters in contest");
        System.out.println("2. See all performances in contest");
        System.out.println("3. Add Voter to contest");
        System.out.println("4. Add Performance to contest");
        System.out.println("5. Remove Voter from contest");
        System.out.println("6. Remove Performance from contest");
        System.out.println("7. Edit name");
        System.out.println("8. Delete");        
        System.out.println("0. Go back");
    }

    private boolean deleteContest() {
        String sure = Utils.askForString("Are you sure? Press Y to continue or any other key to abort");
        if (sure.equalsIgnoreCase("y")) {
            escManager.deleteContest(contest.getId());
            return true;
        } else {
            System.out.println("Cancelled");
            return false;
        }
    }

    private void addVoterToContest() {
        List<Voter> availableVoters = voterManager.getAllVoters();
        availableVoters.removeAll(contest.getVoters());
        UI.showList(availableVoters, "AVAILABLE VOTERS");
        Long voterId = Utils.askForLong("Choose voter by ID: ");
        Voter voter = voterManager.getVoter(voterId);
        escManager.addVoterToContest(voter, contest);
        
    }
    
     public List<Contestant> getAllContestantsForContest(){
        List<Contestant> contestantsForContest = contest.getPerformances().stream()
                        .map(p -> p.getContestant())
                        .collect(Collectors.toList());
        return contestantsForContest;
    }

    private void addPerformanceToContest() {
        List<Contestant> availableContestants = escManager.getAllContestants();
        availableContestants.removeAll(getAllContestantsForContest());
        UI.showList(availableContestants, "AVAILABLE CONTESTANTS");
        Long contestantId = Utils.askForLong("Choose contestant by ID: ");
        Contestant contestant = escManager.getContestantById(contestantId);
        int startNbr = Utils.askForInt("Start number: ");
        escManager.addPerformance(startNbr, contestant, contest);

    }

    private void seeAllVotersInContest() {
        UI.showList(contest.getVoters(), "VOTERS IN CONTEST " + contest);
    }

    private void seeAllPerformancesInContest() {
        List<Performance> performances = contest.getPerformances();
        performances.sort((p1,p2) -> p1.getStartnumber() - p2.getStartnumber());
        UI.showList(performances, "PERFORMANCES IN CONTEST " + contest);
    }

    private void removeVoter() {
        seeAllVotersInContest();
        Long id = Utils.askForLong("ID of voter to remove: ");
        Voter voter = voterManager.getVoter(id);
        voterManager.removeVoterFromContest(contest, voter);
    }

    private void removePerformance() {
        seeAllPerformancesInContest();
        int startNmbr = Utils.askForInt("Startnumber of performance to remove: ");
        Performance performance = escManager.getPerformanceByStartNbr(startNmbr, contest);
        escManager.removePerformance(performance, contest);
    }

    private void editName() {
        String newName = Utils.askForString("New name: ");
        escManager.editNameOfContest(contest, newName);
    }

}
