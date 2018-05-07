package net.brigs.gameseducation.classes_repo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.brigs.gameseducation.R;
import net.brigs.gameseducation.games.math.Mathematic;
import net.brigs.gameseducation.games.memory.MemoryActivity;
import net.brigs.gameseducation.games.tic_tac.TicTacToe;

public class FourthClassItems extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_class);



        findViewById(R.id.math).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FourthClassItems.this, Mathematic.class));
            }
        });


    }
}
