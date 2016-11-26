package br.eduardoklosowski.visualnovel;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Player {
    public static final int MAIN_MENU_NEW_GAME = 1;
    public static final int MAIN_MENU_CONTINUE = 2;
    public static final int MAIN_MENU_EXIT = 3;

    public static final int SAVE_MENU_LOAD = 1;
    public static final int SAVE_MENU_DELETE = 2;
    public static final int SAVE_MENU_EXIT = 3;

    public static final int INGAME_MENU_CONTINUE = 1;
    public static final int INGAME_MENU_SAVE = 2;
    public static final int INGAME_MENU_EXIT = 3;

    private static Player player = new Player();
    private Scanner in = new Scanner(System.in);
    private PrintStream out = System.out;

    private Player() {
    }

    public static Player getPlayer() {
        return player;
    }

    public int readMainMenu() {
        for (;;) {
            out.println();
            out.println("Menu Principal:");
            out.println(" 1 - Novo Jogo");
            out.println(" 2 - Continuar");
            out.println(" 3 - Sair");
            out.print("Opção: ");

            int value = in.nextInt();
            if (value >= MAIN_MENU_NEW_GAME && value <= MAIN_MENU_EXIT) {
                return value;
            }
        }
    }

    public int readSaveMenu() {
        for (;;) {
            out.println();
            out.println("Opções:");
            out.println(" 1 - Carregar jogo");
            out.println(" 2 - Apagar jogo");
            out.println(" 3 - Voltar");
            out.print("Opção: ");

            int value = in.nextInt();
            if (value >= SAVE_MENU_LOAD && value <= SAVE_MENU_EXIT) {
                return value;
            }
        }
    }

    public Save readSave(List<Save> list) {
        int lenList = list.size();

        for (;;) {
            out.println();
            out.println("Jogos:");
            out.println(" 0 - Voltar");
            for (int i = 0; i < lenList; i++) {
                Save save = list.get(i);
                out.println(" " + (i + 1) + " - " + save.getDate());
            }

            int value = in.nextInt();
            if (value == 0) {
                return null;
            }
            if (value > 0 && value <= lenList) {
                return list.get(value - 1);
            }
        }
    }

    public int readInGameMenu() {
        for (;;) {
            out.println();
            out.println("Opções:");
            out.println(" 1 - Continuar");
            out.println(" 2 - Salvar");
            out.println(" 3 - Voltar para o menu principal");
            out.print("Opção: ");

            int value = in.nextInt();
            if (value >= INGAME_MENU_CONTINUE && value <= INGAME_MENU_EXIT) {
                return value;
            }
        }
    }

    public Option readOption(History history) {
        List<Option> options = history.getOptions();
        int lenOptions = options.size();

        for (;;) {
            out.println();
            out.println(history.getText());
            if (lenOptions == 0) {
                throw new ReturnMainMenuException();
            }
            out.println(" 0 - Menu");
            for (int i = 0; i < lenOptions; i++) {
                Option option = options.get(i);
                out.println(" " + (i + 1) + " - " + option.getText());
            }

            int value = in.nextInt();
            if (value == 0) {
                return null;
            }
            if (value > 0 && value <= lenOptions) {
                return options.get(value - 1);
            }
        }
    }
}
