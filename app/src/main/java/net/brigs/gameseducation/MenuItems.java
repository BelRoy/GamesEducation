package net.brigs.gameseducation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.brigs.gameseducation.games.f_puzzle.GamePlay;
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
    }

}
