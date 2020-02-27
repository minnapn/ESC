/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import esc.domain.Contestant;

/**
 *
 * @author minna
 */
public class ContestantMenu extends Menu {

    public ContestantMenu() {
        super("CONTESTANT MENU");
    }

    @Override
    protected boolean performOption(int option) {
        boolean goBack = false;
        switch (option) {
            case 1:
                UI.showContestants(escManager);
                break;
            case 2:
                addContestant();
                break;
            case 3:
                 editContestant();
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

    private void addContestant() {
        String artist = Utils.askForString("Artist: ");
        String song = Utils.askForString("Song: ");
        String country = Utils.askForString("Country: ");
        escManager.createContestant(artist, song, country);
    }


    @Override
    protected void showOptions() {
        System.out.println("1. See all contestants");
        System.out.println("2. Add contestant");
        System.out.println("3. Go to specific contestant");
        System.out.println("0. Go back to main menu");

    }

    private void editContestant() {
        Long id = UI.showContestantsAndAskForId(escManager);
        Contestant contestant = escManager.getContestantById(id);
        new EditContestantMenu(contestant).show();
    }    
    
}
