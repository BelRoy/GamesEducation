package net.brigs.gameseducation.classes_repo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.brigs.gameseducation.R;
import net.brigs.gameseducation.games.f_puzzle.GamePlay;
import net.brigs.gameseducation.games.math.Mathematic;
import net.brigs.gameseducation.games.shulte.ShulteTabs;


public class ThirdClassItems extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_class);


        findViewById(R.id.f_puzzle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdClassItems.this, GamePlay.class));

            }
        });

        findViewById(R.id.math).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdClassItems.this, Mathematic.class));
            }
        });
        findViewById(R.id.shulte).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdClassItems.this, ShulteTabs.class));
            }
        });

    }
}
