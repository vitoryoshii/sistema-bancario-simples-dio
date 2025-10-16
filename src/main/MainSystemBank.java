package main;

import menus.MainMenu;
import util.BankRepository;

import java.util.Scanner;

public class MainSystemBank {

    // Main start of the entire system
    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);
        MainMenu main = new MainMenu(scanner);

        main.exibir();
    }
}
