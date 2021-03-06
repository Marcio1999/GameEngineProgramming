package core.game_engine.physics;

public class Rectangle {
    private float x, y, width, height;
    private Point topRight = new Point( 1,1);
    private Point bottomLeft = new Point( -1, -1);

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Point getTopRight() {
        return topRight;
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    public Rectangle(float _x, float _y, float w, float h){
        width = w;
        height = h;
        this.updateBounds(_x,_y);
    }

    public void updateBounds(float _x, float _y) {
        x = _x;
        y = _y;
        bottomLeft.setX(x - width / 2f);
        bottomLeft.setY(y + height / 2f);
        topRight.setX(x + width / 2f);
        topRight.setY(y - height / 2f);
    }
    public boolean pointHit(int x, int y){
        if(x < bottomLeft.getX() || x > topRight.getX()){
            return  false;
        }
        if(y > bottomLeft.getY() || y < topRight.getY()){
            return false;
        }
        return true;
    }
    public boolean isOverLapping(Rectangle other){

        if(topRight.getY() > other.bottomLeft.getY() || bottomLeft.getY() < other.topRight.getY()){
            return false;
        }

        if(topRight.getX() < other.bottomLeft.getX() || bottomLeft.getX() > other.topRight.getX()){
            return false;
        }

        return true;
    }
    public boolean getIsTouchingAbove(Rectangle other){
        return other.topRight.getY() <= this.bottomLeft.getY() && other.topRight.getY() > this.topRight.getY();
    }
    public boolean getIsTouchingBelow(Rectangle other){
        return  other.bottomLeft.getY() >= this.topRight.getY() && other.bottomLeft.getY() < this.bottomLeft.getY();
    }
    public boolean getIsTouchingRight(Rectangle other) {
        return other.topRight.getX() >= this.bottomLeft.getX() && other.topRight.getX() < this.topRight.getX();
    }
    public boolean getIsTouchingLeft(Rectangle other){
        return  other.bottomLeft.getX() <= this.topRight.getX() && other.bottomLeft.getX() > this.bottomLeft.getX();
    }

}
