package net.brigs.gameseducation.games.memory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.brigs.gameseducation.R;


public class MemoryActivity extends AppCompatActivity {

    private RadioGroup gridGroup;
    private RadioButton rb_selectedGrid;
    private Button btnStart, btnExit, btnSave;
    SharedPreferences saveGridSize, getSavedGridSize;
    String userGridChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        saveGridSize = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        gridGroup = findViewById(R.id.GridGroup);
        btnSave = findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButtonId = gridGroup.getCheckedRadioButtonId();
                rb_selectedGrid = (RadioButton)findViewById(selectedRadioButtonId);
                String selectedRadioButtonText = rb_selectedGrid.getText().toString();
                SharedPreferences.Editor editor = saveGridSize.edit();
                editor.putString("gridKey",selectedRadioButtonText);
                editor.commit();
            }
        });


        btnStart= findViewById(R.id.button_start);
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSharedPreferenceGridSize();
            }
        });

        //Exit Button Reference - used to exit the game
        btnExit = findViewById(R.id.button_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }


    public void toast2x2() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.toastA, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toast4x4() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.toastB, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toast6x6() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.toastC, Toast.LENGTH_SHORT);
        toast.show();
    }

    //Reads the last saved radioButtonPreference
    public void getSharedPreferenceGridSize()
    {
        //(key, defaultValue) - 4x4 grid size is chosen for the first time if the user did not make a choice
        //If the app is not installed for the first time the last saved grid size pref will be started assuming the user did not make a choice
        getSavedGridSize = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        userGridChoice = getSavedGridSize.getString("gridKey","4X4 GRID");

        if(userGridChoice.equals("2X2 GRID"))
        {
            toast2x2();
            Intent twoByTwo = new Intent(MemoryActivity.this, Grid2x2_Activity.class);
            startActivity(twoByTwo);
        }
        else if(userGridChoice.equals("4X4 GRID"))
        {
            toast4x4();
            Intent fourByFour = new Intent(MemoryActivity.this, Grid4x4_Activity.class);
            startActivity(fourByFour);
        }
        else
        {
            toast6x6();
            Intent sixBySix = new Intent(MemoryActivity.this, Grid6x6_Activity.class);
            startActivity(sixBySix);
        }
    }

}
