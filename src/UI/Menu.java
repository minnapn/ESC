package UI;

import esc.ESCmanager;
import java.util.Scanner;

/**
 *
 * @author minna
 */
public abstract class Menu {

    private static Scanner scanner = new Scanner(System.in);
    private String header;

    protected static ESCmanager escManager = new ESCmanager();

    protected abstract boolean performOption(int option);

    protected abstract void showOptions();

    public Menu(String header) {
        this.header = header;
    }

    protected int selectOption() {
        return UI.askForInt("Option: ");
    }

    public void show() {
        boolean show = true;
        while (show) {
            menuHeader();
            showOptions();
            int option = selectOption();
            try {
                show = performOption(option);
            } catch (Exception e) {
                System.out.println();
                UI.stars();
                System.out.println("Oooops, something went wrong, more info: " + e);
                UI.stars();
            }
        }
    }

    protected void menuHeader() {
        System.out.println();
        System.out.println(this.header);
        UI.line();
    }

    protected void updateHeader(String newHeader) {
        this.header = newHeader;
    }

}
