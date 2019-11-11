package com.example.marksgalgeleg;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {
    Button gætKnap;
    Button DeleteDataButton;
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

    final String key = "High scores";
    ArrayList<Integer> score = new ArrayList<>();

    public void decideGameMode(){
        int switchvalue = getIntent().getExtras().getInt("gameMode");

        if(switchvalue==1){
            new DownloadFilesTask().execute();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        decideGameMode();
        spil.nulstil();
        final EditText bogstavGættet = (EditText) findViewById(R.id.gætInputField);
        final TextView synligtOrd = (TextView) findViewById(R.id.synligtord);



        System.out.println("ALLE ORD:");
        for(int i=0; i<spil.muligeOrd.size() ;i++){
            System.out.println(spil.muligeOrd.get(i));
        }

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

        DeleteDataButton = findViewById(R.id.deleteButton);
        DeleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eraseArrayList(key);
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

        sendDataToListView(key); // Transfer the list to the high score.

        Intent myIntent = new Intent(this,WinActivity.class);
        myIntent.putExtra("number",number);
        myIntent.putIntegerArrayListExtra("scores",score);
        myIntent.putExtra("key",key);
        startActivity(myIntent);
    }

    public void goToLoseScreen(){
        // Sends the user to the losepage
        System.out.println("ENTERING LOSING ACTIVITY");
        String ord = spil.getOrdet();


        Intent myIntent = new Intent(this,LoseActivity.class);
        myIntent.putExtra("arg",ord);
        myIntent.putIntegerArrayListExtra("scores",getArrayList(key));
        myIntent.putExtra("key",key);
        startActivity(myIntent);
    }

    public void sendDataToListView(String key){
        if(getArrayList(key)!=null){
            score = getArrayList(key); // First get the array list, if empty it doesnt matter
            score.add(100-numberOfguesses); // Arbitrary score for list.
            saveArrayList(score,key); // Then store the array list with new value
            System.out.println("DATA BEING STORED!!");
        }
        else{
            score.add(100-numberOfguesses);
            saveArrayList(score,key);
            System.out.println("DATA BEING STORED!!");
        }
    }

    public void saveArrayList(ArrayList<Integer> list, String key){
        SharedPreferences prefs;

        System.out.println("Saving arraylist! Showing old arraylist: ");
        System.out.println(Arrays.toString(list.toArray()));

        prefs = this.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!

        System.out.println();
        System.out.println("New array retrieved from storage: ");
        System.out.println(Arrays.toString(getArrayList(key).toArray()));
    }

    public ArrayList<Integer> getArrayList(String key){
        SharedPreferences prefs = this.getSharedPreferences(key,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void eraseArrayList(String key){
        getArrayList(key);
        SharedPreferences prefs = this.getSharedPreferences(key,Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
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