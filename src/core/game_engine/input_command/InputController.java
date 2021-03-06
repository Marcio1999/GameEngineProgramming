package core.game_engine.input_command;

import processing.core.PApplet;

public class InputController {
    public InputHandler inputHandler;
    MoveLeftCommand moveLeftCommand;
    MoveRightCommand moveRightCommand;
    MoveUpCommand moveUpCommand;
    MoveDownCommand moveDownCommand;
    FireCommand fireCommand;
    boolean left, right, up, down, fire;

    public InputController(MoveAble _actor) {
        moveLeftCommand = new MoveLeftCommand(_actor);
        moveRightCommand = new MoveRightCommand(_actor);
        moveUpCommand = new MoveUpCommand(_actor);
        moveDownCommand = new MoveDownCommand(_actor);
        fireCommand = new FireCommand(_actor);
        inputHandler = new InputHandler(moveLeftCommand, moveRightCommand, moveUpCommand, moveDownCommand, fireCommand);

    }

    public void keyHandler(char key, int keycode, boolean active) {
        if (key == 'a' || keycode == PApplet.LEFT) {
            left = active;
        } else if (key == 'd' || keycode == PApplet.RIGHT) {
            right = active;
        } else if (key == 'w' || keycode == PApplet.UP) {
            left = active;
        } else if (key == 's' || keycode == PApplet.DOWN) {
            right = active;
        } else if (key == ' ' || keycode == PApplet.SHIFT) {
            fire = active;
        } else {
            left = false;
            right = false;
            up = false;
            down = false;
        }
    }

    public void checkInput() {
        if(left) {
            inputHandler.moveLeft();
        } else if(right) {
            inputHandler.moveRight();
        }
        if(up) {
            inputHandler.moveUp();
        } else if(down) {
            inputHandler.moveDown();
        }
        if(fire) {
            fire = false;
            inputHandler.fire();
        }
    }
}
