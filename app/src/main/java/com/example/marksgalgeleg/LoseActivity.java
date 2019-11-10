package com.example.marksgalgeleg;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoseActivity extends AppCompatActivity {
    Button RestartGameButton;
    Button ReturnMainMenuButton;
    Button HighScoreButton;
    TextView addCorrectWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        // Retrieving passed argument, in this case the correct word
        String passedArg = getIntent().getExtras().getString("arg");
        addCorrectWord = findViewById(R.id.ActualWordLoseScreen);
        addCorrectWord.setText("Det korrekte ord er: "+passedArg);


        listenToButtons();


    }

    public void listenToButtons(){
        // Capture button clicks
        //TODO: Currently  this activity does not allow buttons. Returns a nullpointerexception??
        RestartGameButton = findViewById(R.id.playAgain2);
        ReturnMainMenuButton = findViewById(R.id.goToMainMenu2);
        HighScoreButton = findViewById(R.id.highScoreLose);

        RestartGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("play again");
                playAgain();
            }
        });
        ReturnMainMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("main menu");
                GotoMenu();
            }
        });

        HighScoreButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                GotoHighScore();
            }
        });
    }

    public void playAgain(){
        // Sends the user to a new game
        Intent myIntent = new Intent(this,
                GameActivity.class);
        startActivity(myIntent);
    }

    public void GotoMenu(){
        // Sends the user to main menu
        Intent myIntent = new Intent(this,
                MainActivity.class);
        startActivity(myIntent);
    }

    public void GotoHighScore(){
        Intent myIntent = new Intent(this,HighScoreListActivity.class);
        startActivity(myIntent);
    }
}
