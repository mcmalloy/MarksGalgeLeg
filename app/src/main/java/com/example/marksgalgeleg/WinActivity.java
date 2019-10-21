package com.example.marksgalgeleg;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WinActivity extends AppCompatActivity {
    Button RestartGameButton;
    Button ReturnMainMenuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winstate);

        listenToButtons();

    }


    public void listenToButtons(){
        // Capture button clicks
        //TODO: Currently  this activity does not allow buttons. Returns a nullpointerexception??
//        RestartGameButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                System.out.println("play again");
//                playAgain();
//            }
//        });
//        ReturnMainMenuButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                System.out.println("main menu");
//                GotoMenu();
//            }
//        });
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
}
