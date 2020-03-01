package UI;

import esc.domain.Contest;
import esc.domain.Contestant;
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
        String sure = UI.askForString("Are you sure? Press Y to continue or any other key to abort");
        if (sure.equalsIgnoreCase("y")) {
            escManager.deleteContestantById(contestant.getId());
            return true;
        } else {
            System.out.println("Cancelled");
            return false;
        }
    }

    private void editCountry() {
        String newCountry = UI.askForString("New country: ");
        escManager.editContestantCountry(contestant, newCountry);
        updateHeader(contestant.toString());
    }

    private void editArtist() {
        String newArtist = UI.askForString("New artist: ");
        escManager.editContestantArtist(contestant, newArtist);
        updateHeader(contestant.toString());
    }

    private void editSong() {
        String newSong = UI.askForString("New song: ");
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
        Long contestId = UI.askForLong("ID: ");
        int startnumber = UI.askForInt("Startnumber: ");
        Contest contest = escManager.getContest(contestId);
        escManager.addPerformance(startnumber, contestant, contest);
    }

    private void deletePerformanceInContest() {
        UI.showList(getAllContestsForContestant(), "CONTESTS");
        Long contestId = UI.askForLong("ID of contest: ");
        Contest contest = escManager.getContest(contestId);
        escManager.deletePerformance(contestant, contest);
    }

}
