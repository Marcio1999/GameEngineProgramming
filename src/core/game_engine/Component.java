package core.game_engine;

import core.game_engine.GameObject;

public abstract class Component {
    public GameObject gameObject;
    public Component(GameObject g){
        this.gameObject = g;
        this.gameObject.addComponentList(this);
    }
    protected abstract void update();

}
