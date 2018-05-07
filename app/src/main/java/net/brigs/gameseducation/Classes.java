package net.brigs.gameseducation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.brigs.gameseducation.classes_repo.FirstClassItems;
import net.brigs.gameseducation.classes_repo.FourthClassItems;
import net.brigs.gameseducation.classes_repo.SecondClassItems;
import net.brigs.gameseducation.classes_repo.ThirdClassItems;

public class Classes extends AppCompatActivity{

    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classes_act);

        findViewById(R.id.f_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Classes.this, FirstClassItems.class));
            }
        });
        findViewById(R.id.s_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Classes.this, SecondClassItems.class));
            }
        });
        findViewById(R.id.t_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Classes.this, ThirdClassItems.class));
            }
        });
        findViewById(R.id.ff_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Classes.this, FourthClassItems.class));
            }
        });
    }
}
