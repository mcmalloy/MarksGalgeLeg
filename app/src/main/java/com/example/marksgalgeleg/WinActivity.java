package com.example.marksgalgeleg;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieImageAsset;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class WinActivity extends AppCompatActivity {
    MediaPlayer player;
    Button RestartGameButton;
    Button ReturnMainMenuButton;
    Button HighScoreButton;
    TextView addNumberOfGuesses;
    String ord;
    ArrayList<Integer> scores = new ArrayList<>();
    String key;
    TextView pointshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winstate);
        playWinningMusic();
        startAnimation();
        // Retrieving passed argument, in this case the number of guesses
        String numberOfGuesses = getIntent().getExtras().getString("number");
        scores = getIntent().getExtras().getIntegerArrayList("scores");
        key = getIntent().getExtras().getString("key");
        ord = getIntent().getStringExtra("ord");
        System.out.println("ANTAL GÆT : "+numberOfGuesses);
        addNumberOfGuesses = findViewById(R.id.numberOfGuessesView);
        addNumberOfGuesses.setText("Tillykke, du vandt spillet efter "+numberOfGuesses+" gæt!");
        listenToButtons();

    }


    public void listenToButtons(){
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
        myIntent.putIntegerArrayListExtra("scores",scores);
        myIntent.putExtra("ord",ord);
        startActivity(myIntent);
    }

    public void playWinningMusic(){
        if(player==null){ // To avoid it playing multiple times and using extra resources
            player = MediaPlayer.create(this,R.raw.winning);
        }
        player.start();
    }

    private void startAnimation(){
        LottieAnimationView animation;
        animation = (LottieAnimationView)findViewById(R.id.confetti);

    }
}
