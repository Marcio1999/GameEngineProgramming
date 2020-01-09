package core.game_engine.physics;

import core.game_engine.Component;
import core.game_engine.GameManager;
import core.game_engine.GameObject;
import core.game_engine.LayerTypes;
import processing.core.PVector;

public class PhysicsComponent extends Component {
    private PVector velocity = new PVector(0, 0, 0);
    public float maxSpeed = 5f;
    private float friction = 0.9f;
    private float spacer = 0.3f;
    private float gravity = 1.1f;
    // box collider
    private BoxCollider2D boxCollider2D;
    private boolean isGrounded = false;
    private PVector dir = new PVector(1, 0, 0);

    public PhysicsComponent(GameObject g, BoxCollider2D b) {
        super(g);
        this.boxCollider2D = b;
    }

    public boolean isGrounded() {
        return isGrounded;
    }

    @Override
    protected void update() {
        if (this.velocity.mag() > this.maxSpeed) {
            float tmpY = this.velocity.y;
            this.velocity.setMag(this.maxSpeed);
            this.velocity.y = tmpY;
        }
        this.velocity.y += gravity;
        if (this.boxCollider2D.otherColliders.size() > 0) {
            for (BoxCollider2D b : this.boxCollider2D.otherColliders) {
                if (b.gameObject.layerType == LayerTypes.INTERACTABLE) {
                    b.gameObject.setActive(false);
                } else {
                    setCollisionSide(b);
                }
            }
            this.boxCollider2D.otherColliders.clear();
        }
        this.velocity.mult(friction);
        this.gameObject.position = this.gameObject.next_position.copy();
        this.gameObject.next_position.add(this.velocity);
        GameManager.CAMERA_FOLLOW(this.gameObject.position);
    }

    public void setVelocity(float x, float y) {
        this.velocity.x += x;
        this.velocity.y += y;
        if (this.velocity.y < 0) {
            this.isGrounded = false;
        }

    }
    public PVector getDir(){
        dir.x = Math.signum(this.velocity.x);
        dir.y = Math.signum(this.velocity.y);
        return this.dir;
    }

    private void setCollisionSide(BoxCollider2D otherBox2D) {
        this.boxCollider2D.findCollisionSide(otherBox2D);
        Point otherTopRight = otherBox2D.getBounds().getTopRight();
        Point otherBottonLeft = otherBox2D.getBounds().getBottomLeft();

        switch (this.boxCollider2D.getHitSideV()) {
            case TOP:
                if (velocity.y < 0) {

                    this.gameObject.next_position.y = otherBottonLeft.getY() + this.boxCollider2D.getBounds().getHeight() / 2f + spacer;
                    velocity.y = gravity;
                }
                break;
            case BOTTOM:
                if (velocity.y >= 0) {
                    this.gameObject.next_position.y = otherTopRight.getY() - this.boxCollider2D.getBounds().getHeight() / 2f + spacer;
                    velocity.y = 0;
                    this.isGrounded = true;
                }
                break;
            case NONE:
                this.isGrounded = false;
                break;

        }
        switch (this.boxCollider2D.getHitSideH()){

            case LEFT:
                if(velocity.x > 0){

                    velocity.x = 0;
                    this.gameObject.next_position.x = otherBottonLeft.getX() - this.boxCollider2D.getBounds().getWidth() / 2f - spacer;

                }

                break;
            case RIGHT:
                if(velocity.x < 0){
                    this.gameObject.next_position.x = otherTopRight.getX() + this.boxCollider2D.getBounds().getWidth() / 2f + spacer;
                    velocity.x = 0;
                }

                break;

        }

    }
}
