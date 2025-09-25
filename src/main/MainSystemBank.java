package main;

import menus.MainMenu;
import util.BankRepository;

public class MainSystemBank {

    // Main start of the entire system
    public static void main(String[] args) {
        BankRepository bankRepository = new BankRepository();
        new MainMenu(bankRepository).exibir();
    }
}
