/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import esc.ESCmanager;
import esc.domain.Contest;
import esc.domain.Contestant;
import esc.domain.Performance;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author minna
 */
public class EditContestantMenu extends Menu {

    private Contestant contestant;

    public EditContestantMenu(Contestant contestant) {
        super(contestant.toString());
        this.contestant = contestant;

    }

    @Override
    protected boolean performOption(int option) {
        boolean goBack = false;
        switch (option) {
            case 1:
                editCountry();
                updateHeader(contestant.toString());
                break;
            case 2:
                editArtist();
                updateHeader(contestant.toString());
                break;
            case 3:
                editSong();
                updateHeader(contestant.toString());
                break;
            case 4:
                showPerformances();
                break;
            case 5:
                addPerformanceToContest();
                break;
            case 6:
                deletePerformanceInContest();
                break;
            case 7:
                goBack = deleteContestant();
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
        System.out.println("1. Edit country");
        System.out.println("2. Edit artist");
        System.out.println("3. Edit song");
        System.out.println("4. See all performances");
        System.out.println("5. Add performance to a contest");
        System.out.println("6. Delete performance in a contest");
        System.out.println("7. Delete");
        System.out.println("0. Go back");

    }

    private boolean deleteContestant() {
        String sure = Utils.askForString("Are you sure? Press Y to continue or any other key to abort");
        if (sure.equalsIgnoreCase("y")) {
            escManager.deleteContestantById(contestant.getId());
            return true;
        } else {
            System.out.println("Cancelled");
            return false;
        }
    }

    private void editCountry() {
        String newCountry = Utils.askForString("New country: ");
        escManager.editContestantCountry(contestant, newCountry);
        updateHeader(contestant.toString());
    }

    private void editArtist() {
        String newArtist = Utils.askForString("New artist: ");
        escManager.editContestantArtist(contestant, newArtist);
        updateHeader(contestant.toString());
    }

    private void editSong() {
        String newSong = Utils.askForString("New song: ");
        escManager.editContestantSong(contestant, newSong);
        updateHeader(contestant.toString());
    }

    private void showPerformances() {
        List<String> performances
                = contestant.getPerformances().stream()
                        .map(p -> p.getContest() + ", startnumber: " + p.getStartnumber())
                        .collect(Collectors.toList());
        UI.showList(performances, "Performances for " + contestant);
    }
    
    public List<Contest> getAllContestsForContestant(){
        List<Contest> contestsForContestant = contestant.getPerformances().stream()
                        .map(p -> p.getContest())
                        .collect(Collectors.toList());
        return contestsForContestant;
    }

    private void addPerformanceToContest() {
        List<Contest> contests = escManager.getAllContests();
        contests.removeAll(getAllContestsForContestant());
        UI.showList(contests, "AVAILABLE CONTESTS");
        Long contestId = Utils.askForLong("ID: ");
        int startnumber = Utils.askForInt("Startnumber: ");
        Contest contest = escManager.getContest(contestId);
        escManager.addPerformance(startnumber, contestant, contest);
    }

    private void deletePerformanceInContest() {
        UI.showList(getAllContestsForContestant(), "CONTESTS");
        Long contestId = Utils.askForLong("ID of contest: ");
        Contest contest = escManager.getContest(contestId);
        escManager.removePerformance(contestant, contest);
    }

}
