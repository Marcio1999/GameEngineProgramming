package core.game_engine.physics;

import core.game_engine.Component;
import core.game_engine.GameManager;
import core.game_engine.GameObject;
import core.game_engine.Sprite;

import java.util.ArrayList;

public class BoxCollider2D extends Component {
    // bounds rectangle
    private Rectangle bounds;
    private boolean hasCollided = false;
    private SIDES hitSideH = SIDES.NONE;
    private SIDES hitSideV = SIDES.NONE;
    public boolean mouse_over = false;

    public SIDES getHitSideV() {
        return hitSideV;
    }

    public SIDES getHitSideH() {
        return hitSideH;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public ArrayList<BoxCollider2D> otherColliders = new ArrayList<>();

    public BoxCollider2D(Sprite g, float w, float h) {
        super(g);
        this.bounds = new Rectangle(gameObject.position.x, gameObject.position.y, w, h);
    }

    @Override
    protected void update() {
        this.bounds.updateBounds(gameObject.position.x, gameObject.position.y);
        int mX = this.gameObject.parent.mouseX - (int)GameManager.CAMERA_POSITION().x;
        int mY = this.gameObject.parent.mouseY - (int)GameManager.CAMERA_POSITION().y;
        this.mouse_over = this.bounds.pointHit(mX, mY);

    }

    public void check_collisions(BoxCollider2D other) {
        hasCollided = bounds.isOverLapping(other.bounds);
        if (hasCollided) {
            this.otherColliders.add(other);
        }
    }

    public void findCollisionSide(BoxCollider2D otherBox2D) {
        hitSideV = SIDES.NONE;
        boolean isTouchingAbove = this.bounds.getIsTouchingAbove(otherBox2D.getBounds());
        boolean isTouchingBelow = false;
        if (!isTouchingAbove) {
            isTouchingBelow = this.bounds.getIsTouchingBelow(otherBox2D.getBounds());
        }

        if (isTouchingAbove) {
            hitSideV = SIDES.BOTTOM;
        } else if (isTouchingBelow) {
            hitSideV = SIDES.TOP;
        }
        hitSideH = SIDES.NONE;
// check proximity to a plat below
        float distanceAbove = Math.abs(otherBox2D.getBounds().getTopRight().getY() - this.bounds.getBottomLeft().getY());
        if(distanceAbove <1f) {
            return;
        }
        boolean isTouchingRight = this.bounds.getIsTouchingRight(otherBox2D.getBounds());
        boolean isTouchingLeft = false;
        if (!isTouchingRight) {
            isTouchingLeft = this.bounds.getIsTouchingLeft(otherBox2D.getBounds());
        }
        if (isTouchingLeft) {
            hitSideH = SIDES.LEFT;
        } else if (isTouchingRight) {
            hitSideH = SIDES.RIGHT;
        }
    }
}
