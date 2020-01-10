package core.game;

import core.game.Player;
import core.game_engine.GameManager;
import core.game.Platform;
import core.game_engine.data_management.DataManager;
import core.game_engine.input_command.InputController;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.logging.Level;


public class TestGame {
    private PApplet parent;
    private GameManager game_manager;
    Platform gamePlatform;
    Player player;
    InputController playerInput;
    DataManager dataManager;
    GameMode gameMode = GameMode.START;
    LevelManager levelManager;

    public TestGame(PApplet p) {
        this.parent = p;
    }

    public void start() {
        game_manager = new GameManager(this.parent);
        dataManager = new DataManager(this.parent);
        levelManager = new LevelManager(this.parent, game_manager);
        //load_game();
    }

    private void load_game() {
        this.game_manager.startup();
        dataManager.load();

        // add player
        player = (Player) levelManager.load_players().get(0);
        playerInput = new InputController(player);
        levelManager.load_level("LevelOne");
    }

    public void update() {
        switch (gameMode) {
            case START:
                welcome_screen();
                break;
            case PLAY:
                playerInput.checkInput();
                game_manager.update();
                break;
            case EDIT:
                //level editor
                levelManager.update();
                game_manager.update();
                break;
            case RELOAD:
                //loads levels
                load_game();
                gameMode = GameMode.PLAY;
                break;
        }
    }

    public void keyReleased(char key, int keycode) {
        switch (gameMode) {
            case START:
                switch (key) {
                    case '1':
                        System.out.println("open edit mode");
                        gameMode = GameMode.EDIT;
                        game_manager.startup();
                        levelManager.load_level("LevelOne");
                        break;
                    default:
                        gameMode = GameMode.RELOAD;
                        break;
                }
                break;
            case PLAY:
                playerInput.keyHandler(key, keycode, false);
                switch (key) {
                    case '1':
                        gameMode = GameMode.START;
                }
                break;
            case EDIT:
                switch (key) {
                    case '1':
                        gameMode = GameMode.START;
                }
                break;
            case RELOAD:
                //loads levels
                break;
        }
    }

    public void keyPressed(char key, int keycode) {
        switch (gameMode) {
            case START:
                //Shows welcome screen
                break;
            case PLAY:
                playerInput.keyHandler(key, keycode, true);
                break;
            case EDIT:
                //level editor
                break;
            case RELOAD:
                //loads levels
                break;
        }
    }

    private void welcome_screen() {
        parent.pushMatrix();
        parent.translate(parent.width / 4, parent.height / 4);
        parent.rectMode(PApplet.CORNERS);
        parent.fill(0, 255, 0);
        parent.textSize(32);
        parent.text("DIGGERSBY THO", 0, 0);
        parent.textSize(28);
        parent.text("Press any key to play", 0, 60);
        parent.text("Press 1 key to edit", 0, 120);
        parent.popMatrix();
    }
}
