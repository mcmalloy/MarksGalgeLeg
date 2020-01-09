package com.example.marksgalgeleg;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
public class GameActivity extends AppCompatActivity {
    MediaPlayer player;
    Button gætKnap;
    Button DeleteDataButton;
    String userword;
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
    int switchvalue;
    final String key = "key123"; // The key identifier that ables the program to score high scores
    public void decideGameMode(){
        switchvalue = getIntent().getExtras().getInt("gameMode");
        //TODO: Implement a thread that starts to download the words
        if(switchvalue==1){
            new DownloadFilesTask().execute();
        }
        else if(switchvalue==2){
            // We are in a multiplayer state
            userword = getIntent().getExtras().getString("userWord");
        }

    }
    public void checkforMultiplayer(){
        if(switchvalue==2){
            spil.setOrd(userword);
            spil.getSynligtOrd();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        decideGameMode();
        spil.nulstil();
        checkforMultiplayer();

        final EditText bogstavGættet = (EditText) findViewById(R.id.gætInputField);
        final TextView synligtOrd = (TextView) findViewById(R.id.synligtord);



        System.out.println("ALLE ORD:");
        for(int i=0; i<spil.muligeOrd.size() ;i++){
            System.out.println(spil.muligeOrd.get(i));
        }

        synligtOrd.setText(spil.getSynligtOrd());

        gætKnap = findViewById(R.id.GuessButton);
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

        DeleteDataButton = findViewById(R.id.deleteButton);
        DeleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

    }

    public void makeGuess(String bogstav){
        ++numberOfguesses;
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
            playSound(1);
            addCorrectLetter(bogstav);
        }
        else if(!spil.erSidsteBogstavKorrekt()){
            playSound(0);
            ImageView image = (ImageView) findViewById(R.id.gameImage);
            image.setImageResource(images[++currentImage[0]]);
            addWrongLetter(bogstav);
        }
    }

    public void playSound(int type){
        if(type==1){
            player = MediaPlayer.create(this,R.raw.correctword);
            player.start();
        }
        else if(type==0){
            player = MediaPlayer.create(this,R.raw.oof);
            player.start();
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
        saveData();
        System.out.println("ENTERING WINNING ACTIVITY");
        String number = ""+numberOfguesses;
        String ord = spil.getOrdet();
        Intent myIntent = new Intent(this,WinActivity.class);
        myIntent.putExtra("number",number);
        myIntent.putIntegerArrayListExtra("scores",loadData());
        myIntent.putExtra("key",key);
        myIntent.putExtra("ord",ord);
        loadData();



        startActivity(myIntent);
    }

    private void saveData(){
        ArrayList<Integer> score = loadData();
        score.add(100-numberOfguesses);
        SharedPreferences sharedPreferences = getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(score);
        editor.putString("task list", json);
        editor.apply();
        System.out.println("The arraylist has been saved");
    }
    private ArrayList<Integer> loadData() {
            ArrayList<Integer> score;
            SharedPreferences sharedPreferences = getSharedPreferences(key, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("task list", null);
            Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
            score = gson.fromJson(json, type);
            if(score==null){
                score = new ArrayList<>();
            }
            System.out.println("The list has been loaded successfully:");
            System.out.println(score);
            return score;
    }
    private void clearData(){
        SharedPreferences sharedPreferences = getSharedPreferences(key, MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public void goToLoseScreen(){
        // Sends the user to the losepage
        System.out.println("ENTERING LOSING ACTIVITY");
        String ord = spil.getOrdet();
        Intent myIntent = new Intent(this,LoseActivity.class);
        myIntent.putExtra("arg",ord); // Sends correct word to lose activity
        // myIntent.putIntegerArrayListExtra("scores",getArrayList(key));
        myIntent.putExtra("key",key);
        startActivity(myIntent);
    }


    class DownloadFilesTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                spil.hentOrdFraDr();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}