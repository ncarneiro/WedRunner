package org.ncarneiro.wedrunner;

import android.graphics.Rect;

/**
 * Created by Nikolas on 3/31/2016.
 */
public class Player {

    //player square center position
    private int x;
    private int y;

    //keep player on screen
    private int minY;
    private int maxY;

    //control player speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 10;
    private int speed;

    //gravity over player
    private final int GRAVITY = -12;

    //player squared collision box
    private Rect collisionBox;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGRAVITY() {
        return GRAVITY;
    }

    public Rect getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rect collisionBox) {
        this.collisionBox = collisionBox;
    }

}