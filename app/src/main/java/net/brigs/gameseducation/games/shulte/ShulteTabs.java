package net.brigs.gameseducation.games.shulte;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.brigs.gameseducation.MenuItems;
import net.brigs.gameseducation.R;




public class ShulteTabs extends Activity{

    @Override

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shulte_act);
        String fontPath = "fonts/disney.ttf";
        TextView text = findViewById(R.id.shulte_table
        );
        Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
        text.setTypeface(typeface);

        findViewById(R.id.sh_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShulteTabs.this, ShulteGame.class));
                finish();
            }
        });

        findViewById(R.id.sh_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShulteTabs.this, MenuItems.class));
                finish();
            }
        });
    }

}
