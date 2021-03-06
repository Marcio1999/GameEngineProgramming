package core.game_engine.input_command;

public class MoveLeftCommand implements Command {
    private MoveAble actor;
    public MoveLeftCommand(MoveAble _actor){
        actor = _actor;
    }
    @Override
    public void execute() {
        actor.moveLeft();
    }
}
