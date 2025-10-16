package main;

import menus.MainMenu;

import java.util.Scanner;

/**
 * Represents the main entry point and bootstrap class for the entire Bank System application.
 * <p>
 * This class is responsible for initializing the core resources, such as the user input
 * handler (Scanner), and launching the primary user interface ({@link menus.MainMenu}).
 */
public class MainSystemBank {

    /**
     * The main method that initiates the execution of the banking system application.
     * <p>
     * The process involves setting up the {@code Scanner} object for user interaction
     * and starting the main display loop via the {@code MainMenu} instance.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);
        MainMenu main = new MainMenu(scanner);

        main.exibir();
    }
}
