/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import esc.VoterManager;
import esc.domain.Voter;

/**
 *
 * @author minna
 */
public class VoterMenu extends Menu {

    private VoterManager voterManager = new VoterManager();

    public VoterMenu() {
        super("VOTER MENU");
    }

    @Override
    protected boolean performOption(int option) {
        boolean goBack = false;
        switch (option) {
            case 1: 
                UI.showList(voterManager.getAllVoters(), "VOTERS");
                break;
            case 2:
                createVoter();
                break;
            case 3:
                editVoter();
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

    public void createVoter() {
        String name = UI.askForString("Name: ");
        voterManager.createVoter(name);
    }

    @Override
    protected void showOptions() {
        System.out.println("1. See all voters");
        System.out.println("2. Add voter");
        System.out.println("3. Go to specific voter");
        System.out.println("0. Go back to main menu");

    }

    private void editVoter() {
        Long id = UI.showVotersAndAskForId(voterManager);
        Voter voter = voterManager.getVoter(id);
        new EditVoterMenu(voter).show();
       }

}
