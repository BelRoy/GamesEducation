package net.brigs.gameseducation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.brigs.gameseducation.games.f_puzzle.GamePlay;
import net.brigs.gameseducation.games.math.Mathematic;
import net.brigs.gameseducation.games.memory.MemoryActivity;
import net.brigs.gameseducation.games.shulte.ShulteTabs;
import net.brigs.gameseducation.games.tic_tac.TicTacToe;

public class MenuItems extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_items);

        findViewById(R.id.tic_tac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuItems.this, TicTacToe.class));

            }
        });

        findViewById(R.id.f_puzzle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuItems.this, GamePlay.class));
            }
        });
        findViewById(R.id.memory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuItems.this, MemoryActivity.class));
            }
        });
        findViewById(R.id.math).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuItems.this, Mathematic.class));
            }
        });
        findViewById(R.id.shulte).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuItems.this, ShulteTabs.class));
            }
        });
    }

}
