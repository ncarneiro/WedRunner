package org.ncarneiro.wedrunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Nikolas on 3/31/2016.
 */
public class Player {

    private boolean rising;

    //player square center position
    private int x;
    private int y;

    //keep player on screen
    private int minY;
    private int maxY;

    //control player speed and elevation
    private final int MIN_ELEVATION = 1;
    private final int MAX_ELEVATION = 20;
    private final int MAX_SPEED = 15;
    private int speed;
    private int elevation;

    //gravity over player
    private final int GRAVITY = -12;

    //player squared collision box
    private Rect collisionBox;

    //player sprite
    private Bitmap bitmap;

    public Player(Context context, int screenX, int screenY) {
        x = 50;
        y = 10;
        speed = 1;
        elevation = 1;
        rising = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.groom);
        minY = 1;
        //refactor: do not allow collision box to be defined by image
        maxY = screenY - bitmap.getHeight()-1;
        collisionBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {
        //adhust speed
        if (speed<MAX_SPEED)
            speed++;
        //adjust height
        // Move the player up or down
        if (rising) {
            elevation += 2;
        } else {
            elevation -= 5;
        }
        if (elevation<MIN_ELEVATION)
            elevation = MIN_ELEVATION;
        else if (elevation>MAX_ELEVATION)
            elevation = MAX_ELEVATION;
        /*
        int auxY = y - (elevation + GRAVITY);
        if (auxY<minY)
            y = minY;
        else if (auxY>maxY)
            y = maxY;
        else
            y = auxY;
        */
        //
        y -= elevation + GRAVITY;
        // But don't let ship stray off screen
        if (y < minY) {
            y = minY;
        } else if (y > maxY) {
            y = maxY;
        }
        //
        //Log.e("Player", "UPDATE: " + getY());

        //update collision box
        // Refresh hit box location
        collisionBox.left = x;
        collisionBox.top = y;
        collisionBox.right = x + bitmap.getWidth();
        collisionBox.bottom = y + bitmap.getHeight();
    }

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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean isRising() {
        return rising;
    }

    public void setRising(boolean rising) {
        this.rising = rising;
    }
}