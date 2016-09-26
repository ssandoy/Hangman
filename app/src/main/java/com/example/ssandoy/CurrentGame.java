package com.example.ssandoy.s236305;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ssandoy on 14.09.2016.
 */
public class CurrentGame implements Parcelable {


    private HangmanStatus hangmanStatus;
    private ArrayList<String> wordToGuess;


    private ArrayList<String> correctCharactersGuessed;
    private ArrayList<String> incorrectCharactersGuessed;


    public CurrentGame(ArrayList<String> wordToGuess) {
        hangmanStatus = new HangmanStatus();
        this.wordToGuess = wordToGuess;
        correctCharactersGuessed = new ArrayList<>();
        incorrectCharactersGuessed = new ArrayList<>();
    }

    public HangmanStatus getHangmanStatus() {
        return hangmanStatus;
    }

    protected CurrentGame(Parcel in) {
    }

    public static final Creator<CurrentGame> CREATOR = new Creator<CurrentGame>() {
        @Override
        public CurrentGame createFromParcel(Parcel in) {
            return new CurrentGame(in);
        }

        @Override
        public CurrentGame[] newArray(int size) {
            return new CurrentGame[size];
        }
    };

    public boolean guessCorrectLetter(String guessedLetter) {
        if(wordToGuess.contains(guessedLetter)) {
            for(String letter : wordToGuess){
                if(letter.equals(guessedLetter)) {
                    correctCharactersGuessed.add(guessedLetter);
                }
            }
            return true;
        } else {
            incorrectCharactersGuessed.add(guessedLetter);
            hangmanStatus.wrongGuess();
            return false;
        }

    }

    public boolean isGameOver() {
        return hangmanStatus.isGameLost();
    }

    public boolean isGameWon() {
        return wordToGuess.size() == correctCharactersGuessed.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeList(wordToGuess);
    }

    public ArrayList<String> getCorrectCharactersGuessed() {
        return correctCharactersGuessed;
    }

    public ArrayList<String> getIncorrectCharactersGuessed() {
        return incorrectCharactersGuessed;
    }
}
