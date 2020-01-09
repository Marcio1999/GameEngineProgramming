package core.game;

import core.game_engine.GameManager;
import core.game_engine.GameObject;
import core.game_engine.LayerTypes;
import core.game_engine.Sprite;
import core.game_engine.input_command.MoveAble;
import core.game_engine.physics.BoxCollider2D;
import core.game_engine.physics.PhysicsComponent;
import processing.core.PApplet;
import processing.core.PVector;

public class Player extends Sprite implements MoveAble {
    public PVector size;
    private float acceleration = 2f;
    private float jump = 11f;
    private PhysicsComponent physicsComponent;

    public Player(PApplet p, int x, int y, int w, int h) {
        super(p,x,y,w,h);
        this.parent = p;
        this.type = "Player";
        this.size = new PVector(w, h, 0);
       // this.position = new PVector(x, y, 0);
        layerType = LayerTypes.MOVING;
        boxCollider2D = new BoxCollider2D(this, w, h);
        physicsComponent = new PhysicsComponent(this, boxCollider2D);
    }

    @Override
    public void update() {
        super.update();
        parent.pushMatrix();
        parent.rectMode(PApplet.CENTER);
        parent.translate(this.position.x, this.position.y);
        parent.fill(255);
        this.parent.rect(0, 0, this.size.x, this.size.y);
        parent.popMatrix();
        }

    @Override
    public void moveLeft() {
        this.physicsComponent.setVelocity(-acceleration, 0);
    }

    @Override
    public void moveRight() {
        this.physicsComponent.setVelocity(acceleration, 0);

    }

    @Override
    public void moveUp() {
        if (this.physicsComponent.isGrounded()) {
            this.physicsComponent.setVelocity(0, -jump);
        }
    }

    @Override
    public void moveDown() {
        this.physicsComponent.setVelocity(0, acceleration);
    }

    @Override
    public void fire() {
        //bullets
        System.out.println("Fire");
        PVector bulletPos = this.position.copy();
        bulletPos.x = this.position.x + this.size.x * 0.65f * physicsComponent.getDir().x;
        bulletPos.y = this.position.y + this.size.y * 0.65f * physicsComponent.getDir().y;

        Projectile bullet = new Projectile(parent, bulletPos.x, bulletPos.y, 10, 10);
        GameManager.Instantiate(bullet);
        bullet.Shoot(physicsComponent.getDir());

    }
}