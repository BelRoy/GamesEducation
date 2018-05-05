package net.brigs.gameseducation.games.memory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import net.brigs.gameseducation.R;

import java.util.Random;

public class Grid4x4_Activity extends AppCompatActivity
        implements View.OnClickListener {

    public MemoryButtonB selected4By4Button1,selected4By4Button2;

    public int numberOf4By4Drawables;
    public MemoryButtonB[] button4By4Collection;

    //buttonDrawableLocations-An int array that holds the unique combinations of cards we have 16/2 = 8 combinations
    //buttonDrawables-An int array that holds the id's of the drawable files we have in the res/drawable
    public int[] buttonDrawableLocations, buttonDrawables;

    //Used for creating the delay when cards are rotated
    public boolean isProcessing = false;

    //added variables
    public final int  FINAL_MATCHES = 8;
    public int correctMatch;
    public int incorrectMatch;
    public long startTime;
    public String gameOverMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid4x4_);

        GridLayout gridLayout_4x4 = (GridLayout) findViewById(R.id.activity_grid4x4_);

        //#of Columns & Rows
        int totalColumns = gridLayout_4x4.getColumnCount();
        int totalRows = gridLayout_4x4.getRowCount();

        numberOf4By4Drawables = (totalColumns * totalRows);
        button4By4Collection = new MemoryButtonB[numberOf4By4Drawables];

        //only stores the unique cards
        buttonDrawables = new int[(numberOf4By4Drawables / 2)];

        buttonDrawables[0] = R.drawable.front_1;
        buttonDrawables[1] = R.drawable.front_2;
        buttonDrawables[2] = R.drawable.front_3;
        buttonDrawables[3] = R.drawable.front_4;
        buttonDrawables[4] = R.drawable.front_5;
        buttonDrawables[5] = R.drawable.front_6;
        buttonDrawables[6] = R.drawable.front_7;
        buttonDrawables[7] = R.drawable.front_8;

        buttonDrawableLocations = new int[numberOf4By4Drawables];
        //Method to populate the cards randomly on the grid layout
        randomize4By4ButtonGraphics();

        //code for action bar height
        int actionBarHeight = 0;
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        TypedValue typedValue = new TypedValue();

        if (getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data,getResources().getDisplayMetrics());
        }

        if ( resourceId > 0)
        {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int optionBarsHeight = actionBarHeight + statusBarHeight;

        //Cascaded for loop to populate the rows and the columns on the grid
        for (int row = 0; row < totalRows; row++)
        {
            for (int column = 0; column < totalColumns; column++)
            {
                MemoryButtonB tempButton = new MemoryButtonB(this, row, column, buttonDrawables[buttonDrawableLocations[row * totalColumns + column]], optionBarsHeight);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                //Storing the references
                button4By4Collection[row * totalColumns + column] = tempButton;
                gridLayout_4x4.addView(tempButton);
            }
        }

    }

    //Menu Bar

    //Inflate the Menu widgets
    //Widgets help the user reset the game or go back to the main menu and pick a new grid
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_4x4,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Logic for the home and the reset btn
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.it_home4x4: goHome();

            case R.id.it_refresh4x4: reset4x4Grid();

            default: return super.onOptionsItemSelected(item);
        }
    }

    public void goHome()
    {
        Intent mainMenu= new Intent(Grid4x4_Activity.this, MemoryActivity.class);
        startActivity(mainMenu);
    }

    //Launch a new Grid2x2_Activity since it contains the logic to randomize and rotate the cards
    public void reset4x4Grid()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }

    public void randomize4By4ButtonGraphics()
    {
        Random rnd = new Random();
        for (int i = 0; i < numberOf4By4Drawables; i++) {
            buttonDrawableLocations[i] = (i % (numberOf4By4Drawables / 2));
        }

        for (int i = 0; i < numberOf4By4Drawables; i++) {
            int temp = buttonDrawableLocations[i];
            int swapIndex = rnd.nextInt(16);
            buttonDrawableLocations[i] = buttonDrawableLocations[swapIndex];
            buttonDrawableLocations[swapIndex] = temp;
        }
    }

    public void matchToast() {
        Context match = getApplicationContext();
        String matchText = "MATCHED!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(match, matchText, duration);
        toast.show();
    }

    public void misMatchToast() {
        Context mismatch = getApplicationContext();
        String mismatchText = "TRY-AGAIN!";
        int duration = Toast.LENGTH_SHORT;

        Toast mismatchToast = Toast.makeText(mismatch, mismatchText, duration);
        mismatchToast.show();
    }


    @Override
    public void onClick(View view) {
        MemoryButtonB button = (MemoryButtonB) view;
        Handler handler = new Handler();

        if (isProcessing) {
            //doNothing when there is a process running - this prevents crashes
            return;
        }

        if (button.isMatched) {
            //if Button has a match then do nothing
            return;
        }

        if (selected4By4Button1 == null) {
            selected4By4Button1 = button;
            selected4By4Button1.rotate();
            return;
        }

        if (selected4By4Button1.getId() == button.getId()) {
            return;
        }
        if (correctMatch == 0 && incorrectMatch == 0){
            startTime = System.currentTimeMillis();
        }
        if (selected4By4Button1.getFrontDrawableValue() == button.getFrontDrawableValue()) {
            //User matches two cards
            button.rotate();
            button.setMatched(true);
            selected4By4Button1.setEnabled(false);
            button.setEnabled(false);
            selected4By4Button1 = null;
            matchToast();
            //n.matchToast();
            //added code for game completion
            correctMatch++;

            if (correctMatch == FINAL_MATCHES){
                int matchScore = correctMatch + incorrectMatch;
                long stopTime = System.currentTimeMillis();
                long elapsedTimeMilliSeconds = (stopTime - startTime) / 10;
                long elapsedTimeSeconds = elapsedTimeMilliSeconds / 100;
                long elapsedTimeMinutes = elapsedTimeSeconds/ 60;
                elapsedTimeSeconds = elapsedTimeSeconds % 60;
                elapsedTimeMilliSeconds = elapsedTimeMilliSeconds % 100;
                if (elapsedTimeMinutes > 0 && elapsedTimeSeconds == 1){
                    gameOverMessage = ("Congratulations, your time was " + elapsedTimeMinutes + " minutes and " + elapsedTimeSeconds + "." + elapsedTimeMilliSeconds + " second and you got " + correctMatch + " out of " + matchScore + " correct, Go back to main menu?");
                }
                else if (elapsedTimeMinutes > 0){
                    gameOverMessage = ("Congratulations, your time was " + elapsedTimeMinutes + " minutes and " + elapsedTimeSeconds + "." + elapsedTimeMilliSeconds + " seconds and you got " + correctMatch + " out of " + matchScore + " correct, Go back to main menu?");
                }
                else if (elapsedTimeSeconds == 1){
                    gameOverMessage = ("Congratulations, your time was " + elapsedTimeSeconds + "." + elapsedTimeMilliSeconds + " second and you got " + correctMatch +  " out of " + matchScore + " correct, Go back to main menu?");
                }
                else {
                    gameOverMessage = ("Congratulations, your time was " + elapsedTimeSeconds +  "." + elapsedTimeMilliSeconds +  " seconds and you got " + correctMatch + " out of " + matchScore + " correct, Go back to main menu?");
                }

                new AlertDialog.Builder(this)
                        .setTitle("Result")
                        .setMessage(gameOverMessage)
                        .setNegativeButton("Play Again", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reset4x4Grid();;
                            }
                        })
                        .setPositiveButton("Go to Main Menu", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent mainMenu = new Intent(Grid4x4_Activity.this, MemoryActivity.class);
                                startActivity(mainMenu);
                            }
                        }).create().show();

            }

            return;
        }
        else {
            //User fails to match two cards
            selected4By4Button2 = button;
            selected4By4Button2.rotate();
            isProcessing = true;
            incorrectMatch++;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selected4By4Button2.rotate();
                    selected4By4Button1.rotate();
                    selected4By4Button1 = null;
                    selected4By4Button2 = null;
                    isProcessing = false;
                }
            }, 400);
            misMatchToast();
            //n.misMatchToast();
        }
    }
}

class MemoryButtonB extends Button {

    //variables to reference the row and column, and the id of the faced down card
    public int row_4By4,column_4By4, actionHeight;

    //id of the front drawable value
    public int front4By4DrawableValue;

    //Reference to the drawable objects()
    public Drawable front4By4Card,backOfCard;

    //variables to define the Actions a user can encounter
    public boolean isRotated = false;
    public boolean isMatched = false;

    //Default Constructor
    public MemoryButtonB(Context context, int Row, int Column, int FrontDrawableValue, int abHeight)
    {
        //Parent Class Constructor
        super(context);

        //This= MemoryButton
        this.row_4By4 = Row;
        this.column_4By4 = Column;
        this.front4By4DrawableValue = FrontDrawableValue;
        this.actionHeight = abHeight;

        //initialize the front & back of the card
        front4By4Card = AppCompatDrawableManager.get().getDrawable(context, front4By4DrawableValue);
        backOfCard = AppCompatDrawableManager.get().getDrawable(context, R.drawable.back);
        setBackground(backOfCard);

        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels / 4;
        int screenHeight = (getContext().getResources().getDisplayMetrics().heightPixels - this.actionHeight) / 4;

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(Row), GridLayout.spec(Column));
        params.width = screenWidth;
        params.height = screenHeight;

        setLayoutParams(params);
    }

    //setters and getters for the primitives

    //get: frontDrawableValue (ID)
    public int getFrontDrawableValue() {
        return front4By4DrawableValue;
    }

    //get: isMatched()
    public boolean isMatched() {
        return isMatched;
    }

    //set: isMatched()
    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    //Logic: Rotate
    public void rotate()
    {
        if (isRotated)
        {
            //doNothing if the user did not rotate any card and keep the card faced down.
            setBackground(backOfCard);
            isRotated = false;
        }
        else
        {
            //if the userRotated the card then set the isRotated to true for a duration of Time
            //Reveal the card
            setBackground(front4By4Card);
            isRotated = true;
        }

        if (isMatched)
        {
            //doNothing since the user did not match.
            //In this case the user revealed two cards that are an incorrect match.
            //We rotate the cards to not show faced down image
            return;
        }
    }
}

