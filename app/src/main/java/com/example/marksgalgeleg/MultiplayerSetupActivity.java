package com.example.marksgalgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MultiplayerSetupActivity extends AppCompatActivity {

    Button startGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_setup);
        final EditText ord = (EditText) findViewById(R.id.newWord);
        startGame = findViewById(R.id.startMultiplayer);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(ord.getText().toString().isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "Please type a word";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    startGame(ord.getText().toString());
                }
            }
        });
    }

    public void startGame(String word){
        Intent myIntent = new Intent(this, GameActivity.class);
        myIntent.putExtra("gameMode",2);
        myIntent.putExtra("userWord",word);
        startActivity(myIntent);
    }
}
