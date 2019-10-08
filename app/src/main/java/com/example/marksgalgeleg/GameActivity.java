package com.example.marksgalgeleg;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        final int[] images =
                {
                        R.drawable.galge,
                        R.drawable.forkert1,
                        R.drawable.forkert2,
                        R.drawable.forkert3,
                        R.drawable.forkert4,
                        R.drawable.forkert5,
                        R.drawable.forkert6
                };
        final int[] currentImage = {0};

        final Galgelogik spil = new Galgelogik();
        spil.nulstil();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initializeFields();
        final EditText bogstavGættet = (EditText) findViewById(R.id.gætInputField);
        final TextView debugWord = (TextView) findViewById(R.id.ActualWord);
        final TextView brugteBogstaver = (TextView) findViewById(R.id.gættedeBogstaver);
        final TextView synligtOrd = (TextView) findViewById(R.id.synligtord);

        debugWord.append("DEBUG, Ordet i denne runde er: "+ spil.getOrdet());

        gætKnap = (Button) findViewById(R.id.GuessButton);
        // Capture button clicks
        gætKnap.setOnClickListener(new View.OnClickListener() { // Lytter til når brugeren trykker på gæt knap.

            public void onClick(View arg0) {

                String bogstav = String.valueOf(bogstavGættet.getText());
                spil.gætBogstav(bogstav);

                brugteBogstaver.append(" "+bogstav);
                bogstavGættet.setText(""); // Resetter tekstfeltet så snart brugeren har lavet et gæt


                System.out.println("Spil status: "+spil.erSpilletSlut());

                if (currentImage[0]<=5 && spil.erSpilletSlut()!=true && spil.erSidsteBogstavKorrekt()==false) {
                    ImageView image = (ImageView) findViewById(R.id.gameImage);
                    image.setImageResource(images[++currentImage[0]]);
                }
            }
        });

    }

    public void initializeFields(){

    }

}