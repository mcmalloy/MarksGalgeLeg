package com.example.marksgalgeleg;

import java.util.ArrayList;

public class Data {

    //declare private data instead of public to ensure the privacy of data field of each class
    private int score;
    private String inGameWord;
    final String key = "High scores";
    final String key2 = "wordFromGame";

    public Data(int name, String inGameWord) {
        this.score = name;
        this.inGameWord = inGameWord;
    }

    //retrieve user's name
    public int getScore(){
        return score;
    }

    //retrieve users' hometown
    public String getHometown(){
        return inGameWord;
    }

    public static ArrayList<Data> getData() {
        ArrayList<Data> data = new ArrayList<Data>();

        return data;
    }
}
