package org.ncarneiro.wedrunner;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by Nikolas on 3/31/2016.
 */
public class Enemy {

    //enemy square center position
    private int x;
    private int y;
    //enemy squared collision box
    private Rect collisionBox;
    //enemy sprite
    private Bitmap bitmap;

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

    public Rect getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rect collisionBox) {
        this.collisionBox = collisionBox;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}