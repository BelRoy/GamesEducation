package net.brigs.gameseducation.classes_repo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.brigs.gameseducation.R;
import net.brigs.gameseducation.games.math.Mathematic;


public class SecondClassItems extends AppCompatActivity {

    @Override

        protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.second_class);




    findViewById(R.id.math).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SecondClassItems.this, Mathematic.class));
        }
    });


}
}
