package org.ncarneiro.wedrunner;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

/**
 * Created by Nikolas on 3/31/2016.
 */
public class GameActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get display to screen info
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);

        //instantiate GameView
        gameView = new GameView(this, displaySize.x, displaySize.y);

        //set the view to the created gameview
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Hide navigation bar. Show system UI on swipe from screen border. Hide in a second after.*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

}