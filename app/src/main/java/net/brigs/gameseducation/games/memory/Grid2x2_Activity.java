package net.brigs.gameseducation.games.memory;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class Grid2x2_Activity extends AppCompatActivity
        implements View.OnClickListener {

    public MemoryButton selected2By2Button1,selectedButton2By2Button2;
    public int numberOf2By2Drawables;
    public MemoryButton[] button2By2Collection;


    public int[] buttonGraphicLocations,buttonGraphics;

    public boolean isProcessing = false;


    public final int  FINAL_MATCHES = 2;
    public int correctMatch,incorrectMatch;
    public long startTime;
    public String gameOverMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid2x2_);

        GridLayout gridLayout = findViewById(R.id.activity_grid2x2_);
        int totalColumns = gridLayout.getColumnCount();
        int totalRows = gridLayout.getRowCount();

        numberOf2By2Drawables = (totalColumns * totalRows);
        button2By2Collection = new MemoryButton[numberOf2By2Drawables];

        buttonGraphics = new int[(numberOf2By2Drawables / 2)];
        buttonGraphics[0] = R.drawable.front_18;
        buttonGraphics[1] = R.drawable.front_21;

        buttonGraphicLocations = new int[numberOf2By2Drawables];
        randomizeButtonGraphics();

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

        int optionBarsHeight = (actionBarHeight + statusBarHeight);

        //Cascaded for loop to populate the rows and the columns on the grid
        for (int row = 0; row < totalRows; row++)
        {
            for (int column = 0; column < totalColumns; column++)
            {
                MemoryButton tempButton = new MemoryButton(this, row, column, buttonGraphics[buttonGraphicLocations[row * totalColumns + column]], optionBarsHeight);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                button2By2Collection[row * totalColumns + column] = tempButton; //Storing the references
                gridLayout.addView(tempButton);
            }
        }
    }
    //Menu Bar

    //Inflate the Menu widgets
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_2x2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Logic for the home and the reset btn
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.it_home2x2: goHome();

            case R.id.it_refresh2x2: reset2x2Grd();

            default: return super.onOptionsItemSelected(item);
        }
    }

    public void goHome()
    {
        Intent mainMenu= new Intent(Grid2x2_Activity.this, MemoryActivity.class);
        startActivity(mainMenu);
    }

    //Launch a new Grid2x2_Activity since it contains the logic to randomize and rotate the cards
    @SuppressLint("ObsoleteSdkInt")
    public void reset2x2Grd()
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

    public void randomizeButtonGraphics() {
        Random rnd = new Random();
        for (int i = 0; i < numberOf2By2Drawables; i++) {
            buttonGraphicLocations[i] = (i % (numberOf2By2Drawables / 2));
        }

        for (int i = 0; i < numberOf2By2Drawables; i++) {
            int temp = buttonGraphicLocations[i];
            int swapIndex = rnd.nextInt(4);
            buttonGraphicLocations[i] = buttonGraphicLocations[swapIndex];
            buttonGraphicLocations[swapIndex] = temp;
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
        MemoryButton button = (MemoryButton) view;
        Handler handler = new Handler();

        if (isProcessing) {
            //doNothing when there is a process running - this prevents crashes
            return;
        }

        if (button.isMatched) {
            //if Button has a match then do nothing
            return;
        }

        if (selected2By2Button1 == null) {
            selected2By2Button1 = button;
            selected2By2Button1.rotate();
            return;
        }

        if (selected2By2Button1.getId() == button.getId()) {
            return;
        }
        if (correctMatch == 0 && incorrectMatch == 0){
            startTime = System.currentTimeMillis();
        }
        if (selected2By2Button1.getFrontDrawableValue() == button.getFrontDrawableValue()) {
            //User matches two cards
            button.rotate();
            button.setMatched(true);
            selected2By2Button1.setEnabled(false);
            button.setEnabled(false);
            selected2By2Button1 = null;
            matchToast();
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
                                reset2x2Grd();;
                            }
                        })
                        .setPositiveButton("Go to Main Menu", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent mainMenu = new Intent(Grid2x2_Activity.this, MemoryActivity.class);
                                startActivity(mainMenu);
                            }
                        }).create().show();

            }


            return;
        }
        else {
            //User fails to match two cards
            selectedButton2By2Button2 = button;
            selectedButton2By2Button2.rotate();
            isProcessing = true;
            incorrectMatch++;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2By2Button2.rotate();
                    selected2By2Button1.rotate();
                    selected2By2Button1 = null;
                    selectedButton2By2Button2 = null;
                    isProcessing = false;
                }
            }, 400);
            misMatchToast();
        }
    }
    public static int getWindowHeight(Activity a){
        int height = a.getWindow().getDecorView().getHeight();
        return height;
    }
}



class MemoryButton extends android.support.v7.widget.AppCompatButton{

    //variables to reference the row and column, and the id of the faced down card
    public int row_2By2,column_2By2, actionHeight;

    //id of the front drawable value
    public int front2By2DrawableValue;

    //Reference to the drawable objects()
    public Drawable front2By2Card,backOfCard;

    //variables to define the Actions a user can encounter
    public boolean isRotated = false;
    public boolean isMatched = false;

    //Default Constructor
    @SuppressLint("RestrictedApi")
    public MemoryButton(Context context, int Row, int Column, int FrontDrawableValue, int height)
    {
        //Parent Class Constructor
        super(context);

        //This= MemoryButton
        this.row_2By2 = Row;
        this.column_2By2 = Column;
        this.front2By2DrawableValue = FrontDrawableValue;
        this.actionHeight = height;

        //initialize the front & back of the card
        front2By2Card = AppCompatDrawableManager.get().getDrawable(context, front2By2DrawableValue);
        backOfCard = AppCompatDrawableManager.get().getDrawable(context, R.drawable.back);
        setBackground(backOfCard);

        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels / 2;
        int screenHeight = (getContext().getResources().getDisplayMetrics().heightPixels - this.actionHeight) / 2;

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(Row),GridLayout.spec(Column));
        params.width = screenWidth;
        params.height = screenHeight;

        setLayoutParams(params);
    }

    //setters and getters for the primitives

    //get: frontDrawableValue (ID)
    public int getFrontDrawableValue(){
        return front2By2DrawableValue;
    }

    //get: isMatched()
    public boolean isMatched(){
        return isMatched;
    }

    //set: isMatched()
    public void setMatched(boolean matched){
        isMatched = matched;
    }

    //Logic: Rotate
    public void rotate()
    {
        if(isRotated)
        {
            //doNothing if the user did not rotate any card and keep the card faced down.
            setBackground(backOfCard);
            isRotated = false;
        }
        else
        {
            //if the userRotated the card then set the isRotated to true for a duration of Time
            //Reveal the card
            setBackground(front2By2Card);
            isRotated = true;
        }

        if(isMatched){
            //doNothing since the user did not match.
            //In this case the user revealed two cards that are an incorrect match.
            //We rotate the cards to not show faced down image
            return;
        }
    }
}
