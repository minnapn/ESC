package UI;

import esc.VoterManager;
import esc.domain.Contest;
import esc.domain.Voter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author minna
 */
public class EditVoterMenu extends Menu{
    
    private Voter voter;
    private VoterManager voterManager = new VoterManager();

    public EditVoterMenu(Voter voter) {
        super(voter.toString());
        this.voter = voter;
    }

    @Override
    protected boolean performOption(int option) {
boolean goBack = false;
        switch (option) {
            case 1:
                editName();
                updateHeader(voter.toString());
                break;
            case 2:
                showContestsForVoter();
                break;
            case 3:
                addContestToVoter();
                break;
            case 4:
                removeContestFromVoter();
                break;
            case 5:
                goBack = deleteVoter();
                break;
            case 0:
                goBack = true;
                break;
            default:
                System.out.println("Not a valid choice");
                break;

        }
        return !goBack;    }

    @Override
    protected void showOptions() {
        System.out.println("1. Edit name");
        System.out.println("2. See all contests attatched");
        System.out.println("3. Add contest");
        System.out.println("4. Remove voter form contest");
        System.out.println("5. Delete");    
        System.out.println("0. Go back");    
    }

    private void editName() {
        String newName = UI.askForString("New name: ");
        voterManager.editVoterName(voter, newName);

    }

    private void showContestsForVoter() {
        UI.showList(voter.getContests(), "Contests "+ voter.getName() + " is voting in:");
    }

    private void addContestToVoter() {
        List<Contest> contestsAvailable = escManager.getAllContests();
        contestsAvailable.removeAll(voter.getContests());
        UI.showList(contestsAvailable, "CONTESTS AVAILABLE");
        Long contestId = UI.askForLong("Choose contest by ID: ");
        Contest contest = escManager.getContest(contestId);
        escManager.addVoterToContest(voter, contest);
    }

    private void removeContestFromVoter() {
        showContestsForVoter();
        Long contestId = UI.askForLong("ID of contest to remove: ");
        Contest contest = voter.getContests().stream().filter(c -> c.getId().equals(contestId)).findAny().get();
        voterManager.removeVoterFromContest(contest, voter);
    }

    private boolean deleteVoter() {
        String sure = UI.askForString("Are you sure? Press Y to continue or any other key to abort");
            if (sure.equalsIgnoreCase("y")) {
            voterManager.deleteVoter(voter.getId());
            return true;
        } else {
            System.out.println("Cancelled");
            return false;
        }    }
    
}
