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
    private boolean falling;
    private int maxJumps;
    private int jumpsPerformed;

    //player square center position
    private int x;
    private int y;

    //keep player on screen
    private int minY;
    private int maxY;

    //control player speed and elevation
    private final int MAX_SPEED = 10;
    private int min_elevation;
    private int max_elevation;
    private int elevation_multiplier;
    private int jump_height;
    private int speed;
    private int elevation;

    //gravity over player
    private final int GRAVITY = -12;

    //player squared collision box
    private Rect collisionBox;

    //player sprite
    private Bitmap bitmap;

    public Player(Context context, int screenX, int screenY) {
        int factor = 12;
        int bitmapSide = ((screenX/100)*factor);

        elevation_multiplier = 1;
        jump_height = 250;
        min_elevation = 1;
        max_elevation = 20;

        maxJumps = 2;
        jumpsPerformed = 0;
        speed = 1;
        elevation = 1;
        rising = false;
        falling = true;
        Bitmap bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.groom);
        bitmap = Bitmap.createScaledBitmap(bit, bitmapSide, bitmapSide, false);
        x = 50;
        y = screenY-(bitmap.getHeight()/2-1);
        minY = 1;
        //refactor: do not allow collision box to be defined by image
        maxY = screenY - bitmap.getHeight()-2;
        collisionBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {
        //adhust speed
        if (speed<MAX_SPEED)
            speed++;
        //adjust height
        // Move the player up or down
        if (rising) {
            elevation += 2* elevation_multiplier;//2
        } else if (falling) {
            elevation -= 5* elevation_multiplier;//5
        }
        if (elevation< min_elevation)
            elevation = min_elevation;
        else if (elevation> max_elevation)
            elevation = max_elevation;

        if (rising || falling) {
            y -= elevation + GRAVITY;
        }

        // But don't let ship stray off screen
        if (y < minY) {
            y = minY;
        } else if (y > maxY) {
            y = maxY;
            if (!rising) {
                hitGround();
            }
        }
        //

        //JUMP
        int jp;
        if (jumpsPerformed>0) jp = jumpsPerformed;
        else jp = 1;
        int aux = maxY - jump_height*jp;
        if (y < aux) {
            y = aux;
            stopJump();
        }

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

    public boolean startJump() {
        boolean b = false;
        Log.e("PLAYER", "START JUMP: " + getY()+"\t"+jumpsPerformed);
        if (jumpsPerformed<maxJumps) {
            jumpsPerformed++;
            rising = true;
            falling = false;
            b = true;
        }
        Log.e("PLAYER", "START JUMP: " + getY()+"\t"+jumpsPerformed);
        return b;
    }

    public void stopJump() {
        rising = false;
        falling = true;
        Log.e("PLAYER", "STOP JUMP: " + getY()+"\t"+jumpsPerformed);
    }

    private void hitGround() {
        jumpsPerformed = 0;
        rising = false;
        falling = false;
        y = maxY-1;
        Log.e("PLAYER", "GROUNDED: " + getY());
    }

}