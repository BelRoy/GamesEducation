package net.brigs.gameseducation.classes_repo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import net.brigs.gameseducation.R;
import net.brigs.gameseducation.games.math.Mathematic;
import net.brigs.gameseducation.games.memory.MemoryActivity;
import net.brigs.gameseducation.games.tic_tac.TicTacToe;

public class FirstClassItems extends AppCompatActivity {

        @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_class);


        findViewById(R.id.tic_tac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstClassItems.this, TicTacToe.class));

            }
        });

        findViewById(R.id.math).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstClassItems.this, Mathematic.class));
            }
        });
        findViewById(R.id.memory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstClassItems.this, MemoryActivity.class));
            }
        });

    }
}
