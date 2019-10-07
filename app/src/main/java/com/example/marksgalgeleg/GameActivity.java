package com.example.marksgalgeleg;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marksgalgeleg.Galgelogik;

public class GameActivity extends AppCompatActivity {

    /**
     * TODO:
     * Create the actual game screen
     * Show the first picture of the empty noose
     * Show the form of hidden word
     *
     */
    Button gætKnap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView img;
        int[] images = {R.drawable.galge,R.drawable.forkert1,R.drawable.forkert2,R.drawable.forkert3};
        int currentImage;

        final Galgelogik spil = new Galgelogik();
        spil.nulstil();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        gætKnap = (Button) findViewById(R.id.GuessButton);
        // Capture button clicks
        gætKnap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                 // spil.gætBogstav("2");

            }
        });

    }




}
