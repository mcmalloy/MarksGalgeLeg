package com.example.marksgalgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.example.marksgalgeleg.Galgelogik;

public class MainActivity extends AppCompatActivity {
    Button Startbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Startbutton = (Button) findViewById(R.id.StartGameButton);

        // Capture button clicks
        Startbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startGame();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void startGame(){
        // Starts the game activity. Let the games commence



        Intent myIntent = new Intent(this,
                GameActivity.class);
        startActivity(myIntent);
    }


}
