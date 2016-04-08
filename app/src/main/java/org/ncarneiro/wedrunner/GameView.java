package org.ncarneiro.wedrunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nikolas on 3/31/2016.
 */
public class GameView extends SurfaceView implements Runnable {

    //
    public ArrayList<SpaceDust> dustList = new ArrayList<SpaceDust>();
    //

    private volatile boolean playing;
    private boolean gameOver;
    private Thread gameThread;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder holder;

    private int screenX;
    private int screenY;

    private Bitmap street1;
    private Bitmap street2;
    private Bitmap street3;
    private boolean isStreet2Draw = true;
    private final int streetRefreshGap = 10;
    private int streetRefreshControl;

    Player player;
    ArrayList<Enemy> enemies;

    public GameView(Context context, int x, int y) {
        super(context);
        //
        enemies = new ArrayList<>();
        Bitmap street = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rua2);
        street2 = Bitmap.createScaledBitmap(street, x, y/10, false);
        street = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rua3);
        street3 = Bitmap.createScaledBitmap(street, x, y / 10, false);
        street1 = street2;
        streetRefreshControl = streetRefreshGap;
        //
        screenX = x;
        screenY = y;
        holder = getHolder();
        paint = new Paint();
        startGame();
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    /* Control framerate */
    private void control() {
        try {
            gameThread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        //
        /* Initialise space objects */
        int numSpecs = 40;
        for (int i = 0; i < numSpecs; i++) {
            SpaceDust spec = new SpaceDust(screenX, screenY);
            dustList.add(spec);
        }
        //
        gameOver = false;
        player = new Player(getContext(), screenX, screenY);
    }

    /* Clean up our thread if the game is interrupted or the player quits */
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* Make a new thread and start it execution moves to our R */
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update() {
        //
        for (SpaceDust sd : dustList) {
            sd.update(player.getSpeed());
        }
        //

        //detect collision to the player
        boolean playerHit = false;
        for (Enemy e : enemies) {
            if (Rect.intersects(player.getCollisionBox(), e.getCollisionBox())) {
                playerHit = true;
            }
        }
        if (playerHit) {
            //TODO: handle player hit
        }
        player.update();

        if (gameOver) {
            //TODO: handle game over
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            //First we lock the area of memory we will be drawing to
            canvas = holder.lockCanvas();

            // Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // White specs of dust
            paint.setColor(Color.argb(255, 255, 255, 255));
            for (SpaceDust sd : dustList) {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }

            //draw street
            if (streetRefreshControl<streetRefreshGap) {
                streetRefreshControl++;
            } else {
                if (isStreet2Draw) {
                    isStreet2Draw = false;
                    street1 = street3;
                } else {
                    isStreet2Draw = true;
                    street1 = street2;
                }
                streetRefreshControl = 0;
            }
            canvas.drawBitmap(street1, 0, screenY-street1.getHeight(), paint);

            // Draw the player's ship
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            //TODO: draw hud info
            // Unlock and draw the scene
            holder.unlockCanvasAndPost(canvas);
        }
    }

    // SurfaceView allows us to handle the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.e("GameView","OnTouchEvent: "+event.toString());
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                player.setRising(true);
                Log.e("GameView", "RISING: " + player.getY());
                // If we are currently on the pause screen, start a new game
                if(gameOver){
                    startGame();
                }
                break;
            case MotionEvent.ACTION_UP:
                player.setRising(false);
                Log.e("GameView", "NOT RISING " + player.getY());
                break;
        }
        return true;
    }

}
