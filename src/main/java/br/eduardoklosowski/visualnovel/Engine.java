package br.eduardoklosowski.visualnovel;

public class Engine implements Runnable {
    public final long INITIAL_HISTORY = 1;

    private Player player = Player.getPlayer();
    private SaveDAO saveDAO = new SaveDAO();
    private HistoryDAO historyDAO = new HistoryDAO();

    public void run() {
        mainMenu();
    }

    public void mainMenu() {
        for (;;) {
            try {
                switch (player.readMainMenu()) {
                    case Player.MAIN_MENU_NEW_GAME:
                        gameMenu(historyDAO.get(INITIAL_HISTORY));
                        throw new ReturnMainMenuException();
                    case Player.MAIN_MENU_CONTINUE:
                        saveMenu();
                        break;
                    case Player.MAIN_MENU_EXIT:
                        return;
                }
            } catch (ReturnMainMenuException e) {
            }
        }
    }

    public void saveMenu() {
        Save save;
        for (;;) {
            switch (player.readSaveMenu()) {
                case Player.SAVE_MENU_LOAD:
                    save = player.readSave(saveDAO.list());
                    if (save != null) {
                        gameMenu(save.getHistory());
                        throw new ReturnMainMenuException();
                    }
                    break;
                case Player.SAVE_MENU_DELETE:
                    save = player.readSave(saveDAO.list());
                    if (save != null) {
                        saveDAO.delete(save);
                    }
                    break;
                case Player.SAVE_MENU_EXIT:
                    return;
            }
        }
    }

    public void inGameMenu(History history) {
        for (;;) {
            switch (player.readInGameMenu()) {
                case Player.INGAME_MENU_CONTINUE:
                    return;
                case Player.INGAME_MENU_SAVE:
                    Save save = new Save();
                    save.setHistory(history);
                    saveDAO.save(save);
                    return;
                case Player.INGAME_MENU_EXIT:
                    throw new ReturnMainMenuException();
            }
        }
    }

    public void gameMenu(History history) {
        for (;;) {
            Option option = player.readOption(history);
            if (option == null) {
                inGameMenu(history);
            } else {
                history = option.getNext();
            }
        }
    }
}
