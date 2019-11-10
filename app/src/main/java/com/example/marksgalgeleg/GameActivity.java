package com.example.marksgalgeleg;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    Button gætKnap;
    final Galgelogik spil = new Galgelogik();
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
    static int numberOfguesses = 0;

    ArrayList<Integer> score = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        spil.nulstil();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText bogstavGættet = (EditText) findViewById(R.id.gætInputField);
        final TextView synligtOrd = (TextView) findViewById(R.id.synligtord);



        synligtOrd.setText(spil.getSynligtOrd());

        gætKnap = (Button) findViewById(R.id.GuessButton);
        // Capture button clicks
        gætKnap.setOnClickListener(new View.OnClickListener() { // Lytter til når brugeren trykker på gæt knap.

                public void onClick(View arg0) { String bogstav = String.valueOf(bogstavGættet.getText());
                    spil.gætBogstav(bogstav);
                    spil.logStatus();

                    bogstavGættet.setText(""); // Resetter tekstfeltet så snart brugeren har lavet et gæt
                    String ord = spil.getSynligtOrd();
                    System.out.println(ord);
                    synligtOrd.setText("" + ord);
                    System.out.println("Spil status: "+spil.erSpilletSlut());

                    makeGuess(bogstav);
                }
            });

    }

    public void makeGuess(String bogstav){
        numberOfguesses++;
        // After every guess we check to see if the game has now concluded.
        if(spil.erSpilletSlut()){
            if(spil.erSpilletVundet()){
                goToWinScreen(); // Sends the user to the winpage
            }
            else{
                goToLoseScreen();
            }
        }
        else if(spil.erSidsteBogstavKorrekt()){
            addCorrectLetter(bogstav);
        }
        else if(spil.erSidsteBogstavKorrekt()==false){
            ImageView image = (ImageView) findViewById(R.id.gameImage);
            image.setImageResource(images[++currentImage[0]]);
            addWrongLetter(bogstav);
        }
    }

    public void addWrongLetter(String letter){
        final TextView brugteBogstaverForkert = (TextView) findViewById(R.id.ForkerteBogstaver);
        brugteBogstaverForkert.append(" "+letter);
    }

    public void addCorrectLetter(String letter){

        final TextView brugteBogstaverKorrekt = (TextView) findViewById(R.id.KorrekteBogstaver);
        brugteBogstaverKorrekt.append(" "+letter);

    }

    public void goToWinScreen(){
        // Sends the user to the winpage
        System.out.println("ENTERING WINNING ACTIVITY");
        String number = ""+numberOfguesses;

        sendDataToListView(); // Transfer the list to the high score.

        Intent myIntent = new Intent(this,WinActivity.class);
        myIntent.putExtra("number",number);
        startActivity(myIntent);
    }

    public void goToLoseScreen(){
        // Sends the user to the losepage
        System.out.println("ENTERING LOSING ACTIVITY");
        String ord = spil.getOrdet();
        Intent myIntent = new Intent(this,LoseActivity.class);
        myIntent.putExtra("arg",ord);
        startActivity(myIntent);
    }

    public void sendDataToListView(){
        if(getArrayList("High scores")!=null){
            score = getArrayList("High scores"); // First get the array list, if empty it doesnt matter
            score.add(100-numberOfguesses); // Arbitrary score for list.
            saveArrayList(score,"High scores"); // Then store the array list with new value
            Intent sendScores = new Intent(this,HighScoreListActivity.class);
            sendScores.putExtra("list",score);
            System.out.println("DATA BEING STORED!!");
        }
        else{
            score.add(100-numberOfguesses);
            saveArrayList(score,"High scores");
            Intent sendScores = new Intent(this,HighScoreListActivity.class);
            sendScores.putExtra("list",score);
            System.out.println("DATA BEING STORED!!");
        }
    }

    public void saveArrayList(ArrayList<Integer> list, String key){
        SharedPreferences prefs;

        System.out.println("Saving arraylist! Showing old arraylist: ");
        System.out.println(Arrays.toString(list.toArray()));

        prefs = this.getSharedPreferences("High scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!

        System.out.println();
        System.out.println("New array retrieved from storage: ");
        System.out.println(Arrays.toString(getArrayList("High scores").toArray()));
    }

    public ArrayList<Integer> getArrayList(String key){
        SharedPreferences prefs = this.getSharedPreferences("High scores",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}