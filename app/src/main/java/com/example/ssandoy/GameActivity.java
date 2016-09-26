package com.example.ssandoy.s236305;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Button button;

    private CurrentGame currentGame;

    private Typeface myTypeface;

    private ArrayList<String> wordToGuess;
    private ArrayList<String> wordsInGame;
    private int totalWords;

    private int losses = 0;
    private int wins = 0;

    private AlertDialog alertDialog;

    private TextView wordView;
    private LinearLayout statView;

    private ImageButton returnButton;

    private TableLayout buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSelector.loadLanguage(this);
        setContentView(R.layout.activity_game);
        myTypeface = Typeface.createFromAsset(getAssets(), "fonts/chawp.ttf");
        totalWords =  getApplicationContext().getResources().getStringArray(R.array.WORDS).length;
        wordView = (TextView) findViewById(R.id.wordView);
        wordView.setTypeface(myTypeface);
        wordView.setTextSize(17);
        statView = (LinearLayout) findViewById(R.id.statView);
        buttons = (TableLayout) findViewById(R.id.buttonLayout);
        returnButton = (ImageButton) findViewById(R.id.returnArrow);
        returnButtonOnClick(this);
        wordsInGame = new ArrayList<>(Arrays.asList(getApplicationContext().getResources().getStringArray(R.array.WORDS)));
        newGame();
        setWordView();
        setStatView();

        for(int i = 0; i < statView.getChildCount(); i++) {
            View view = statView.getChildAt(i);
            if (view instanceof TextView) {
                TextView statView = (TextView) view;
                statView.setTypeface(myTypeface);
            }
        }

        removeButtons();


    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        int status = currentGame.getHangmanStatus().getStatus();
        savedInstanceState.putParcelable("CurrentGame", currentGame);
        savedInstanceState.putInt("wins", wins);
        savedInstanceState.putInt("losses", losses);
        savedInstanceState.putInt("Status", status);
        savedInstanceState.putStringArrayList("wordToGuess", wordToGuess);
        savedInstanceState.putStringArrayList("wordsInGame",wordsInGame);

    }

    @Override
    public void onBackPressed() {
        createGameOverDialog();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentGame = savedInstanceState.getParcelable("CurrentGame");
        wins = savedInstanceState.getInt("wins");
        losses = savedInstanceState.getInt("losses");
        currentGame.getHangmanStatus().setStatus(savedInstanceState.getInt("Status"));
        wordToGuess = savedInstanceState.getStringArrayList("wordToGuess");
        wordsInGame = savedInstanceState.getStringArrayList("wordsInGame");
        updateWordView();
        updateImage();
        updateButtons();

        if(currentGame.isGameOver() || currentGame.isGameWon()){
            onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(alertDialog != null && alertDialog.isShowing())
        alertDialog.dismiss();
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        Button letter = button;
        boolean guess = currentGame.guessCorrectLetter(letter.getText().toString());
        if(!guess) {
            button.setTextColor(Color.RED);
        } else {
            button.setTextColor(Color.TRANSPARENT);
        }
        createGameOverDialog();
        updateImage();

        button.setClickable(false);
        updateWordView(letter.getText().toString());
    }


    public void createGameOverDialog() {
        if(currentGame.isGameOver()) {
            String message = getResources().getString(R.string.word) + " " + convertArrayListToString(wordToGuess)
                    + "\n" + getResources().getString(R.string.fortsette);
            losses++;
            alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.gameover)
                    .setMessage(message)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            newGame();
                        }
                    })
                    .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        } else if(currentGame.isGameWon()) {
            String message = getResources().getString(R.string.word) + " " + convertArrayListToString(wordToGuess)
                    + "\n" + getResources().getString(R.string.fortsette);
            wins++;
           alertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.gamewon)
                    .setMessage(message)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            newGame();
                        }
                    })
                    .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }


    public ArrayList<String> generateWord(){

        String word;
        Random r = new Random();
        int j = r.nextInt(wordsInGame.size());

        word = wordsInGame.get(j);

        wordToGuess = new ArrayList<>();
        for(int i = 0; i<word.length(); i++) {
            wordToGuess.add(Character.toString(word.charAt(i)));
        }
        wordsInGame.remove(j);

        return wordToGuess;
    }

    public void updateImage() {
        switch(currentGame.getHangmanStatus().getStatus()) {
            case 6:
                ImageView gallow = (ImageView) findViewById(R.id.gallows);
                gallow.setImageDrawable(getResources().getDrawable(R.drawable.hangman0));
                break;
            case 5:
                ImageView head = (ImageView) findViewById(R.id.gallows);
                head.setImageDrawable(getResources().getDrawable(R.drawable.hangman1));
                break;
            case 4:
                ImageView body = (ImageView) findViewById(R.id.gallows);
                body.setImageDrawable(getResources().getDrawable(R.drawable.hangman2));
                break;
            case 3:
                ImageView leftarm = (ImageView) findViewById(R.id.gallows);
                leftarm.setImageDrawable(getResources().getDrawable(R.drawable.hangman3));
                break;
            case 2:
                ImageView rightarm = (ImageView) findViewById(R.id.gallows);
                rightarm.setImageDrawable(getResources().getDrawable(R.drawable.hangman4));
                break;
            case 1:
                ImageView leftleg = (ImageView) findViewById(R.id.gallows);
                leftleg.setImageDrawable(getResources().getDrawable(R.drawable.hangman5));
                break;
            case 0:
                ImageView rightleg = (ImageView) findViewById(R.id.gallows);
                rightleg.setImageDrawable(getResources().getDrawable(R.drawable.hangman6));
                break;
        }
    }

    public void setWordView(){
        wordView.setText("");
        for(int j = 0; j<wordToGuess.size(); j++)
        {

            wordView.append("_");
            if(!(j == wordToGuess.size() -1))
                wordView.append(" ");
        }
    }

    public void setStatView() {
        for(int i = 0; i < statView.getChildCount(); i++) {
            View view = (View) statView.getChildAt(i);
            if (view instanceof TextView) {
                TextView statView = (TextView) view;
                if (view.getId() == R.id.statwin) {
                    statView.setText(getResources().getString(R.string.wins) + " " + wins);
                } else if (view.getId() == R.id.stattap) {
                    statView.setText(getResources().getString(R.string.losses) + " " + losses);
                }

            }
        }
    }


    public void updateWordView(String letter) {

        StringBuilder currSequence = new StringBuilder();
        for(String wordletter : wordToGuess) {
            if(wordletter.equals(letter + " ")) {
                currSequence.append(wordletter);
            } else if(currentGame.getCorrectCharactersGuessed().contains(wordletter)) {
                currSequence.append(wordletter + " ");
            }
            else {
                currSequence.append("_ ");
            }
        }
        wordView.setText(currSequence);
    }

    public void updateWordView() {
        StringBuilder currSequence = new StringBuilder();

        for(String wordletter : wordToGuess) {
            if(currentGame.getCorrectCharactersGuessed().contains(wordletter)) {
                currSequence.append(wordletter);
            }
            else {
                currSequence.append("_ ");
            }
        }
        wordView.setText(currSequence);
    }

    public void updateButtons() {

        for (int i = 0; i < buttons.getChildCount(); i++) {
            View view = (View) buttons.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow row = (TableRow) view;
                for (int j = 0; j < row.getChildCount(); j++) {
                    Button knapp = (Button) row.getChildAt(j);
                    knapp.setTypeface(myTypeface);
                    if (currentGame.getCorrectCharactersGuessed().contains(knapp.getText())) {
                        knapp.setClickable(false);
                        knapp.setTextColor(Color.TRANSPARENT);
                    } else if(currentGame.getIncorrectCharactersGuessed().contains(knapp.getText())) {
                        knapp.setClickable(false);
                        knapp.setTextColor(Color.RED);
                    }

                }
            }
        }
    }


    private void newGame() {
        if(wins + losses < totalWords) {
            wordToGuess = generateWord();
            currentGame = new CurrentGame(wordToGuess);
            setWordView();

            updateImage();
            setStatView();

            for (int i = 0; i < buttons.getChildCount(); i++) {
                View view = (View) buttons.getChildAt(i);
                if (view instanceof TableRow) {
                    TableRow row = (TableRow) view;
                    for (int j = 0; j < row.getChildCount(); j++) {
                        Button knapp = (Button) row.getChildAt(j);
                        knapp.setTypeface(myTypeface);
                        knapp.setTextColor(Color.WHITE);
                        knapp.setClickable(true);
                    }

                }

            }
        } else {
           alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.usedUpWords)
                    .setMessage(R.string.returnToStart)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent exit = new Intent(GameActivity.this,StartActivity.class);
                            startActivity(exit);
                            finish();
                        }
                    })
                    .show();
        }
    }

    public String convertArrayListToString(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for(String character : list) {
            sb.append(character);
        }
        return sb.toString();
    }

    public void returnButtonOnClick(final Context context) {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alertDialog =  new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.exitGame)
                        .setMessage(R.string.exitGameText)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent exit = new Intent(GameActivity.this,StartActivity.class);
                                startActivity(exit);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
    }

    public void removeButtons() {
        SharedPreferences sprfs = getSharedPreferences(LanguageSelector.LANGUAGE, Activity.MODE_PRIVATE);
        String lang= sprfs.getString(LanguageSelector.LANGUAGE, LanguageSelector.DEFAULT_LANGUAGE);
        if(lang.equals("en")){
            button = (Button) findViewById(R.id.Æbutton);
            button.setVisibility(View.GONE);

            button = (Button) findViewById(R.id.Øbutton);
            button.setVisibility(View.GONE);

            button = (Button) findViewById(R.id.Åbutton);
            button.setVisibility(View.GONE);
        }
    }

}
