package com.example.marksgalgeleg;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

  //TODO: Show the word in its hidden form, displaying each correctly guessed word


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
        final EditText bogstavGættet = (EditText) findViewById(R.id.gætInputField);
        final TextView debugWord = (TextView) findViewById(R.id.ActualWord);
        final TextView synligtOrd = (TextView) findViewById(R.id.synligtord);

        debugWord.append("DEBUG, Ordet i denne runde er: "+ spil.getOrdet());
        synligtOrd.setText(spil.getSynligtOrd());

        gætKnap = (Button) findViewById(R.id.GuessButton);
        // Capture button clicks
        gætKnap.setOnClickListener(new View.OnClickListener() { // Lytter til når brugeren trykker på gæt knap.

            public void onClick(View arg0) {

                String bogstav = String.valueOf(bogstavGættet.getText());
                spil.gætBogstav(bogstav);
                spil.logStatus();

                bogstavGættet.setText(""); // Resetter tekstfeltet så snart brugeren har lavet et gæt
                String ord = spil.getSynligtOrd();
                System.out.println(ord);
                synligtOrd.setText("" + ord);
                System.out.println("Spil status: "+spil.erSpilletSlut());

                if (spil.erSpilletSlut()!=true && spil.erSidsteBogstavKorrekt()==false) {
                    ImageView image = (ImageView) findViewById(R.id.gameImage);
                    image.setImageResource(images[++currentImage[0]]);
                    addWrongLetter(bogstav);
                }
                else{
                    addCorrectLetter(bogstav);
                    if(spil.erSpilletVundet()){
                        Toast.makeText(getApplicationContext(),"Du har vundet spillet!",Toast.LENGTH_LONG);
                    }
                    else if(spil.erSpilletTabt()){
                        Toast.makeText(getApplicationContext(),"Du har tabt spillet!",Toast.LENGTH_LONG);
                    }
                }
            }
        });

    }

    public void addWrongLetter(String letter){
        final TextView brugteBogstaverForkert = (TextView) findViewById(R.id.ForkerteBogstaver);
        brugteBogstaverForkert.append(" "+letter);
    }

    public void addCorrectLetter(String letter){
        final TextView brugteBogstaverKorrekt = (TextView) findViewById(R.id.KorrekteBogstaver);
        brugteBogstaverKorrekt.append(" "+letter);
    }
}