package com.example.marksgalgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.marksgalgeleg.Galgelogik;

public class MainActivity extends AppCompatActivity {
    Button Startbutton;
    Button ContinueButton;
    Button HelpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Startbutton = (Button) findViewById(R.id.StartGameButton);
        ContinueButton = (Button) findViewById(R.id.ContinueGameButton);
        HelpButton = (Button) findViewById(R.id.HelpButton);

        listenToButtons();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void listenToButtons(){
        // Capture button clicks
        Startbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startGame();
            }
        });
        ContinueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                continueGame();
            }
        });
        HelpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                help();
            }
        });

    }

    public void startGame(){
        // Starts the game activity. Let the games commence
        Intent myIntent = new Intent(this,
                GameActivity.class);
        startActivity(myIntent);
    }

    public void continueGame(){
        Toast.makeText(getApplicationContext(),"This hasn't been implemented yet",Toast.LENGTH_LONG);
    }

    public void help(){
        Toast.makeText(getApplicationContext(),"This hasn't been implemented yet",Toast.LENGTH_LONG);
    }

}
