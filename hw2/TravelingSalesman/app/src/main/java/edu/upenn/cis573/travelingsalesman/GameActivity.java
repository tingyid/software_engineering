package edu.upenn.cis573.travelingsalesman;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends ActionBarActivity {

    /**
     * Set the content view and pass the number of
     * locations to the view, then initialize
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
        GameView gameView = (GameView) findViewById(R.id.gameView);
        int numLocations = getIntent().getIntExtra(MainActivity.EXTRA_NUM_LOCATIONS, 4);
        gameView.init(numLocations);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    /**
    This method is called when the user chooses something in the menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_clear) {
            GameView gv = (GameView) findViewById(R.id.gameView);
            gv.clearView();
            return true;
        }
        else if (id == R.id.menu_quit) {
            finish();
            return true;
        } else if (id == R.id.menu_undo) {
            GameView gv = (GameView) findViewById(R.id.gameView);
            gv.undoLast();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
