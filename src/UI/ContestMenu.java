/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import esc.domain.Contest;
import java.util.List;

/**
 *
 * @author minna
 */
public class ContestMenu extends Menu {

    public ContestMenu() {
        super("CONTEST MENU");
    }

    @Override
    protected boolean performOption(int option) {
        boolean goBack = false;
        switch (option) {
            case 1:
                UI.showAllContests(escManager);
                break;
            case 2:
                 addContest();
                break;
            case 3:
                 editContest();
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

    public void editContest() {
        Long id = UI.showContestsAndAskForId(escManager);
        Contest contest = escManager.getContest(id);
        new EditContestMenu(contest).show();
    }

    public void addContest() {
        String name = Utils.askForString("Name: ");
        escManager.createContest(name);
        System.out.println("Contest added");
    }

   

   
    @Override
    protected void showOptions() {
        System.out.println("1. See all contests");
        System.out.println("2. Add contest");
        System.out.println("3. Go to specific contest");
        System.out.println("0. Go back to main menu");
    }
    
}
