package core.game;

import core.game_engine.GameManager;
import core.game_engine.LayerTypes;
import core.game_engine.Sprite;
import core.game_engine.physics.BoxCollider2D;
import processing.core.PApplet;
import processing.core.PVector;

public class Projectile extends Sprite {
    private PVector direction;
    private float speed = 8f;
    private int lifespan = 20;
    private int life = 0;
    public Projectile(PApplet p, float x, float y, float w, float h) {
        super(p, x, y, w, h);
        layerType = LayerTypes.MOVING;
        boxCollider2D = new BoxCollider2D(this, w, h);
    }
    public void Shoot(PVector d){
        this.direction = d;
    }
    @Override
    public void update(){
        life ++;
        if(life > lifespan){
            //destroy
            return;
        }
        super.update();
        this.position.add(direction.setMag(speed));
        this.checkCollision();
        this.render();
    }
    private void checkCollision(){
        if(this.boxCollider2D.otherBoxColliders.size() > 0){
            for(BoxCollider2D b : this.boxCollider2D.otherBoxColliders){

                if(b.gameObject.getLayerType() == LayerTypes.STATIC){
                    b.gameObject.setActive(false);
                }else{
                    GameManager.Destroy(this);
                }
            }
            this.boxCollider2D.otherBoxColliders.clear();
        }
    }
    private void render(){
        parent.pushMatrix();
        parent.rectMode(PApplet.CENTER);
        parent.translate(this.position.x, this.position.y);
        parent.fill(255,90,90,240);
        this.parent.ellipse(0,0, this.size.x, this.size.y);
        parent.popMatrix();
    }
}
