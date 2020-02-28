package UI;

import esc.domain.Contest;

/**
 *
 * @author minna
 */
public class MainMenu extends Menu {

    public MainMenu() {
        super("MAIN MENU");
    }

    @Override
    protected boolean performOption(int option) {
        boolean goBack = false;
        switch (option) {
            case 1:
                new ContestMenu().show();
                break;
            case 2:
                new VoterMenu().show();
                break;
            case 3:
                new ContestantMenu().show();
                break;
            case 4: 
                Long id = UI.showContestsAndAskForId(escManager);
                Contest contest = escManager.getContest(id);
                new VotingMode(contest).show();
                break;
            case 5:
                new StatisticsMenu().show();
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
        System.out.println("1. Contests");
        System.out.println("2. Voters");
        System.out.println("3. Contestants");
        System.out.println("4. Voting mode");
        System.out.println("5. Statistics");
        System.out.println("0. Exit");
    }

}
