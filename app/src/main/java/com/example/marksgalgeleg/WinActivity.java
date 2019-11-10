package com.example.marksgalgeleg;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WinActivity extends AppCompatActivity {
    Button RestartGameButton;
    Button ReturnMainMenuButton;
    Button HighScoreButton;
    TextView addNumberOfGuesses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winstate);

        // Retrieving passed argument, in this case the number of guesses
        String numberOfGuesses = getIntent().getExtras().getString("number");
        System.out.println("ANTAL GÆT : "+numberOfGuesses);
        addNumberOfGuesses = findViewById(R.id.numberOfGuessesView);
        addNumberOfGuesses.setText("Tillykke, du vandt spillet på "+numberOfGuesses+". forsøg!");
        listenToButtons();

    }


    public void listenToButtons(){
        System.out.println("virker det stadig?");
        // Capture button clicks
        RestartGameButton = findViewById(R.id.playAgain);
        ReturnMainMenuButton = findViewById(R.id.goToMainMenu);
        HighScoreButton = findViewById(R.id.highScoreWin);


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
        Intent myIntent = new Intent(this,
                HighScoreListActivity.class);
        startActivity(myIntent);
    }

    public void startKonfetti(){
        // Todo: Not an important method, using an open-source animation library here.
    }
}
