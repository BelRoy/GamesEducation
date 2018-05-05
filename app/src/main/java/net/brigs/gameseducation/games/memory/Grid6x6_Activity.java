package net.brigs.gameseducation.games.memory;

import android.annotation.SuppressLint;
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

public class Grid6x6_Activity extends AppCompatActivity
        implements View.OnClickListener {

    public MemoryButtonC selected6By6Button1,selected6By6Button2;

    public int numberOf6By6Drawables;
    public MemoryButtonC[] buttonCollection;

    //buttonGraphicLocations - unique combinations of cards we have
    //buttonGraphics - The original R.ID.Drawable id's for the individual pictures we have
    public int[] buttonDrawableLocations,buttonDrawables;

    //Used for creating the delay
    public boolean isProcessing = false;

    //added variables
    public final int  FINAL_MATCHES = 18;
    public int correctMatch;
    public int incorrectMatch;
    public long startTime;
    public String gameOverMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid6x6_);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.activity_grid6x6_);

        //#of Columns & Rows
        int totalColumns = gridLayout.getColumnCount();
        int totalRows = gridLayout.getRowCount();

        numberOf6By6Drawables = (totalColumns * totalRows);
        buttonCollection = new MemoryButtonC[numberOf6By6Drawables];

        //only stores the unique cards
        buttonDrawables = new int[(numberOf6By6Drawables / 2)];

        buttonDrawables[0] = R.drawable.front_1;
        buttonDrawables[1] = R.drawable.front_2;
        buttonDrawables[2] = R.drawable.front_3;
        buttonDrawables[3] = R.drawable.front_4;
        buttonDrawables[4] = R.drawable.front_5;
        buttonDrawables[5] = R.drawable.front_6;
        buttonDrawables[6] = R.drawable.front_7;
        buttonDrawables[7] = R.drawable.front_8;
        buttonDrawables[8] = R.drawable.front_9;
        buttonDrawables[9] = R.drawable.front_10;
        buttonDrawables[10] = R.drawable.front_11;
        buttonDrawables[11] = R.drawable.front_12;
        buttonDrawables[12] = R.drawable.front_13;
        buttonDrawables[13] = R.drawable.front_14;
        buttonDrawables[14] = R.drawable.front_15;
        buttonDrawables[15] = R.drawable.front_16;
        buttonDrawables[16] = R.drawable.front_17;
        buttonDrawables[17] = R.drawable.front_18;

        buttonDrawableLocations = new int[numberOf6By6Drawables];
        randomize6By6Graphics();

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
        for (int r = 0; r < totalRows; r++)
        {
            for (int c = 0; c < totalColumns; c++)
            {
                MemoryButtonC tempButton = new MemoryButtonC(this, r, c, buttonDrawables[buttonDrawableLocations[r * totalColumns + c]], optionBarsHeight);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttonCollection[r * totalColumns + c] = tempButton;
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
        menuInflater.inflate(R.menu.menu_6x6,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Logic for the home and the reset btn
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.it_home6x6: goHome();

            case R.id.it_refresh6x6: reset6x6Grid();

            default: return super.onOptionsItemSelected(item);
        }
    }

    public void goHome()
    {
        Intent mainMenu= new Intent(Grid6x6_Activity.this, MemoryActivity.class);
        startActivity(mainMenu);
    }

    //Launch a new Grid2x2_Activity since it contains the logic to randomize and rotate the cards
    @SuppressLint("ObsoleteSdkInt")
    public void reset6x6Grid()
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

    public void randomize6By6Graphics() {
        Random rnd = new Random();
        for (int i = 0; i < numberOf6By6Drawables; i++) {
            buttonDrawableLocations[i] = (i % (numberOf6By6Drawables / 2));
        }

        for (int i = 0; i < numberOf6By6Drawables; i++) {
            int temp = buttonDrawableLocations[i];
            int swapIndex = rnd.nextInt(36);
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
        MemoryButtonC button = (MemoryButtonC) view;
        Handler handler = new Handler();

        if (isProcessing) {
            //doNothing when there is a process running - this prevents crashes
            return;
        }

        if (button.isMatched) {
            //if Button has a match then do nothing
            return;
        }

        if (selected6By6Button1 == null) {
            selected6By6Button1 = button;
            selected6By6Button1.rotate();
            return;
        }

        if (selected6By6Button1.getId() == button.getId()) {
            return;
        }
        if (correctMatch == 0 && incorrectMatch == 0){
            startTime = System.currentTimeMillis();
        }
        if (selected6By6Button1.getFrontDrawableValue() == button.getFrontDrawableValue()) {
            //User matches two cards
            button.rotate();
            button.setMatched(true);
            selected6By6Button1.setEnabled(false);
            button.setEnabled(false);
            selected6By6Button1 = null;
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
                                reset6x6Grid();;
                            }
                        })
                        .setPositiveButton("Go to Main Menu", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent mainMenu = new Intent(Grid6x6_Activity.this, MemoryActivity.class);
                                startActivity(mainMenu);
                            }
                        }).create().show();

            }
            return;
        }
        else {
            //User fails to match two cards
            selected6By6Button2 = button;
            selected6By6Button2.rotate();
            isProcessing = true;
            incorrectMatch++;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selected6By6Button2.rotate();
                    selected6By6Button1.rotate();
                    selected6By6Button1 = null;
                    selected6By6Button2 = null;
                    isProcessing = false;
                }
            }, 400);
            misMatchToast();
        }
    }
}

class MemoryButtonC extends Button {

    //variables to reference the row and column, and the id of the faced down card
    public int row_6By6,column_6By6, actionHeight;
    public int front6By6DrawableValue;

    //Reference to the drawable objects()
    public Drawable front6By6Card,backOfCard;

    //variables to define the Actions a user can encounter
    public boolean isRotated = false;
    public boolean isMatched = false;

    //Default Constructor
    public MemoryButtonC(Context context, int Row, int Column, int FrontDrawableValue, int height) {
        //Parent Class Constructor
        super(context);

        //This= MemoryButton
        this.row_6By6 = Row;
        this.column_6By6 = Column;
        this.front6By6DrawableValue = FrontDrawableValue;
        this.actionHeight = height;

        //initialize the front & back of the card
        front6By6Card = AppCompatDrawableManager.get().getDrawable(context, front6By6DrawableValue);
        backOfCard = AppCompatDrawableManager.get().getDrawable(context, R.drawable.back);
        setBackground(backOfCard);

        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels / 6;
        int screenHeight = (getContext().getResources().getDisplayMetrics().heightPixels - this.actionHeight) / 6;

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(Row), GridLayout.spec(Column));
        params.width = screenWidth;
        params.height = screenHeight;

        setLayoutParams(params);
    }

    //setters and getters for the primitives

    //get: frontDrawableValue (ID)
    public int getFrontDrawableValue() {
        return front6By6DrawableValue;
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
            setBackground(front6By6Card);
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